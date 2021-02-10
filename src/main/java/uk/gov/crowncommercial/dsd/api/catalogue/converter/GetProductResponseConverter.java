package uk.gov.crowncommercial.dsd.api.catalogue.converter;

import java.util.Map;
import org.apache.camel.Converter;
import org.apache.camel.Exchange;
import org.apache.camel.TypeConverter;
import org.springframework.stereotype.Component;
import uk.gov.crowncommercial.dsd.api.catalogue.model.GetProductResponse;
import uk.gov.crowncommercial.dsd.api.catalogue.model.GetProductResponse.GetProductResponseBuilder;
import uk.gov.crowncommercial.dsd.api.catalogue.model.Product;

/**
 * Converter for Spree Get Product response to CS {@link GetProductResponse}
 */
@Component
public class GetProductResponseConverter {

  @SuppressWarnings("unchecked")
  @Converter
  public GetProductResponse toGetProductResponse(final Map<String, Object> data,
      final Exchange exchange) {

    final TypeConverter converter = exchange.getContext().getTypeConverter();
    final GetProductResponseBuilder responseBuilder = GetProductResponse.builder();

    // Convert Products:
    final Map<String, Object> productData = (Map<String, Object>) data.get("data");
    responseBuilder.product(converter.convertTo(Product.class, productData));

    // TODO: meta

    return responseBuilder.build();
  }

}
