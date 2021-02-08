package uk.gov.crowncommercial.dsd.api.catalogue.model;

import java.math.BigDecimal;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

/**
 * Product
 */
@Value
public class Product {

  @JsonProperty("name")
  private String name;

  @JsonProperty("description")
  private String description;

  @JsonProperty("deliveryIncludedPrice")
  private String deliveryIncludedPrice;

  @JsonProperty("price")
  private String price;

  @JsonProperty("currency")
  private String currency;

  @JsonProperty("displayDeliveryIncludedPrice")
  private String displayDeliveryIncludedPrice;

  @JsonProperty("displayPrice")
  private String displayPrice;

  @JsonProperty("keySellingPoints")
  private String keySellingPoints;

  @JsonProperty("availableOn")
  private java.sql.Timestamp availableOn;

  @JsonProperty("active")
  private Boolean active;

  @JsonProperty("available")
  private Boolean available;

  @JsonProperty("manufacturer")
  private String manufacturer;

  @JsonProperty("mpnNumber")
  private String mpnNumber;

  @JsonProperty("purchasable")
  private Boolean purchasable;

  @JsonProperty("inStock")
  private Boolean inStock;

  @JsonProperty("backorderable")
  private Boolean backorderable;

  @JsonProperty("cnetId")
  private String cnetId;

  @JsonProperty("defaultSupplierProductCatalogInstanceId")
  private String defaultSupplierProductCatalogInstanceId;

  @JsonProperty("imageId")
  private String imageId;

  @JsonProperty("slug")
  private String slug;

  @JsonProperty("totalOnHand")
  private BigDecimal totalOnHand;

  @JsonProperty("unspsc")
  private String unspsc;

  @JsonProperty("metaDescription")
  private String metaDescription;

  @JsonProperty("metaKeywords")
  private String metaKeywords;

  @JsonProperty("createdAt")
  private java.sql.Timestamp createdAt;

  @JsonProperty("updatedAt")
  private java.sql.Timestamp updatedAt;

  @JsonProperty("images")

  private List<Image> images = null;

  @JsonProperty("supplierCatalogProductInstances")
  private List<SupplierCatalogProductInstance> supplierCatalogProductInstances = null;

  @JsonProperty("documents")
  private List<Document> documents = null;

  @JsonProperty("productProperties")
  private List<ProductProperty> productProperties = null;

}
