package uk.gov.crowncommercial.dsd.api.catalogue.model;

import java.math.BigDecimal;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Product
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen",
    date = "2021-02-01T13:35:17.494247Z[Europe/London]")
public class Product {
  @JsonProperty("name")
  private String name;

  @JsonProperty("description")
  private String description;

  @JsonProperty("delivery_included_price")
  private String deliveryIncludedPrice;

  @JsonProperty("price")
  private String price;

  @JsonProperty("currency")
  private String currency;

  @JsonProperty("display_delivery_included_price")
  private String displayDeliveryIncludedPrice;

  @JsonProperty("display_price")
  private String displayPrice;

  @JsonProperty("key_selling_points")
  private String keySellingPoints;

  @JsonProperty("available_on")
  private java.sql.Timestamp availableOn;

  @JsonProperty("active")
  private Boolean active;

  @JsonProperty("available")
  private Boolean available;

  @JsonProperty("manufacturer")
  private String manufacturer;

  @JsonProperty("mpn_number")
  private String mpnNumber;

  @JsonProperty("purchasable")
  private Boolean purchasable;

  @JsonProperty("in_stock")
  private Boolean inStock;

  @JsonProperty("backorderable")
  private Boolean backorderable;

  @JsonProperty("cnet_id")
  private String cnetId;

  @JsonProperty("default_supplier_product_catalog_instance_id")
  private String defaultSupplierProductCatalogInstanceId;

  @JsonProperty("image_id")
  private String imageId;

  @JsonProperty("slug")
  private String slug;

  @JsonProperty("total_on_hand")
  private BigDecimal totalOnHand;

  @JsonProperty("unspsc")
  private String unspsc;

  @JsonProperty("meta_description")
  private String metaDescription;

  @JsonProperty("meta_keywords")
  private String metaKeywords;

  @JsonProperty("created_at")
  private java.sql.Timestamp createdAt;

  @JsonProperty("updated_at")
  private java.sql.Timestamp updatedAt;

  @JsonProperty("images")

  private final List<Image> images = null;

  @JsonProperty("supplier_catalog_product_instances")
  private List<SupplierCatalogProductInstance> supplierCatalogProductInstances;

  @JsonProperty("documents")
  private List<Document> documents;

  @JsonProperty("product_properties")
  private List<ProductProperty> productProperties;

  public Product name(final String name) {
    this.name = name;
    return this;
  }
}

