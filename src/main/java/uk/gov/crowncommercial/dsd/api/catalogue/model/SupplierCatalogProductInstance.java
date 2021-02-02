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

  @JsonProperty("display_price")
  private String displayPrice;

  @JsonProperty("weight")
  private String weight;

  @JsonProperty("height")
  private String height;

  @JsonProperty("width")
  private String width;

  @JsonProperty("depth")
  private String depth;

  @JsonProperty("is_master")
  private Boolean isMaster;

  @JsonProperty("purchasable")
  private Boolean purchasable;

  @JsonProperty("in_stock")
  private Boolean inStock;

  @JsonProperty("backorderable")
  private Boolean backorderable;

  @JsonProperty("commercial_agreement_lot")
  private CommercialAgreementLot commercialAgreementLot;

  @JsonProperty("product_delivery_charges")
  private ProductDeliveryCharges productDeliveryCharges;

  @JsonProperty("supplier")
  private Supplier supplier;

}

