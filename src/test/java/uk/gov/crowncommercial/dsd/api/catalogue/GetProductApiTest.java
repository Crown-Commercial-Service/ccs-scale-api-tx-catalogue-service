package uk.gov.crowncommercial.dsd.api.catalogue;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static io.restassured.RestAssured.given;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.Matchers.hasEntry;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.http.HttpHeaders.ACCEPT;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import org.apache.camel.builder.NotifyBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.util.UriComponentsBuilder;
import io.restassured.http.ContentType;

class GetProductApiTest extends AbstractApiTest {

  private static final String QUERY_PARAM_INCLUDE =
      "default_variant,default_variant.vendor,variants,variants.option_values,documents,variants.delivery_charges,"
          + "variants.vendor,variants.catalog,option_types,product_properties,images,manufacturer";

  @Value("${api.paths.get-product}")
  private String apiGetProduct;

  @Value("${spree.api.paths.get-product}")
  private String spreeApiPathGetProduct;

  @Test
  void getProductAuthorised() throws Exception {

    final int productId = 73;

    final UriComponentsBuilder uriBuilder =
        UriComponentsBuilder.fromUriString(spreeApiBasePath + spreeApiPathGetProduct);
    uriBuilder.uriVariables(Map.of("id", productId));
    final String spreeUri = uriBuilder.build().toString();

    stubFor(get(urlEqualTo(spreeUri)).willReturn(
        aResponse().withStatus(200).withHeader(CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .withBodyFile("getProductAuthorised.json")));

    final NotifyBuilder notifyBuilder = new NotifyBuilder(camelContext).whenDone(1).create();

    /*
     * Get product, all attributes tested
     */
    // @formatter:off
    given()
      .header(AUTHORIZATION, AUTH_BEARER_TOKEN)
    .when()
      .get(apiGetProduct, productId)
    .then()
      .statusCode(SC_OK)
      .contentType(ContentType.JSON)
      .body("product.id", is("73"))
      .body("product.name", is("Compaq Deskpro 6266X Model 3200 - Super Interwebs"))
      .body("product.description", is("Has floppy disk drive. Does internet."))
      .body("product.deliveryIncludedPrice", is("41.52"))
      .body("product.price", is("39.52"))
      .body("product.displayDeliveryIncludedPrice", is("£41.52"))
      .body("product.displayPrice", is("£39.52"))
      .body("product.currency", is("GBP"))
      .body("product.availableOn", is("2021-02-05T00:00:00Z"))
      .body("product.available", is(true))
      .body("product.mpnNumber", is("178902-054"))
      .body("product.purchasable", is(true))
      .body("product.inStock", is(true))
      .body("product.backorderable", is(false))
      .body("product.cnetId", is("S0000097"))
      .body("product.slug", is("3bd11ad7-9f49-4ca2-af55-d1a7174fecc6"))
      .body("product.totalOnHand", is(50))
      .body("product.metaDescription", is("meta description"))
      .body("product.metaKeywords", is("meta keywords"))
      .body("product.unspsc", is("44120000"))
      .body("product.updatedAt", is("2021-02-05T00:00:00Z"))
      .body("product.keySellingPoints", is("probably worth a bit now"))
      .body("product.images.size()", is(2))
      .body("product.images", hasItem(hasEntry("id", "82581")))
      .body("product.images", hasItem(hasEntry("id", "83887")));
      // TODO etc etc

    // @formatter:on

    // Assert exchange done before verifying external API call
    assertTrue(notifyBuilder.matches(5, TimeUnit.SECONDS));

    verify(1,
        getRequestedFor(urlEqualTo(spreeUri))
            .withHeader(ACCEPT, equalTo(MediaType.APPLICATION_JSON_VALUE))
            .withHeader(AUTHORIZATION, equalTo(AUTH_BEARER_TOKEN))
            .withQueryParam("include", equalTo(QUERY_PARAM_INCLUDE)));
  }

}
