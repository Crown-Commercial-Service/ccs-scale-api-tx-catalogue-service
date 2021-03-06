package uk.gov.crowncommercial.dsd.api.catalogue.config;

import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.extern.slf4j.Slf4j;

/**
 * Jackson configuration
 */
@Configuration
@Slf4j
public class JacksonConfig {

  @Bean
  public Jackson2ObjectMapperBuilderCustomizer configureJackson() {
    log.debug("Configuring Jackson behaviour...");

    return jacksonObjectMapperBuilder -> {
      jacksonObjectMapperBuilder.serializationInclusion(Include.NON_NULL);
      jacksonObjectMapperBuilder.featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS,
          DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE,
          DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
      jacksonObjectMapperBuilder.propertyNamingStrategy(PropertyNamingStrategy.LOWER_CAMEL_CASE);
    };
  }

}
