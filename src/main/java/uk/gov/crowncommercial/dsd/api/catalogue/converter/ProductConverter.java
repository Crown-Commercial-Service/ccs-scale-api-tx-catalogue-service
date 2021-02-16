package uk.gov.crowncommercial.dsd.api.catalogue.converter;

import static org.apache.commons.lang3.StringUtils.isBlank;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.apache.camel.Converter;
import org.apache.camel.TypeConverters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.TypeRef;
import uk.gov.crowncommercial.dsd.api.catalogue.model.*;

/**
 * Product conversion utilities
 */
@Component
public class ProductConverter implements TypeConverters {

  @Autowired
  private ObjectMapper objectMapper;

  /**
   * Registered with Camel as converter but may be invoked directly
   *
   * @param productData the raw Spree product <code>{"data":{}}</code>
   * @return a product
   */
  @Converter
  public Product toProduct(final Map<String, Object> productData) {

    final Product product = objectMapper.convertValue(productData.get("attributes"), Product.class);
    product.setId((String) productData.get("id"));

    return product;
  }

  /**
   * Extract, unmarshal and transform image data from the Spree response for inclusion with the
   * {@link Product}.
   *
   * Note: for List Products, a single image is identified in the product data by
   * {@link Product#getImageId()} but in Get Product, all images related to the product are returned
   *
   * @param product
   * @param imagesData the Spree include data filtered to image(s) only
   * @return the product with image data
   */
  public Product addImages(final Product product, final List<Map<String, Object>> imagesData) {
    product.setImages(imagesData.parallelStream().filter(
        imgData -> isBlank(product.getImageId()) || product.getImageId().equals(imgData.get("id")))
        .map(imgData -> objectMapper.convertValue(imgData, Image.class))
        .collect(Collectors.toList()));
    return product;
  }

  /**
   * Extract and unmarshal the Spree variant include data and required related objects (vendor,
   * catalog, delivery_charges) into a collection of {@link SupplierCatalogProductInstance}
   *
   * @param product
   * @param spreeResponse
   * @return the enriched product
   */
  public Product addSupplierCatalogProductInstances(final Product product,
      final Map<String, Object> spreeResponse) {

    final DocumentContext docContext = JsonPath.parse(spreeResponse);

    /*
     * Filter included data objs for type = "variant" and tx to root type
     * SupplierCatalogProductInstance:
     */
    final List<SupplierCatalogProductInstance> supplierCatalogProductInstances =
        docContext.read("$.included[?(@.type == 'variant')]",
            new TypeRef<List<SupplierCatalogProductInstance>>() {});

    /*
     * For each variant (aka. SupplierCatalogProductInstance), find the relationship `id` for
     * `vendor` (Supplier),`catalog` (CommercialAgreementLot) and `delivery_charges`
     * (ProductDeliveryCharges), then find and read the relevant object and set on the SCPI
     */
    supplierCatalogProductInstances.stream().forEach(scpi -> {

      // Cannot do this in one unfortunately (https://github.com/json-path/JsonPath/issues/272)
      final List<?> variantJSON =
          JsonPath.read(spreeResponse, "$.included[?(@.type == 'variant' && @.id == '"
              + scpi.getSupplierProductCatalogInstanceId() + "')]");

      final String vendorId = JsonPath.read(variantJSON, "$[0].relationships.vendor.data.id");
      final String catalogId = JsonPath.read(variantJSON, "$[0].relationships.catalog.data.id");
      final String deliveryChargesId =
          JsonPath.read(variantJSON, "$[0].relationships.delivery_charges.data.id");

      final Supplier supplier =
          docContext.read("$.included[?(@.type == 'vendor' && @.id == '" + vendorId + "')]",
              new TypeRef<List<Supplier>>() {}).get(0);

      final CommercialAgreementLot cal =
          docContext.read("$.included[?(@.type == 'catalog' && @.id == '" + catalogId + "')]",
              new TypeRef<List<CommercialAgreementLot>>() {}).get(0);

      final ProductDeliveryCharges pdcs =
          docContext
              .read("$.included[?(@.type == 'product_delivery_charges' && @.id == '"
                  + deliveryChargesId + "')]", new TypeRef<List<ProductDeliveryCharges>>() {})
              .get(0);

      scpi.setSupplier(supplier);
      scpi.setCommercialAgreementLot(cal);
      scpi.setProductDeliveryCharges(pdcs);
    });

    product.setSupplierCatalogProductInstances(supplierCatalogProductInstances);

    return product;
  }

  public void addDocuments(final Product product, final List<Map<String, Object>> documentData) {
    product.setDocuments(
        objectMapper.convertValue(documentData, new TypeReference<List<Document>>() {}));
  }

  public void addProductProperties(final Product product,
      final List<Map<String, Object>> productPropertyData) {
    product.setProductProperties(objectMapper.convertValue(productPropertyData,
        new TypeReference<List<ProductProperty>>() {}));
  }

}
