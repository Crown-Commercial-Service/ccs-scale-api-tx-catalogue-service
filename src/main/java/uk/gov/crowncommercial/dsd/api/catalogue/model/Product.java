package uk.gov.crowncommercial.dsd.api.catalogue.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * Product
 */
@Data
public class Product {

  @JsonProperty("id")
  private String id;

  @JsonProperty("name")
  private String name;

  @JsonProperty("description")
  private String description;

  @JsonProperty("deliveryIncludedPrice")
  @JsonAlias("delivery_included_price")
  private String deliveryIncludedPrice;

  @JsonProperty("price")
  private String price;

  @JsonProperty("currency")
  private String currency;

  @JsonProperty("displayDeliveryIncludedPrice")
  @JsonAlias("display_delivery_included_price")
  private String displayDeliveryIncludedPrice;

  @JsonProperty("displayPrice")
  @JsonAlias("display_price")
  private String displayPrice;

  @JsonProperty("keySellingPoints")
  @JsonAlias("key_selling_points")
  private String keySellingPoints;

  @JsonProperty("availableOn")
  @JsonAlias("available_on")
  @DateTimeFormat(iso = ISO.DATE_TIME)
  private OffsetDateTime availableOn;

  @JsonProperty("active")
  private Boolean active;

  @JsonProperty("available")
  private Boolean available;

  @JsonProperty("manufacturer")
  private String manufacturer;

  @JsonProperty("mpnNumber")
  @JsonAlias("mpn_number")
  private String mpnNumber;

  @JsonProperty("purchasable")
  private Boolean purchasable;

  @JsonProperty("inStock")
  @JsonAlias("in_stock")
  private Boolean inStock;

  @JsonProperty("backorderable")
  private Boolean backorderable;

  @JsonProperty("cnetId")
  @JsonAlias("cnet_id")
  private String cnetId;

  @JsonProperty("defaultSupplierProductCatalogInstanceId")
  @JsonAlias("default_supplier_product_catalog_instance_id")
  private String defaultSupplierProductCatalogInstanceId;

  @JsonProperty("imageId")
  @JsonAlias("image_id")
  private String imageId;

  @JsonProperty("slug")
  private String slug;

  @JsonProperty("totalOnHand")
  @JsonAlias("total_on_hand")
  private BigDecimal totalOnHand;

  @JsonProperty("unspsc")
  private String unspsc;

  @JsonProperty("metaDescription")
  @JsonAlias("meta_description")
  private String metaDescription;

  @JsonProperty("metaKeywords")
  @JsonAlias("meta_keywords")
  private String metaKeywords;

  @JsonProperty("createdAt")
  @JsonAlias("created_at")
  @DateTimeFormat(iso = ISO.DATE_TIME)
  private OffsetDateTime createdAt;

  @JsonProperty("updatedAt")
  @JsonAlias("updated_at")
  @DateTimeFormat(iso = ISO.DATE_TIME)
  private OffsetDateTime updatedAt;

  @JsonProperty("images")
  private List<Image> images;

  @JsonProperty("supplierCatalogProductInstances")
  private List<SupplierCatalogProductInstance> supplierCatalogProductInstances;

  @JsonProperty("documents")
  private List<Document> documents;

  @JsonProperty("productProperties")
  private List<ProductProperty> productProperties;

}
