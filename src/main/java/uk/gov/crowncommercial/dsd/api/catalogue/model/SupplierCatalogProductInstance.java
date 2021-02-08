package uk.gov.crowncommercial.dsd.api.catalogue.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

/**
 * The combination of Supplier and Catalogue for this product. One record for each Supplier with
 * appropriate pricing for each Catalogue on which the supplier offers the product.
 */
@Value
public class SupplierCatalogProductInstance {

  @JsonProperty("supplier_product_catalog_instance_id")
  private String supplierProductCatalogInstanceId;

  @JsonProperty("sku")
  private String sku;

  @JsonProperty("price")
  private String price;

  @JsonProperty("currency")
  private String currency;

  @JsonProperty("displayPrice")
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
  private Boolean isMaster;

  @JsonProperty("purchasable")
  private Boolean purchasable;

  @JsonProperty("inStock")
  private Boolean inStock;

  @JsonProperty("backorderable")
  private Boolean backorderable;

  @JsonProperty("commercialAgreementLot")
  private CommercialAgreementLot commercialAgreementLot;

  @JsonProperty("productDeliveryCharges")
  private ProductDeliveryCharges productDeliveryCharges;

  @JsonProperty("supplier")
  private Supplier supplier;
}
