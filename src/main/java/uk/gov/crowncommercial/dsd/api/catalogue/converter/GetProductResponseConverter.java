package uk.gov.crowncommercial.dsd.api.catalogue.converter;

import static uk.gov.crowncommercial.dsd.api.catalogue.config.Constants.EXPROP_SPREE_IMAGE_DATA;
import java.util.List;
import java.util.Map;
import org.apache.camel.Converter;
import org.apache.camel.Exchange;
import org.apache.camel.TypeConverter;
import org.apache.camel.TypeConverters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
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
  public GetProductResponse toGetProductResponse(final Map<String, Object> data,
      final Exchange exchange) {

    final TypeConverter converter = exchange.getContext().getTypeConverter();
    final GetProductResponseBuilder responseBuilder = GetProductResponse.builder();

    // Basic product conversion
    final Product product =
        converter.convertTo(Product.class, (Map<String, Object>) data.get("data"));
    productConverter.addImagesToProduct(product,
        exchange.getProperty(EXPROP_SPREE_IMAGE_DATA, List.class));
    responseBuilder.product(product);

    // TODO: the other bits..

    return responseBuilder.build();
  }

}
