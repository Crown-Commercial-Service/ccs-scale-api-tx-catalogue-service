package uk.gov.crowncommercial.dsd.api.catalogue.model;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * ProductListMeta
 */
@Data
public class ProductListMeta {

  @JsonProperty("count")
  private Long count;

  @JsonProperty("totalCount")
  @JsonAlias("total_count")
  private Long totalCount;

  @JsonProperty("totalPages")
  @JsonAlias("total_pages")
  private Long totalPages;

  @JsonProperty("filters")
  private Filters filters;

  @JsonProperty("breadcrumbs")
  private List<BreadcrumbMeta> breadcrumbs;
}
