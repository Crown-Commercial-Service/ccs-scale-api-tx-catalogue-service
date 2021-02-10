package uk.gov.crowncommercial.dsd.api.catalogue.logic;

import java.util.Map;
import java.util.Set;
import java.util.StringJoiner;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

/**
 *
 */
@Component
public class SpreeProductListRequestComposer implements Processor {

  // TODO: Externalise in yaml
  private final String filterParamTemplate = "filter[%s]";
  private final Set<String> filterParamAttributes =
      Set.of("ids", "skus", "price", "manufacturers", "categories", "name", "in_stock");
  private final Set<String> otherParams =
      Set.of("filter[properties][property_id]", "sort_by", "page", "per_page");

  @Override
  public void process(final Exchange exchange) throws Exception {
    final Map<String, Object> headers = exchange.getIn().getHeaders();
    final StringJoiner queryParams = new StringJoiner("&");

    filterParamAttributes.stream().map(filterParamTemplate::formatted).filter(headers::containsKey)
        .forEach(p -> queryParams.add(p + '=' + headers.get(p)));

    otherParams.stream().filter(headers::containsKey)
        .forEach(p -> queryParams.add(p + '=' + headers.get(p)));

    exchange.getIn().setHeader(Exchange.HTTP_QUERY, queryParams.toString());
  }

}
