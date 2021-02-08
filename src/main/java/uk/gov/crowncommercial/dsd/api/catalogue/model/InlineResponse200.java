package uk.gov.crowncommercial.dsd.api.catalogue.model;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

/**
 * InlineResponse200
 */
@Value
public class InlineResponse200 {
  @JsonProperty("products")
  private List<Product> products = null;

  @JsonProperty("meta")
  private ProductListMeta meta;

  @JsonProperty("listLinks")
  private ListLinks listLinks;
}

