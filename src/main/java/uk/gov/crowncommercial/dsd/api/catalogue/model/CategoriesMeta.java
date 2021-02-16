package uk.gov.crowncommercial.dsd.api.catalogue.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;

/**
 *
 */
@Data
public class CategoriesMeta {

  private Long id;

  private String name;

  @JsonAlias("taxonomy_id")
  private Long taxonomyId;

  @JsonAlias("parent_id")
  private Long parentId;

  private Long count;

}
