package uk.gov.crowncommercial.dsd.api.catalogue.routes;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static uk.gov.crowncommercial.dsd.api.catalogue.config.Constants.ROUTE_FINALISE_RESPONSE;
import static uk.gov.crowncommercial.dsd.api.catalogue.config.Constants.ROUTE_ID_LIST_PRODUCTS;
import static uk.gov.crowncommercial.dsd.api.catalogue.config.Constants.ROUTE_LIST_PRODUCTS;
import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.ValidationException;
import org.apache.camel.builder.endpoint.EndpointRouteBuilder;
import org.apache.camel.component.http.HttpMethods;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;
import uk.gov.crowncommercial.dsd.api.catalogue.config.Constants;
import uk.gov.crowncommercial.dsd.api.catalogue.logic.RequestValidator;
import uk.gov.crowncommercial.dsd.api.catalogue.logic.SpreeProductListRequestComposer;
import uk.gov.crowncommercial.dsd.api.catalogue.model.ApiError;
import uk.gov.crowncommercial.dsd.api.catalogue.model.Errors;
import uk.gov.crowncommercial.dsd.api.catalogue.model.ListProductsResponse;

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

  @Autowired
  private RequestValidator requestValidator;

  @Autowired
  private SpreeProductListRequestComposer spreeProductListRequestComposer;

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
      .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(BAD_REQUEST.value()))
      .to(ROUTE_FINALISE_RESPONSE);

    onException(Exception.class)
      .handled(true)
      .logHandled(true)
      .setBody(constant(Errors.builder().error(new ApiError(INTERNAL_SERVER_ERROR.name(), INTERNAL_SERVER_ERROR.getReasonPhrase(), "Unexpected error")).build()))
      .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(INTERNAL_SERVER_ERROR.value()))
      .to(ROUTE_FINALISE_RESPONSE);

    /*
     * GET Products
     *
     * TODO: Expand to invoke Spree API v2 `api/v2/storefront/products` endpoint with filters etc and transform in accordance with:
     * https://github.com/Crown-Commercial-Service/ccs-scale-api-definitions/blob/SCA-1516-Catalog-Service-Iteration-1/catalogue/catalogue-service.yaml
     */
    rest(apiBasePath)
      .get(apiListProducts)
      .skipBindingOnErrorCode(false)
      .outType(ListProductsResponse.class)
      .produces(Constants.MEDIATYPE_APP_VND_JSON)
      .to(ROUTE_LIST_PRODUCTS);

    from(ROUTE_LIST_PRODUCTS)
      .routeId(ROUTE_ID_LIST_PRODUCTS)
      .log(LoggingLevel.INFO, "Endpoint get-products invoked")

      // Validate request
      .process(requestValidator)
      .process(spreeProductListRequestComposer)

      // Don't bridge - can set a predictable endpoint URI to identify the component in tests.
      .setHeader(Exchange.HTTP_METHOD, constant(HttpMethods.GET))
      .setHeader(Exchange.HTTP_URI, simple("{{SPREE_API_HOST}}{{spree.api.paths.base}}{{spree.api.paths.list-products}}"))
      .setHeader(HttpHeaders.ACCEPT, constant(MediaType.APPLICATION_JSON_VALUE))

      // Log headers prior to Spree API invocation
      .to("log:DEBUG?showBody=false&showHeaders=true")
      .to("http://spree-api?headerFilterStrategy=#spreeApiHeaderFilter")

      .unmarshal().json()
      .to("log:DEBUG?showBody=false&showHeaders=true")
      .convertBodyTo(ListProductsResponse.class)
      .to(ROUTE_FINALISE_RESPONSE);

    from(ROUTE_FINALISE_RESPONSE)
      .removeHeaders("*");
    // @formatter:on
  }

}
