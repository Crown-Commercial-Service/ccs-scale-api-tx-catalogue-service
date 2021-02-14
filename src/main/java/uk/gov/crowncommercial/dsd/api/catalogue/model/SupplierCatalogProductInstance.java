package uk.gov.crowncommercial.dsd.api.catalogue.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.Data;

/**
 * The combination of Supplier and Catalogue for this product. One record for each Supplier with
 * appropriate pricing for each Catalogue on which the supplier offers the product.
 */
@Data
public class SupplierCatalogProductInstance {

  @Data
  private static class Attributes {

    @JsonProperty("sku")
    private String sku;

    @JsonProperty("price")
    private String price;

    @JsonProperty("currency")
    private String currency;

    @JsonProperty("displayPrice")
    @JsonAlias("display_price")
    private String displayPrice;

    @JsonProperty("weight")
    private String weight;

    @JsonProperty("height")
    private String height;

    @JsonProperty("width")
    private String width;

    @JsonProperty("depth")
    private String depth;

    @JsonProperty("isMaster")
    @JsonAlias("is_master")
    private Boolean isMaster;

    @JsonProperty("purchasable")
    private Boolean purchasable;

    @JsonProperty("inStock")
    @JsonAlias("in_stock")
    private Boolean inStock;

    @JsonProperty("backorderable")
    private Boolean backorderable;
  }

  @JsonProperty("supplierProductCatalogInstanceId")
  @JsonAlias("id")
  private String supplierProductCatalogInstanceId;

  // Deserialize ONLY
  @JsonProperty(access = Access.WRITE_ONLY)
  private Attributes attributes;

  @JsonProperty("commercialAgreementLot")
  private CommercialAgreementLot commercialAgreementLot;

  @JsonProperty("productDeliveryCharges")
  private ProductDeliveryCharges productDeliveryCharges;

  @JsonProperty("supplier")
  private Supplier supplier;

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
