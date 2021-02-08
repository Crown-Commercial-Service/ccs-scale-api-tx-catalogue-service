package uk.gov.crowncommercial.dsd.api.catalogue.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

/**
 * Supplier
 */
@Value
public class Supplier {

  @JsonProperty("id")
  private String id;

  @JsonProperty("name")
  private String name;

  @JsonProperty("aboutUs")
  private String aboutUs;
}
