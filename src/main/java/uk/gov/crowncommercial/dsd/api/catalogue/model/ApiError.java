package uk.gov.crowncommercial.dsd.api.catalogue.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

/**
 * ApiError
 */
@Value
public class ApiError {
  @JsonProperty("status")
  private String status;

  @JsonProperty("title")
  private String title;

  @JsonProperty("detail")
  private String detail;
}

