package uk.gov.crowncommercial.dsd.api.catalogue.model;

import lombok.Value;

/**
 * ProductProperty
 */
@Value
public class ProductProperty {

  private String name;
  private String description;
  private String value;
  private String group;
}
