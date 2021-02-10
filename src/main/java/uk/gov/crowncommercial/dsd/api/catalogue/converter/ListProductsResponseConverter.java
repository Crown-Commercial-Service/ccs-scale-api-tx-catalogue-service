package uk.gov.crowncommercial.dsd.api.catalogue.converter;

import java.util.List;
import java.util.Map;
import org.apache.camel.Converter;
import org.apache.camel.Exchange;
import org.apache.camel.TypeConverter;
import org.apache.camel.TypeConverters;
import org.springframework.stereotype.Component;
import uk.gov.crowncommercial.dsd.api.catalogue.model.ListProductsResponse;
import uk.gov.crowncommercial.dsd.api.catalogue.model.ListProductsResponse.ListProductsResponseBuilder;
import uk.gov.crowncommercial.dsd.api.catalogue.model.Product;

/**
 * Converter Spree List Products response to CS {@link ListProductsResponse}
 */
@Component
public class ListProductsResponseConverter implements TypeConverters {

  @SuppressWarnings("unchecked")
  @Converter
  public ListProductsResponse toListProductResponse(final Map<String, Object> data,
      final Exchange exchange) {

    final TypeConverter converter = exchange.getContext().getTypeConverter();
    final ListProductsResponseBuilder responseBuilder = ListProductsResponse.builder();

    // Convert Products:
    final List<Map<String, Object>> productData = (List<Map<String, Object>>) data.get("data");
    productData.stream().map(pd -> converter.convertTo(Product.class, pd))
        .forEach(responseBuilder::product);

    return responseBuilder.build();
  }

}
