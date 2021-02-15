package uk.gov.crowncommercial.dsd.api.catalogue.logic;

import static java.lang.String.format;
import java.util.Arrays;
import java.util.Map;
import java.util.StringJoiner;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;

/**
 * Prepare the List Products request to Spree, mostly just translating accepted incoming query
 * parameters.
 */
@Component
@Slf4j
public class SpreeListProductsRequestComposer implements Processor {

  private static final String FILTER_PARAM_TEMPLATE = "filter[%s]";

  @Value("${spree.api.pass-through-params.list-products.filter}")
  private String[] passThroughFilterParams;

  @Value("${spree.api.pass-through-params.list-products.other}")
  private String[] passThroughOtherParams;

  @Override
  public void process(final Exchange exchange) throws Exception {
    final Map<String, Object> headers = exchange.getIn().getHeaders();
    final StringJoiner queryParams = new StringJoiner("&");

    log.info("passThroughFilterParams: " + passThroughFilterParams);

    Arrays.stream(passThroughFilterParams).map(s -> format(FILTER_PARAM_TEMPLATE, s))
        .filter(headers::containsKey).forEach(p -> queryParams.add(p + "=" + headers.get(p)));

    Arrays.stream(passThroughOtherParams).filter(headers::containsKey)
        .forEach(p -> queryParams.add(p + "=" + headers.get(p)));

    // Ensure we retrieve image links
    queryParams.add("include=image");

    exchange.getIn().setHeader(Exchange.HTTP_QUERY, queryParams.toString());
  }

}
