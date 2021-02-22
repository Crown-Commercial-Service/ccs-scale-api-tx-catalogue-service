package uk.gov.crowncommercial.dsd.api.catalogue;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static io.restassured.RestAssured.given;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.concurrent.TimeUnit;
import org.apache.camel.builder.NotifyBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import io.restassured.http.ContentType;

class GetProductApiTest extends AbstractApiTest {

  @Value("${api.paths.get-product}")
  private String apiGetProduct;

  @Value("${spree.api.paths.get-product}")
  private String spreeApiPathGetProduct;

  @Test
  void getProductAuthorised() throws Exception {

    stubFor(get(urlPathMatching(spreeApiBasePath + spreeApiPathGetProduct)).willReturn(aResponse()
        .withStatus(200).withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
        .withBodyFile("getProductAuthorised.json")));

    final NotifyBuilder notifyBuilder = new NotifyBuilder(camelContext).whenDone(1).create();

    /*
     * Get product, all attributes tested
     */
    // @formatter:off
    given()
    .when()
      .get(apiGetProduct, "73")
    .then()
      .statusCode(SC_OK)
      .contentType(ContentType.JSON)
      .body("product.id", is("73"));
      // TODO etc etc

    // @formatter:on

    // Assert NotifyBBuilder and mock spree endpoint satisfied
    assertTrue(notifyBuilder.matches(5, TimeUnit.SECONDS));

    verify(1, getRequestedFor(urlPathMatching(spreeApiBasePath + spreeApiPathGetProduct))
        .withHeader("Accept", equalTo(MediaType.APPLICATION_JSON_VALUE)));
  }

}
