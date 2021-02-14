package uk.gov.crowncommercial.dsd.api.catalogue.converter;

import static org.apache.commons.lang3.StringUtils.isBlank;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.apache.camel.Converter;
import org.apache.camel.TypeConverters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.TypeRef;
import lombok.extern.slf4j.Slf4j;
import uk.gov.crowncommercial.dsd.api.catalogue.model.Image;
import uk.gov.crowncommercial.dsd.api.catalogue.model.Product;
import uk.gov.crowncommercial.dsd.api.catalogue.model.SupplierCatalogProductInstance;

/**
 * Product conversion utilities
 */
@Component
@Slf4j
public class ProductConverter implements TypeConverters {

  @Autowired
  private ObjectMapper objectMapper;

  /**
   * Registered with Camel as converter but may be invoked directly
   *
   * @param productData the raw Spree product <code>{"data":{}}</code>
   * @return a product
   */
  @SuppressWarnings("unchecked")
  @Converter
  public Product toProduct(final Map<String, Object> productData) {

    final Product product = objectMapper
        .convertValue((Map<String, Object>) productData.get("attributes"), Product.class);
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
   *
   * @param product
   * @param includesData
   * @return
   */
  public Product addSupplierCatalogProductInstances(final Product product,
      final Map<String, Object> spreeResponse) {

    final DocumentContext docContext = JsonPath.parse(spreeResponse);

    // Filter included data objs for type = "variant" and tx to root type
    // SupplierCatalogProductInstance:

    final List<SupplierCatalogProductInstance> supplierCatalogProductInstances =
        docContext.read("$.included[?(@.type == 'variant')]",
            new TypeRef<List<SupplierCatalogProductInstance>>() {});

    // For each variant (aka. SupplierCatalogProductInstance), find the relationship `id` for
    // `vendor` (Supplier),`catalog` (CommercialAgreementLot) and `product_delivery_charges`
    // (ProductDeliveryCharges)

    product.setSupplierCatalogProductInstances(supplierCatalogProductInstances);

    return product;
  }

}
