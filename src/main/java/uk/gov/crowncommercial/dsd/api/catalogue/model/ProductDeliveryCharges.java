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

  @JsonProperty("standard_charge_product_uk_mainland")
  private String standardChargeProductUkMainland;

  @JsonProperty("standard_charge_basket&quot;")
  private String standardChargeBasketDoubleQuote;

  @JsonProperty("standard_charge_product_uk_non_mainland&quot;")
  private String standardChargeProductUkNonMainlandDoubleQuote;

  @JsonProperty("standard_delivery_time&quot;")
  private BigDecimal standardDeliveryTimeDoubleQuote;

  @JsonProperty("next_day_charge_product_uk_mainland&quot;")
  private String nextDayChargeProductUkMainlandDoubleQuote;

  @JsonProperty("next_day_charge_basket&quot;")
  private String nextDayChargeBasketDoubleQuote;

  @JsonProperty("display_standard_charge_product_uk_mainland&quot;")
  private String displayStandardChargeProductUkMainlandDoubleQuote;

  @JsonProperty("display_standard_charge_product_uk_non_mainland&quot;")
  private String displayStandardChargeProductUkNonMainlandDoubleQuote;

  @JsonProperty("display_next_day_charge_product_uk_mainland&quot;")
  private String displayNextDayChargeProductUkMainlandDoubleQuote;

}

