package uk.gov.crowncommercial.dsd.api.catalogue.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

/**
 * InlineObject
 */
@Value
public class InlineObject {
  @JsonProperty("user")
  private CatalogAccountUser user;
}

