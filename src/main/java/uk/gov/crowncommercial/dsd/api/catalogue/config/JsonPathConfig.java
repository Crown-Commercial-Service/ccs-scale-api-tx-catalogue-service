package uk.gov.crowncommercial.dsd.api.catalogue.config;

import java.util.EnumSet;
import java.util.Set;
import javax.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.Option;
import com.jayway.jsonpath.spi.json.JacksonJsonProvider;
import com.jayway.jsonpath.spi.json.JsonProvider;
import com.jayway.jsonpath.spi.mapper.JacksonMappingProvider;
import com.jayway.jsonpath.spi.mapper.MappingProvider;
import lombok.RequiredArgsConstructor;

/**
 * TODO: Javadoc
 */
@Configuration
@RequiredArgsConstructor
public class JsonPathConfig {

  private final ObjectMapper objectMapper;

  @PostConstruct
  public void configureJsonPath() {
    com.jayway.jsonpath.Configuration.setDefaults(new com.jayway.jsonpath.Configuration.Defaults() {

      private final JsonProvider jsonProvider = new JacksonJsonProvider(objectMapper);
      private final MappingProvider mappingProvider = new JacksonMappingProvider(objectMapper);

      @Override
      public JsonProvider jsonProvider() {
        return jsonProvider;
      }

      @Override
      public MappingProvider mappingProvider() {
        return mappingProvider;
      }

      @Override
      public Set<Option> options() {
        return EnumSet.noneOf(Option.class);
      }
    });
  }

}
