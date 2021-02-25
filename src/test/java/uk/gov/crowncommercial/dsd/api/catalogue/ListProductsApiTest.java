package uk.gov.crowncommercial.dsd.api.catalogue;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static io.restassured.RestAssured.given;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.http.HttpHeaders.ACCEPT;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import java.util.concurrent.TimeUnit;
import org.apache.camel.builder.NotifyBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import io.restassured.http.ContentType;

class ListProductsApiTest extends AbstractApiTest {

  @Value("${api.paths.list-products}")
  private String apiPathListProducts;

  @Value("${spree.api.paths.list-products}")
  private String spreeApiPathListProducts;

  @Test
  void listFilterIdsSingle() throws Exception {

    stubFor(get(urlPathEqualTo(spreeApiBasePath + spreeApiPathListProducts)).willReturn(aResponse()
        .withStatus(200).withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
        .withBodyFile("listFilterIdsSingle.json")));

    final NotifyBuilder notifyBuilder = new NotifyBuilder(camelContext).whenDone(1).create();

    /*
     * Single product filter, all Product attributes tested
     */
    // @formatter:off
    given()
      .header(AUTHORIZATION, AUTH_BEARER_TOKEN)
      .param("filter[ids]", "73")
      .param("filter[categories]", "101")
      .param("filter[price]", "<300")
      .param("include", "image")
      .param("filter[INCORRECT]", "foo")
    .when()
      .get(apiPathListProducts)
    .then()
      .statusCode(SC_OK)
      .contentType(ContentType.JSON)
      .body("products.size()", is(1))
      .body("products[0].id", is("73"))
      .body("products[0].name", is("Compaq Deskpro 6266X Model 3200 - Super Interwebs"))
      .body("products[0].description", is("Has floppy disk drive. Does internet."))
      .body("products[0].deliveryIncludedPrice", is("41.52"))
      .body("products[0].price", is("39.52"))
      .body("products[0].displayDeliveryIncludedPrice", is("£41.52"))
      .body("products[0].displayPrice", is("£39.52"))
      .body("products[0].currency", is("GBP"))
      .body("products[0].availableOn", is("2021-02-05T00:00:00Z"))
      .body("products[0].active", is(false))
      .body("products[0].available", is(true))
      .body("products[0].manufacturer", is("Compaq"))
      .body("products[0].mpnNumber", is("178902-054"))
      .body("products[0].purchasable", is(true))
      .body("products[0].inStock", is(true))
      .body("products[0].backorderable", is(false))
      .body("products[0].cnetId", is("S0000097"))
      .body("products[0].imageId", is("82581"))
      .body("products[0].slug", is("3bd11ad7-9f49-4ca2-af55-d1a7174fecc6"))
      .body("products[0].totalOnHand", is(50))
      .body("products[0].createdAt", is("2020-10-12T15:26:42.703+01:00"))
      .body("products[0].updatedAt", is("2021-02-05T13:44:25.406Z"))
      .body("products[0].images.size()", is(1))
      .body("products[0].images[0].id", is("82581"))
      .body("products[0].images[0].styles.size()", is(13));

    // @formatter:on

    // Assert exchange done before verifying external API call
    assertTrue(notifyBuilder.matches(5, TimeUnit.SECONDS));

    verify(1,
        getRequestedFor(urlPathEqualTo(spreeApiBasePath + spreeApiPathListProducts))
            .withHeader(ACCEPT, equalTo(MediaType.APPLICATION_JSON_VALUE))
            .withHeader(AUTHORIZATION, equalTo(AUTH_BEARER_TOKEN))
            .withQueryParam("filter[ids]", equalTo("73"))
            .withQueryParam("filter[taxons]", equalTo("101"))
            .withQueryParam("include", equalTo("image")));
  }

}
