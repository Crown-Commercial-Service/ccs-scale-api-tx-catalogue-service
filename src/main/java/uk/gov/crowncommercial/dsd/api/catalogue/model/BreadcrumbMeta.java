package uk.gov.crowncommercial.dsd.api.catalogue.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;

/**
 *
 */
@Data
public class BreadcrumbMeta {

  @JsonAlias("id")
  private Long categoryId;

  private String name;

}
