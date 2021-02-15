package uk.gov.crowncommercial.dsd.api.catalogue.model;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * Get Product meta (no defined schema type)
 */
@Data
public class GetProductMeta {

  @JsonProperty("breadcrumbs")
  private List<BreadcrumbMeta> breadcrumbs;
}
