package uk.gov.crowncommercial.dsd.api.catalogue.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

/**
 * ListLinks
 */
@Value
public class ListLinks {
  @JsonProperty("self")
  private String self;

  @JsonProperty("next")
  private String next;

  @JsonProperty("prev")
  private String prev;

  @JsonProperty("last")
  private String last;

  @JsonProperty("first")
  private String first;
}

