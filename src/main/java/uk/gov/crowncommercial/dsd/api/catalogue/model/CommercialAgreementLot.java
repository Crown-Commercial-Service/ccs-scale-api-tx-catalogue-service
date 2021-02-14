package uk.gov.crowncommercial.dsd.api.catalogue.model;

import java.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.Data;
import lombok.Value;

/**
 * CommercialAgreementLot
 */
@Value
public class CommercialAgreementLot {

  @Data
  private static class Attributes {

    @JsonProperty("fullName")
    @JsonAlias("full_name")
    private String fullName;

    @JsonProperty("caName")
    @JsonAlias("ca_name")
    private String caName;

    @JsonProperty("caNumber")
    @JsonAlias("ca_ref")
    private String caNumber;

    @JsonProperty("lotNumber")
    @JsonAlias("lot_number")
    private String lotNumber;

    @JsonProperty("lotExpiryDate")
    @JsonAlias("lot_expiry_date")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate lotExpiryDate;
  }

  @JsonProperty("id")
  private String id;

  // Deserialize ONLY
  @JsonProperty(access = Access.WRITE_ONLY)
  private Attributes attributes;

  /**
   * Serialize unwrapped
   *
   * @return attributes
   */
  @JsonUnwrapped
  public Attributes getBasicAttributes() {
    return attributes;
  }

}
