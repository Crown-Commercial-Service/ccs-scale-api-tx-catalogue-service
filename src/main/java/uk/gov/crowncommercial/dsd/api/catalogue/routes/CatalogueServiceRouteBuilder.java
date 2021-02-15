package uk.gov.crowncommercial.dsd.api.catalogue.routes;

import static java.lang.Boolean.TRUE;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static uk.gov.crowncommercial.dsd.api.catalogue.config.Constants.*;
import java.util.Map;
import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.ValidationException;
import org.apache.camel.builder.endpoint.EndpointRouteBuilder;
import org.apache.camel.component.http.HttpMethods;
import org.apache.camel.http.base.HttpOperationFailedException;
import org.apache.camel.model.rest.RestBindingMode;
import org.apache.camel.model.rest.RestParamType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriTemplate;
import lombok.RequiredArgsConstructor;
import uk.gov.crowncommercial.dsd.api.catalogue.logic.HttpOperationFailedExceptionProcessor;
import uk.gov.crowncommercial.dsd.api.catalogue.logic.RequestValidator;
import uk.gov.crowncommercial.dsd.api.catalogue.logic.SpreeListProductsRequestComposer;
import uk.gov.crowncommercial.dsd.api.catalogue.model.ApiError;
import uk.gov.crowncommercial.dsd.api.catalogue.model.ApiErrors;
import uk.gov.crowncommercial.dsd.api.catalogue.model.GetProductResponse;
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
  private String apiGetProduct;

  @Value("${spree.api.paths.get-product}")
  private String spreeApiPathsGetProduct;

  private final RequestValidator requestValidator;
  private final SpreeListProductsRequestComposer spreeListProductsRequestComposer;
  private final HttpOperationFailedExceptionProcessor httpExceptionProcessor;

  @Override
  public void configure() throws Exception {

    // Permit repeated reading of stream bodies from external sources
    getContext().setStreamCaching(true);

    // @formatter:off
    restConfiguration()
      .component("servlet")
      .bindingMode(RestBindingMode.json)
      .skipBindingOnErrorCode(false)
      .clientRequestValidation(true)
      .enableCORS(true);

    // TODO: Complete request validation handling logic..
    onException(ValidationException.class)
      .handled(true)
      .setBody(constant(ApiErrors.builder().error(new ApiError(BAD_REQUEST.name(), BAD_REQUEST.getReasonPhrase(), "something invalid")).build()))
      .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(BAD_REQUEST.value()))
      .to(ROUTE_FINALISE_RESPONSE);

    onException(HttpOperationFailedException.class)
      .handled(true)
      .process(httpExceptionProcessor)
      .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(INTERNAL_SERVER_ERROR.value()));

    onException(Exception.class)
      .handled(true)
      .logHandled(true)
      .setBody(constant(ApiErrors.builder().error(new ApiError(INTERNAL_SERVER_ERROR.name(), INTERNAL_SERVER_ERROR.getReasonPhrase(), "Unexpected error")).build()))
      .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(INTERNAL_SERVER_ERROR.value()))
      .to(ROUTE_FINALISE_RESPONSE);

    /*
     * List Products
     */
    rest(apiBasePath)
      .get(apiListProducts)
      .outType(ListProductsResponse.class)
      .to(ROUTE_LIST_PRODUCTS);

    from(ROUTE_LIST_PRODUCTS)
      .routeId(ROUTE_ID_LIST_PRODUCTS)
      .log(LoggingLevel.INFO, "Endpoint list-products invoked")

      // Validate request
      .process(requestValidator)
      .process(spreeListProductsRequestComposer)

      // Don't bridge - can set a predictable endpoint URI to identify the component in tests.
      .setHeader(Exchange.HTTP_METHOD, constant(HttpMethods.GET))
      .setHeader(Exchange.HTTP_URI, simple("{{SPREE_API_HOST}}{{spree.api.paths.base}}{{spree.api.paths.list-products}}"))
      .setHeader(HttpHeaders.ACCEPT, constant(MediaType.APPLICATION_JSON_VALUE))

      // Log headers prior to Spree API invocation
      .to("log:DEBUG?showBody=false&showHeaders=true")
      .to(ENDPOINT_SPREE_API_LIST_PRODUCTS +  "?headerFilterStrategy=#spreeApiHeaderFilter")
      //.to(ENDPOINT_SPREE_API_LIST_PRODUCTS)

      .unmarshal().json()
      .to("log:DEBUG?showBody=false&showHeaders=true")
      // Filter image data into exchange prop
      .setProperty(EXPROP_SPREE_IMAGE_DATA, jsonpath("$.included[?(@.type == 'image')]"))
      .convertBodyTo(ListProductsResponse.class)
      .to(ROUTE_FINALISE_RESPONSE);

    /*
     * Get product
     */
    rest(apiBasePath)
      .get(apiGetProduct)
      .outType(GetProductResponse.class)
      .param().name("id").type(RestParamType.path).required(TRUE).dataType("string").endParam()
      .to(ROUTE_GET_PRODUCT);

    from(ROUTE_GET_PRODUCT)
      .routeId(ROUTE_ID_GET_PRODUCT)
      .log(LoggingLevel.INFO, "Endpoint get-product invoked for product id: ${header.id}")

      // Validate request?
      .process(requestValidator)

      // Don't bridge - can set a predictable endpoint URI to identify the component in tests.
      .setHeader(Exchange.HTTP_METHOD, constant(HttpMethods.GET))

      .process(e -> e.getIn().setHeader("SpreeApiPathGetProductExpanded",
          new UriTemplate(spreeApiPathsGetProduct).expand(Map.of("id", e.getIn().getHeader("id")))))
      .log(LoggingLevel.INFO, "${header.SpreeApiPathGetProductExpanded}")
      .setHeader(Exchange.HTTP_URI,  simple("{{SPREE_API_HOST}}{{spree.api.paths.base}}${header.SpreeApiPathGetProductExpanded}"))
      .setHeader(HttpHeaders.ACCEPT, constant(MediaType.APPLICATION_JSON_VALUE))

      // Log headers prior to Spree API invocation
      .to("log:DEBUG?showBody=false&showHeaders=true")

      // TODO: Why necessary - interferes with invoked URL otherwise(?)
      .removeHeader(Exchange.HTTP_PATH)
      .to(ENDPOINT_SPREE_API_GET_PRODUCT + "?headerFilterStrategy=#spreeApiHeaderFilter")

      .unmarshal().json()
      .to("log:DEBUG?showBody=false&showHeaders=true")

      .setProperty(EXPROP_SPREE_IMAGE_DATA, jsonpath("$.included[?(@.type == 'image')]"))
      .setProperty(EXPROP_SPREE_DOCUMENT_DATA, jsonpath("$.included[?(@.type == 'document')].attributes"))
      .setProperty(EXPROP_SPREE_PRODUCT_PROPS_DATA, jsonpath("$.included[?(@.type == 'product_property')].attributes"))

      .convertBodyTo(GetProductResponse.class)

      .to(ROUTE_FINALISE_RESPONSE);

    from(ROUTE_FINALISE_RESPONSE)
      .removeHeaders("*");
    // @formatter:on
  }

}
