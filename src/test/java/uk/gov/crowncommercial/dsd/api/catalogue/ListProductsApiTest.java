package uk.gov.crowncommercial.dsd.api.catalogue;

import static io.restassured.RestAssured.given;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.CoreMatchers.is;
import static uk.gov.crowncommercial.dsd.api.catalogue.config.Constants.ENDPOINT_SPREE_API_LIST_PRODUCTS;
import org.apache.camel.EndpointInject;
import org.apache.camel.builder.AdviceWith;
import org.apache.camel.component.mock.MockEndpoint;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import io.restassured.http.ContentType;
import lombok.extern.slf4j.Slf4j;
import uk.gov.crowncommercial.dsd.api.catalogue.config.Constants;

@Slf4j
class ListProductsApiTest extends AbstractApiTest {

  @Value("${api.paths.list-products}")
  private String apiListProducts;

  @EndpointInject("mock:" + ENDPOINT_SPREE_API_LIST_PRODUCTS)
  protected MockEndpoint mockEndpointSpreeListProducts;

  @Test
  void listFilterIdsSingle() throws Exception {

    log.info("mockEndpointSpreeListProducts" + mockEndpointSpreeListProducts);

    // Mock the behaviour of the Spree v2 API
    AdviceWith.adviceWith(camelContext, Constants.ROUTE_ID_LIST_PRODUCTS, builder -> {
      builder.weaveByToUri(ENDPOINT_SPREE_API_LIST_PRODUCTS + "*").replace().setBody().constant(
          getClass().getResourceAsStream(PATH_TEST_RESOURCES + "listFilterIdsSingle.json"));
    });

    /*
     * Single product filter, all Product attributes tested
     */
    // @formatter:off
    given()
      .param("filter[ids]", "73")
    .when()
      .get(apiListProducts)
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

    // Notify Builder - assert mockEndpoint received the messages!!
  }

}
