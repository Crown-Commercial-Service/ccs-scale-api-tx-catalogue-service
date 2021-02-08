package uk.gov.crowncommercial.dsd.api.catalogue.model;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

/**
 * Account
 */
@Value
public class Account {
  @JsonProperty("email")
  private String email;

  @JsonProperty("addresses")
  private List<Address> addresses = null;
}

