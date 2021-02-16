package uk.gov.crowncommercial.dsd.api.catalogue.model;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;

/**
 *
 */
@Data
public class Filters {

  @JsonAlias("taxons")
  private List<CategoriesMeta> categories;

  private List<ManufacturersMeta> manufacturers;

  private List<PropertiesMeta> properties;

}
