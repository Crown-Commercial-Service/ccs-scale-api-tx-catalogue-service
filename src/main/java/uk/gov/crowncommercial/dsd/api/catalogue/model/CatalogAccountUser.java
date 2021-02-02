package uk.gov.crowncommercial.dsd.api.catalogue.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

/**
 * CatalogAccountUser
 */
@Value
public class CatalogAccountUser {

  @JsonProperty("email")
  private String email;

  @JsonProperty("password")
  private String password;

  @JsonProperty("password_confirmation")
  private String passwordConfirmation;

}

