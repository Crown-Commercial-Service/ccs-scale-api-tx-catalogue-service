package uk.gov.crowncommercial.dsd.api.catalogue.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

/**
 * Address
 */
@Value
public class Address {

  @JsonProperty("id")
  private String id;

  @JsonProperty("firstname")
  private String firstname;

  @JsonProperty("lastname")
  private String lastname;

  @JsonProperty("address1")
  private String address1;

  @JsonProperty("address2")
  private String address2;

  @JsonProperty("city")
  private String city;

  @JsonProperty("postcode")
  private String postcode;

  @JsonProperty("phone")
  private String phone;

  @JsonProperty("county")
  private String county;

  @JsonProperty("country_name")
  private String countryName;

  @JsonProperty("country_iso3")
  private String countryIso3;

  @JsonProperty("company")
  private String company;

  @JsonProperty("default_billing_address")
  private Boolean defaultBillingAddress;

  @JsonProperty("default_shipping_address")
  private Boolean defaultShippingAddress;

}

