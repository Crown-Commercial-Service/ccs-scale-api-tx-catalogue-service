package uk.gov.crowncommercial.dsd.api.catalogue.model;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Singular;

/**
 * ListProductsResponse
 */
@Builder
public class ListProductsResponse {

  @JsonProperty("products")
  @Singular
  private final List<Product> products;

  @JsonProperty("meta")
  private final ProductListMeta meta;

  @JsonProperty("listLinks")
  private final ListLinks listLinks;
}
