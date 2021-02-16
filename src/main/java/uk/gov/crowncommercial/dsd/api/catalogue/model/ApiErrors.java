package uk.gov.crowncommercial.dsd.api.catalogue.model;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Singular;

/**
 * ApiErrors
 */
@Builder
public class ApiErrors {

  @JsonProperty("errors")
  @Singular
  private final List<ApiError> errors;

}
