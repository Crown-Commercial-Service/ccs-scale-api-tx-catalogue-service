package uk.gov.crowncommercial.dsd.api.catalogue.model;

import java.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

/**
 * CommercialAgreementLot
 */
@Value
public class CommercialAgreementLot {

  @JsonProperty("id")
  private String id;

  @JsonProperty("full_name")
  private String fullName;

  @JsonProperty("ca_name")
  private String caName;

  @JsonProperty("ca_ref")
  private String caRef;

  @JsonProperty("lot_number")
  private String lotNumber;

  @JsonProperty("lot_expiry_date")
  @DateTimeFormat(iso = ISO.DATE)
  private LocalDate lotExpiryDate;

}

