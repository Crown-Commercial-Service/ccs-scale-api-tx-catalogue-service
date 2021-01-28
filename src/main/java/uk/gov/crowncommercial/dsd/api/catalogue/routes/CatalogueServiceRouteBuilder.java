package uk.gov.crowncommercial.dsd.api.catalogue.routes;

import static org.springframework.http.HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.endpoint.EndpointRouteBuilder;
import org.apache.camel.builder.endpoint.dsl.RestEndpointBuilderFactory.RestBindingMode;
import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;
import uk.gov.crowncommercial.dsd.api.catalogue.model.ProductList;

/**
 *
 */
@Component
@RequiredArgsConstructor
public class CatalogueServiceRouteBuilder extends EndpointRouteBuilder {

  private static final String JSON_BINDING = RestBindingMode.json.name();
  private static final String PATH_ROOT = "/catalogue";

  private static final String ROUTE_ID_GET_PRODUCTS = "get-products";
  private static final String ROUTE_GET_PRODUCTS = "direct:" + ROUTE_ID_GET_PRODUCTS;
  private static final String ROUTE_FINALISE_RESPONSE = "direct:finalise-response";

  @Override
  public void configure() throws Exception {

    // @formatter:off
    restConfiguration()
      .component("servlet")
      .bindingMode(JSON_BINDING);

    /*
     * GET Products
     *
     * TODO: Expand to invoke Spree API v2 `api/v2/storefront/products` endpoint with filters etc and transform in accordance with:
     * https://github.com/Crown-Commercial-Service/ccs-scale-api-definitions/blob/SCA-1516-Catalog-Service-Iteration-1/catalogue/catalogue-service.yaml
     */
    rest()
      .get(PATH_ROOT + "/products")
      .outType(ProductList.class)
      .to(ROUTE_GET_PRODUCTS);

    from(ROUTE_GET_PRODUCTS)
      .routeId(ROUTE_ID_GET_PRODUCTS)
      .log(LoggingLevel.INFO, "Endpoint get-products invoked")

      // TODO: Invoke Spree: `.to(http://[SPREE_API_HOST]/etc)`
      // TODO: Transform response

      .setBody(e -> new ProductList())
      .to(ROUTE_FINALISE_RESPONSE);

    from(ROUTE_FINALISE_RESPONSE)
      .removeHeaders("*")
      .setHeader(ACCESS_CONTROL_ALLOW_ORIGIN, constant("*"));
    // @formatter:on
  }

}
