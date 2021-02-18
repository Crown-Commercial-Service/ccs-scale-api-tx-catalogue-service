package uk.gov.crowncommercial.dsd.api.catalogue;

import static io.restassured.RestAssured.given;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.CoreMatchers.is;
import static uk.gov.crowncommercial.dsd.api.catalogue.config.Constants.ENDPOINT_SPREE_API_GET_PRODUCT;
import org.apache.camel.EndpointInject;
import org.apache.camel.builder.AdviceWith;
import org.apache.camel.component.mock.MockEndpoint;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import io.restassured.http.ContentType;
import lombok.extern.slf4j.Slf4j;
import uk.gov.crowncommercial.dsd.api.catalogue.config.Constants;

@Slf4j
class GetProductApiTest extends AbstractApiTest {

  @Value("${api.paths.get-product}")
  private String apiGetProduct;

  @EndpointInject("mock:" + ENDPOINT_SPREE_API_GET_PRODUCT)
  protected MockEndpoint mockEndpointSpreeGetProduct;

  @Test
  void getProductAuthorised() throws Exception {

    log.info("mockEndpointSpreeGetProduct" + mockEndpointSpreeGetProduct);

    // Mock the behaviour of the Spree v2 API
    AdviceWith.adviceWith(camelContext, Constants.ROUTE_ID_GET_PRODUCT, builder -> {
      builder.weaveByToUri(ENDPOINT_SPREE_API_GET_PRODUCT + "*").replace().setBody().constant(
          getClass().getResourceAsStream(PATH_TEST_RESOURCES + "getProductAuthorised.json"));
    });

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

    // Notify Builder - assert mockEndpoint received the messages!!
  }

}
