package uk.gov.crowncommercial.dsd.api.catalogue.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

/**
 * Error
 */
@Value
public class Error {

  @JsonProperty("error")
  private String error;
}
