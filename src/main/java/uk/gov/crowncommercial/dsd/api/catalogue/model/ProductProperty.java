package uk.gov.crowncommercial.dsd.api.catalogue.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

/**
 * ProductProperty
 */
@Value
public class ProductProperty {

  @JsonProperty("name")
  private String name;

  @JsonProperty("description")
  private String description;

  @JsonProperty("value")
  private String value;

  @JsonProperty("group")
  private String group;
}
