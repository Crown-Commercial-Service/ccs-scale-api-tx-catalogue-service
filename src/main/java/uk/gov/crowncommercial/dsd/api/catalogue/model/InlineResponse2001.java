package uk.gov.crowncommercial.dsd.api.catalogue.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

/**
 * InlineResponse2001
 */
@Value
public class InlineResponse2001 {
  @JsonProperty("product")
  private Product product;

  @JsonProperty("meta")
  private Object meta;
}

