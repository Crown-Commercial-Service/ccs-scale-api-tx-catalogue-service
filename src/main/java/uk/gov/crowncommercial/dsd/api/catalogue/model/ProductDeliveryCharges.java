package uk.gov.crowncommercial.dsd.api.catalogue.model;

import java.math.BigDecimal;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.Data;

/**
 * ProductDeliveryCharges
 */
@Data
public class ProductDeliveryCharges {

  @Data
  private static class Attributes {

    @JsonProperty("standardChargeProductUkMainland")
    @JsonAlias("standard_charge_product_uk_mainland")
    private String standardChargeProductUkMainland;

    @JsonProperty("standardChargeBasket")
    @JsonAlias("standard_charge_basket")
    private String standardChargeBasketDoubleQuote;

    @JsonProperty("standardChargeProductUkNonMainland")
    @JsonAlias("standard_charge_product_uk_non_mainland")
    private String standardChargeProductUkNonMainlandDoubleQuote;

    @JsonProperty("standardDeliveryTime")
    @JsonAlias("standard_delivery_time")
    private BigDecimal standardDeliveryTimeDoubleQuote;

    @JsonProperty("nextDayChargeProductUkMainland")
    @JsonAlias("next_day_charge_product_uk_mainland")
    private String nextDayChargeProductUkMainlandDoubleQuote;

    @JsonProperty("nextDayChargeBasket")
    @JsonAlias("next_day_charge_basket")
    private String nextDayChargeBasketDoubleQuote;

    @JsonProperty("displayStandardChargeProductUkMainland")
    @JsonAlias("display_standard_charge_product_uk_mainland")
    private String displayStandardChargeProductUkMainlandDoubleQuote;

    @JsonProperty("displayStandardChargeProductUkNonMainland")
    @JsonAlias("display_standard_charge_product_uk_non_mainland")
    private String displayStandardChargeProductUkNonMainlandDoubleQuote;

    @JsonProperty("displayNextDayChargeProductUkMainland")
    @JsonAlias("display_next_day_charge_product_uk_mainland")
    private String displayNextDayChargeProductUkMainlandDoubleQuote;
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
