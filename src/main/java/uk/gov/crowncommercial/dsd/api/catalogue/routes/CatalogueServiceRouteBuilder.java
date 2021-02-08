package uk.gov.crowncommercial.dsd.api.catalogue.routes;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.ValidationException;
import org.apache.camel.builder.endpoint.EndpointRouteBuilder;
import org.apache.camel.component.http.HttpMethods;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import uk.gov.crowncommercial.dsd.api.catalogue.logic.RequestValidator;
import uk.gov.crowncommercial.dsd.api.catalogue.model.ApiError;
import uk.gov.crowncommercial.dsd.api.catalogue.model.Errors;

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

  @Autowired
  private RequestValidator requestValidator;

  public static final String ROUTE_ID_GET_PRODUCTS = "get-products";
  private static final String ROUTE_GET_PRODUCTS = "direct:" + ROUTE_ID_GET_PRODUCTS;
  private static final String ROUTE_FINALISE_RESPONSE = "direct:finalise-response";

  @Override
  public void configure() throws Exception {

    // Permit repeated reading of stream bodies from external sources
    getContext().setStreamCaching(true);

    // @formatter:off
    restConfiguration()
      .component("servlet")
      .bindingMode(RestBindingMode.json)
      .clientRequestValidation(true)
      .enableCORS(true);

    // TODO: onException()....
    onException(ValidationException.class)
      .handled(true)
      .setBody(constant(Errors.builder().error(new ApiError(BAD_REQUEST.name(), BAD_REQUEST.getReasonPhrase(), "something invalid")).build()))
      .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(BAD_REQUEST.value()));

    onException(Exception.class)
      .handled(true)
      .setBody(constant(Errors.builder().error(new ApiError(INTERNAL_SERVER_ERROR.name(), INTERNAL_SERVER_ERROR.getReasonPhrase(), "Unexpected error")).build()))
      .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(INTERNAL_SERVER_ERROR.value()));

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

      // Validate request
      .process(requestValidator)
      // TODO: Prepare Spree request - anything much here?

      // Don't bridge - can set a predictable endpoint URI to identify the component in tests.
      .setHeader(Exchange.HTTP_METHOD, constant(HttpMethods.GET))
      .setHeader(Exchange.HTTP_URI, simple("{{SPREE_API_HOST}}{{spree.api.paths.base}}{{spree.api.paths.list-products}}"))

      // TODO: Extend
      .setHeader(Exchange.HTTP_QUERY, simple("filter[name]=${header.filter[name]}"))
      .to("log:DEBUG?showBody=false&showHeaders=true")

      // TODO: Replace with a header filter to strip `filter[*]` and other in params that end up as request headers otherwise (excl. Authorization)
      .removeHeaders("filter[name]")
      .to("http://spree-api")

      .unmarshal().json()
      .to("log:DEBUG?showBody=true&showHeaders=true")

      // TODO: Transform response
      .to(ROUTE_FINALISE_RESPONSE);

    from(ROUTE_FINALISE_RESPONSE)
      .removeHeaders("*");
    // @formatter:on
  }

}
