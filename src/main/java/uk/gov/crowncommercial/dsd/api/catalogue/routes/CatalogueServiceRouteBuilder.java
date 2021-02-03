package uk.gov.crowncommercial.dsd.api.catalogue.routes;

import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.endpoint.EndpointRouteBuilder;
import org.apache.camel.component.http.HttpMethods;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 *
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class CatalogueServiceRouteBuilder extends EndpointRouteBuilder {

  @Value("${api.paths.base}")
  private String apiBasePath;

  @Value("${api.paths.list-products}")
  private String apiListProducts;

  @Value("${api.paths.get-product}")
  private String apiGetProducts;

  public static final String ROUTE_ID_GET_PRODUCTS = "get-products";
  private static final String ROUTE_GET_PRODUCTS = "direct:" + ROUTE_ID_GET_PRODUCTS;
  private static final String ROUTE_FINALISE_RESPONSE = "direct:finalise-response";

  @Override
  public void configure() throws Exception {

    log.info("PATH: " + apiBasePath + apiListProducts);

    getContext().setStreamCaching(true);

    // @formatter:off
    restConfiguration()
      .component("servlet")
      .bindingMode(RestBindingMode.json)
      .enableCORS(true);

    // TODO: onException()....

    /*
     * GET Products
     *
     * TODO: Expand to invoke Spree API v2 `api/v2/storefront/products` endpoint with filters etc and transform in accordance with:
     * https://github.com/Crown-Commercial-Service/ccs-scale-api-definitions/blob/SCA-1516-Catalog-Service-Iteration-1/catalogue/catalogue-service.yaml
     */
    rest(apiBasePath)
      .get(apiListProducts)
      .skipBindingOnErrorCode(false)
//      .outType(ProductList.class)
      .to(ROUTE_GET_PRODUCTS);

    from(ROUTE_GET_PRODUCTS)
      .routeId(ROUTE_ID_GET_PRODUCTS)
      .log(LoggingLevel.INFO, "Endpoint get-products invoked")

      // Don't bridge - can set a predictable endpoint URI to identify the component in tests.
      .setHeader(Exchange.HTTP_METHOD, constant(HttpMethods.GET))
      .setHeader(Exchange.HTTP_URI, simple("{{spree.api.url}}{{spree.api.paths.base}}{{spree.api.paths.list-products}}"))
      .setHeader(Exchange.HTTP_QUERY, constant("filter[name]=\"Compaq Rack 4136\""))

      // Pass through Bearer auth if given
      .setHeader("Authorization", simple("${header.Authorization}"))

      //.to("log:DEBUG?showBody=true&showHeaders=true")
      .to("http://spree-api")
      .unmarshal().json()
      .log(LoggingLevel.INFO, "${body}")
      // TODO: Transform response

      .to(ROUTE_FINALISE_RESPONSE);

    from(ROUTE_FINALISE_RESPONSE)
      .removeHeaders("*")
      .log(LoggingLevel.INFO, "${body}");
    // @formatter:on
  }

}
