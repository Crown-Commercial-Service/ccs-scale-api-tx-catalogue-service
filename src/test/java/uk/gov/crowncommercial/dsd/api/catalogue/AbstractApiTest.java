package uk.gov.crowncommercial.dsd.api.catalogue;

import org.apache.camel.CamelContext;
import org.apache.camel.test.spring.junit5.MockEndpointsAndSkip;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import io.restassured.RestAssured;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@MockEndpointsAndSkip("http://spree-api*")
@ActiveProfiles("test")
@DirtiesContext
abstract class AbstractApiTest {

  static final String PATH_TEST_RESOURCES = "/spree-json/";

  @Value("${api.paths.base}")
  String apiBasePath;

  @Autowired
  CamelContext camelContext;

  @LocalServerPort
  int port;

  @BeforeEach
  public void setUp() {
    RestAssured.port = port;
    RestAssured.basePath = apiBasePath;
  }

}
