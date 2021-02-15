package uk.gov.crowncommercial.dsd.api.catalogue;

import static io.restassured.RestAssured.given;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.CoreMatchers.is;
import static uk.gov.crowncommercial.dsd.api.catalogue.config.Constants.ENDPOINT_SPREE_API_LIST_PRODUCTS;
import org.apache.camel.CamelContext;
import org.apache.camel.EndpointInject;
import org.apache.camel.builder.AdviceWith;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.spring.CamelSpringBootRunner;
import org.apache.camel.test.spring.MockEndpointsAndSkip;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import uk.gov.crowncommercial.dsd.api.catalogue.config.Constants;

@RunWith(CamelSpringBootRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@MockEndpointsAndSkip("http://spree-api*")
@ActiveProfiles("test")
class APITest {

  @Value("${api.paths.base}")
  private String apiBasePath;

  @Value("${api.paths.list-products}")
  private String apiListProducts;

  @Autowired
  private CamelContext camelContext;

  @LocalServerPort
  private int port;

  @EndpointInject("mock:" + ENDPOINT_SPREE_API_LIST_PRODUCTS)
  protected MockEndpoint mockEndpointSpreeListProducts;

  @BeforeEach
  public void setUp() {
    RestAssured.port = port;
    RestAssured.basePath = apiBasePath;
  }

  @Test
  void listProducts() throws Exception {

    // Mock the behaviour of the Spree v2 API
    AdviceWith.adviceWith(camelContext, Constants.ROUTE_ID_LIST_PRODUCTS, builder -> {
      builder.weaveByToUri(ENDPOINT_SPREE_API_LIST_PRODUCTS + "*").replace().setBody()
          .constant(getClass().getResourceAsStream("/spree-json/ListProductsSingle.json"));
    });

    /*
     * RESTAssured Example
     */
    // @formatter:off
    given()
      .param("filter[ids]", "3")
      // etc
    .when()
      .get(apiListProducts)
    .then()
      .statusCode(SC_OK)
      .contentType(ContentType.JSON)
      //.etc etc etc
      .body("products.size()", is(1));
    // @formatter:on
  }

}
