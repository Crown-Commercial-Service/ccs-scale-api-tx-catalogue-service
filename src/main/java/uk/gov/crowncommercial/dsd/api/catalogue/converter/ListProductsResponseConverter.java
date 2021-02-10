package uk.gov.crowncommercial.dsd.api.catalogue.converter;

import java.util.List;
import java.util.Map;
import org.apache.camel.Converter;
import org.apache.camel.Exchange;
import org.apache.camel.TypeConverter;
import org.apache.camel.TypeConverters;
import org.springframework.stereotype.Component;
import uk.gov.crowncommercial.dsd.api.catalogue.model.ListLinks;
import uk.gov.crowncommercial.dsd.api.catalogue.model.ListProductsResponse;
import uk.gov.crowncommercial.dsd.api.catalogue.model.ListProductsResponse.ListProductsResponseBuilder;
import uk.gov.crowncommercial.dsd.api.catalogue.model.Product;
import uk.gov.crowncommercial.dsd.api.catalogue.model.ProductListMeta;

/**
 * Converter for Spree List Products response to CS {@link ListProductsResponse}
 */
@Component
public class ListProductsResponseConverter implements TypeConverters {

  @SuppressWarnings("unchecked")
  @Converter
  public ListProductsResponse toListProductResponse(final Map<String, Object> spreeResponse,
      final Exchange exchange) {

    final TypeConverter converter = exchange.getContext().getTypeConverter();
    final ListProductsResponseBuilder responseBuilder = ListProductsResponse.builder();

    // Convert products
    final List<Map<String, Object>> productData =
        (List<Map<String, Object>>) spreeResponse.get("data");
    productData.stream().map(pd -> converter.convertTo(Product.class, pd))
        .forEach(responseBuilder::product);

    // Convert meta data
    responseBuilder.meta(converter.convertTo(ProductListMeta.class, spreeResponse.get("meta")));

    // Convert list links
    responseBuilder.listLinks(converter.convertTo(ListLinks.class, spreeResponse.get("links")));

    return responseBuilder.build();
  }

}
