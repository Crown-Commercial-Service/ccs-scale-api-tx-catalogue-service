package uk.gov.crowncommercial.dsd.api.catalogue.model;

import java.util.List;
import lombok.Data;

/**
 *
 */
@Data
public class PropertiesMeta {

  private Long id;

  private String name;

  private Long count;

  private List<PropertiesMetaValue> values;

}
