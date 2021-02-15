package uk.gov.crowncommercial.dsd.api.catalogue.model;

import java.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.Value;

/**
 * CommercialAgreementLot
 */
@Value
public class CommercialAgreementLot {

  @Value
  private static class Attributes {

    @JsonAlias("full_name")
    private String fullName;

    @JsonAlias("ca_name")
    private String caName;

    @JsonAlias("ca_ref")
    private String caNumber;

    @JsonAlias("lot_number")
    private String lotNumber;

    @JsonAlias("lot_expiry_date")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate lotExpiryDate;
  }

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
