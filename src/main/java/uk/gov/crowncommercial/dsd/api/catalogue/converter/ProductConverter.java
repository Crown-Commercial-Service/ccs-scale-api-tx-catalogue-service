package uk.gov.crowncommercial.dsd.api.catalogue.converter;

import java.util.Map;
import org.apache.camel.Converter;
import org.apache.camel.TypeConverters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.databind.ObjectMapper;
import uk.gov.crowncommercial.dsd.api.catalogue.model.Product;

/**
 *
 */
@Component
public class ProductConverter implements TypeConverters {

  @Autowired
  private ObjectMapper objectMapper;

  @SuppressWarnings("unchecked")
  @Converter
  public Product toProduct(final Map<String, Object> data) {

    final Product product =
        objectMapper.convertValue((Map<String, Object>) data.get("attributes"), Product.class);
    product.setId((String) data.get("id"));

    return product;
  }

}
