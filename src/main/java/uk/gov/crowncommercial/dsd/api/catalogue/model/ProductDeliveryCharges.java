package uk.gov.crowncommercial.dsd.api.catalogue.model;

import java.math.BigDecimal;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

/**
 * ProductDeliveryCharges
 */
@Value
public class ProductDeliveryCharges {

  @JsonProperty("id")
  private String id;

  @JsonProperty("standardChargeProductUkMainland")
  private String standardChargeProductUkMainland;

  @JsonProperty("standardChargeBasket&quot;")
  private String standardChargeBasketDoubleQuote;

  @JsonProperty("standardChargeProductUkNonMainland&quot;")
  private String standardChargeProductUkNonMainlandDoubleQuote;

  @JsonProperty("standardDeliveryTime&quot;")
  private BigDecimal standardDeliveryTimeDoubleQuote;

  @JsonProperty("nextDayChargeProductUkMainland&quot;")
  private String nextDayChargeProductUkMainlandDoubleQuote;

  @JsonProperty("nextDayChargeBasket&quot;")
  private String nextDayChargeBasketDoubleQuote;

  @JsonProperty("displayStandardChargeProductUkMainland&quot;")
  private String displayStandardChargeProductUkMainlandDoubleQuote;

  @JsonProperty("displayStandardChargeProductUkNonMainland&quot;")
  private String displayStandardChargeProductUkNonMainlandDoubleQuote;

  @JsonProperty("displayNextDayChargeProductUkMainland&quot;")
  private String displayNextDayChargeProductUkMainlandDoubleQuote;
}
