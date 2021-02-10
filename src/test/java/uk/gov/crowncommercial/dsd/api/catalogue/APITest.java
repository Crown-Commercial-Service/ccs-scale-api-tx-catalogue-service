package uk.gov.crowncommercial.dsd.api.catalogue;

import static io.restassured.RestAssured.given;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.CoreMatchers.is;
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
import uk.gov.crowncommercial.dsd.api.catalogue.routes.CatalogueServiceRouteBuilder;

@RunWith(CamelSpringBootRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@MockEndpointsAndSkip("http://spree-api")
@ActiveProfiles("test")
public class APITest {

  @Value("${api.paths.base}")
  private String apiBasePath;

  @Value("${api.paths.list-products}")
  private String apiListProducts;

  @Autowired
  private CamelContext camelContext;

  @LocalServerPort
  private int port;

  @EndpointInject("mock:http://spree-api")
  protected MockEndpoint spree;

  @BeforeEach
  public void setUp() {
    RestAssured.port = port;
    RestAssured.basePath = apiBasePath;
  }

  @Test
  public void listProducts() throws Exception {

    // Mock the behaviour of the Spree v2 API
    AdviceWith.adviceWith(camelContext, CatalogueServiceRouteBuilder.ROUTE_ID_LIST_PRODUCTS,
        builder -> {
          builder.weaveByToUri("http://spree-api").replace().setBody()
              .constant("{\"products\": [], \"meta\": {}, \"listLinks\": {} }");
        });

    /*
     * RESTAssured Example
     */
    // @formatter:off
    given()
      .param("param1", "foo")
      // etc
    .when()
      .get(apiListProducts)
    .then()
      .statusCode(SC_OK)
      .contentType(ContentType.JSON)
      //.etc etc etc
      .body("products.size()", is(0));
    // @formatter:on
  }

}
