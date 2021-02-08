package uk.gov.crowncommercial.dsd.api.catalogue.model;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Singular;

/**
 * Errors
 */
@Builder
public class Errors {

  @JsonProperty("errors")
  @Singular
  private final List<ApiError> errors;

}
