package uk.gov.crowncommercial.dsd.api.catalogue.converter;

import static uk.gov.crowncommercial.dsd.api.catalogue.config.Constants.EXPROP_SPREE_DOCUMENT_DATA;
import static uk.gov.crowncommercial.dsd.api.catalogue.config.Constants.EXPROP_SPREE_IMAGE_DATA;
import static uk.gov.crowncommercial.dsd.api.catalogue.config.Constants.EXPROP_SPREE_PRODUCT_PROPS_DATA;
import java.util.List;
import java.util.Map;
import org.apache.camel.Converter;
import org.apache.camel.Exchange;
import org.apache.camel.TypeConverter;
import org.apache.camel.TypeConverters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uk.gov.crowncommercial.dsd.api.catalogue.model.GetProductMeta;
import uk.gov.crowncommercial.dsd.api.catalogue.model.GetProductResponse;
import uk.gov.crowncommercial.dsd.api.catalogue.model.GetProductResponse.GetProductResponseBuilder;
import uk.gov.crowncommercial.dsd.api.catalogue.model.Product;

/**
 * Converter for Spree Get Product response to CS {@link GetProductResponse}
 */
@Component
public class GetProductResponseConverter implements TypeConverters {

  @Autowired
  private ProductConverter productConverter;

  @SuppressWarnings("unchecked")
  @Converter
  public GetProductResponse toGetProductResponse(final Map<String, Object> spreeResponse,
      final Exchange exchange) {

    final TypeConverter converter = exchange.getContext().getTypeConverter();
    final GetProductResponseBuilder responseBuilder = GetProductResponse.builder();

    // Convert basic Product attributes
    final Product product = converter.convertTo(Product.class, spreeResponse.get("data"));

    // Add Images
    productConverter.addImages(product, exchange.getProperty(EXPROP_SPREE_IMAGE_DATA, List.class));

    // Add SupplierCatalogProductInstances
    productConverter.addSupplierCatalogProductInstances(product, spreeResponse);

    // Add Documents & Product Properties
    productConverter.addDocuments(product,
        exchange.getProperty(EXPROP_SPREE_DOCUMENT_DATA, List.class));
    productConverter.addProductProperties(product,
        exchange.getProperty(EXPROP_SPREE_PRODUCT_PROPS_DATA, List.class));

    // Convert meta data
    responseBuilder.meta(converter.convertTo(GetProductMeta.class, spreeResponse.get("meta")));

    return responseBuilder.product(product).build();
  }

}
