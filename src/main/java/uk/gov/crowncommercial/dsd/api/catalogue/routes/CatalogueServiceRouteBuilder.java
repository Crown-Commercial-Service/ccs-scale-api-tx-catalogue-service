package uk.gov.crowncommercial.dsd.api.catalogue.routes;

import static org.springframework.http.HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN;
import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.endpoint.EndpointRouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;
import uk.gov.crowncommercial.dsd.api.catalogue.model.ProductList;

/**
 *
 */
@Component
@RequiredArgsConstructor
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

    // @formatter:off
    restConfiguration()
      .component("servlet")
      .bindingMode(RestBindingMode.json);

    /*
     * GET Products
     *
     * TODO: Expand to invoke Spree API v2 `api/v2/storefront/products` endpoint with filters etc and transform in accordance with:
     * https://github.com/Crown-Commercial-Service/ccs-scale-api-definitions/blob/SCA-1516-Catalog-Service-Iteration-1/catalogue/catalogue-service.yaml
     */
    rest(apiBasePath)
      .get(apiListProducts)
      .outType(ProductList.class)
      .to(ROUTE_GET_PRODUCTS);

    from(ROUTE_GET_PRODUCTS)
      .routeId(ROUTE_ID_GET_PRODUCTS)
      .log(LoggingLevel.INFO, "Endpoint get-products invoked")

      // Don't bridge - can set a predictable endpoint URI to identify the component in tests.
      .setHeader(Exchange.HTTP_URI, constant("https://actual-spree-api-endpoint-injected/"))
      .to("http://spree-api")
      .log(LoggingLevel.INFO, "${body}")
      // TODO: Transform response

      .setBody(e -> new ProductList())
      .to(ROUTE_FINALISE_RESPONSE);

    from(ROUTE_FINALISE_RESPONSE)
      .removeHeaders("*")
      .setHeader(ACCESS_CONTROL_ALLOW_ORIGIN, constant("*"));
    // @formatter:on
  }

}
