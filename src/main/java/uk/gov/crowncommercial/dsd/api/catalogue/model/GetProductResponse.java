package uk.gov.crowncommercial.dsd.api.catalogue.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

/**
 * GetProductResponse
 */
@Builder
public class GetProductResponse {

  @JsonProperty("product")
  private final Product product;

  @JsonProperty("meta")
  private final ProductMeta meta;
}
