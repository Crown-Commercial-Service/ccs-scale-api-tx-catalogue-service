package uk.gov.crowncommercial.dsd.api.catalogue.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.io.File;
import java.io.IOException;
import java.time.OffsetDateTime;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.ActiveProfiles;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import uk.gov.crowncommercial.dsd.api.catalogue.model.Product;

/**
 *
 */
@SpringBootTest(webEnvironment = WebEnvironment.NONE)
@ActiveProfiles("test")
class ProductConverterTest {

  @Autowired
  private ObjectMapper objectMapper;

  @Autowired
  private ProductConverter productConverter;

  @Test
  void testProductConverterAllData() throws JsonParseException, JsonMappingException, IOException {

    // TODO: Read from /spree-json/Product.json
    final Map<String, Object> jsonSpreeProduct =
        objectMapper.readValue(new File("src/test/resources/spree-json/Product.json"),
            new TypeReference<Map<String, Object>>() {});

    final Product product = productConverter.toProduct(jsonSpreeProduct);

    assertEquals("35", product.getId());
    assertEquals("Compaq Deskpro 2000 Abacus", product.getName());
    assertEquals("It connects ye olde interwebs", product.getDescription());
    assertEquals("502.00", product.getDeliveryIncludedPrice());
    assertEquals("500.00", product.getPrice());
    assertEquals("USD", product.getCurrency());
    assertEquals("504.00", product.getDisplayDeliveryIncludedPrice());
    assertEquals("510.00", product.getDisplayPrice());
    assertEquals("keyboard, big monitor, mouse, floppy", product.getKeySellingPoints());
    assertEquals(OffsetDateTime.parse("2021-01-27T12:38:21.000+00:00"), product.getAvailableOn());
    assertEquals(false, product.getActive());
    assertEquals("Compaq", product.getManufacturer());
    assertEquals("244502-056", product.getMpnNumber());
    assertEquals(true, product.getPurchasable());
  }

}
