package uk.gov.crowncommercial.dsd.api.catalogue.logic;

import java.util.List;
import java.util.Map;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.web.util.UriComponentsBuilder;
import lombok.RequiredArgsConstructor;

/**
 * Prepare the List Products request to Spree, mostly just translating accepted incoming query
 * parameters.
 */
@ConfigurationProperties("spree.api.query-params.list-products")
@ConstructorBinding
@RequiredArgsConstructor
public class SpreeListProductsRequestComposer implements Processor {

  private final List<String> passThrough;
  private final Map<String, String> mapped;
  private final Map<String, String> fixed;

  @Override
  public void process(final Exchange exchange) throws Exception {
    final Map<String, Object> headers = exchange.getIn().getHeaders();
    final UriComponentsBuilder uriBuilder = UriComponentsBuilder.newInstance();

    passThrough.stream().filter(headers::containsKey)
        .forEach(p -> uriBuilder.queryParam(p, headers.get(p)));

    mapped.entrySet().stream().filter(es -> headers.containsKey(es.getKey()))
        .forEach(es -> uriBuilder.queryParam(es.getValue(), headers.get(es.getKey())));

    fixed.entrySet().stream().forEach(es -> uriBuilder.queryParam(es.getKey(), es.getValue()));

    final String queryString = uriBuilder.build().getQuery();
    exchange.getIn().setHeader(Exchange.HTTP_QUERY, queryString);
  }

}
