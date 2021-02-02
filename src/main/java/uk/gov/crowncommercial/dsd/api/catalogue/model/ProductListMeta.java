package uk.gov.crowncommercial.dsd.api.catalogue.model;

import java.math.BigDecimal;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

/**
 * ProductListMeta
 */
@Value
public class ProductListMeta {
  @JsonProperty("count")
  private BigDecimal count;

  @JsonProperty("total_count")
  private BigDecimal totalCount;

  @JsonProperty("total_pages")
  private BigDecimal totalPages;

  @JsonProperty("filters")
  private Object filters;

  @JsonProperty("breadcrumbs")
  private List<Object> breadcrumbs = null;
}

