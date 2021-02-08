package uk.gov.crowncommercial.dsd.api.catalogue.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

/**
 * InlineObject1
 */
@Value
public class InlineObject1 {
  @JsonProperty("user")
  private CatalogAccountUser user;
}

