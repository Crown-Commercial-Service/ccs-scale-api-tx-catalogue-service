package uk.gov.crowncommercial.dsd.api.catalogue.converter;

import java.util.Map;
import org.apache.camel.Converter;
import org.apache.camel.TypeConverters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.databind.ObjectMapper;
import uk.gov.crowncommercial.dsd.api.catalogue.model.ProductListMeta;

/**
 *
 */
@Component
public class ProductListMetaConverter implements TypeConverters {

  @Autowired
  private ObjectMapper objectMapper;

  @Converter
  public ProductListMeta toProduct(final Map<String, Object> data) {

    return objectMapper.convertValue(data, ProductListMeta.class);
  }

}
