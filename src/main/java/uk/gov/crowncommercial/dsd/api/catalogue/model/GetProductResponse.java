package uk.gov.crowncommercial.dsd.api.catalogue.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

/**
 * GetProductResponse
 */
@Value
public class GetProductResponse {
  @JsonProperty("product")
  private Product product;

  @JsonProperty("meta")
  private Object meta;
}

