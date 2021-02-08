package uk.gov.crowncommercial.dsd.api.catalogue.model;

import java.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

/**
 * CommercialAgreementLot
 */
@Value
public class CommercialAgreementLot {
  @JsonProperty("id")
  private String id;

  @JsonProperty("fullName")
  private String fullName;

  @JsonProperty("caName")
  private String caName;

  @JsonProperty("caNumber")
  private String caNumber;

  @JsonProperty("lotNumber")
  private String lotNumber;

  @JsonProperty("lotExpiryDate")
  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
  private LocalDate lotExpiryDate;
}

