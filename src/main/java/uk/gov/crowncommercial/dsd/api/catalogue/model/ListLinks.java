package uk.gov.crowncommercial.dsd.api.catalogue.model;

import lombok.Data;

/**
 * ListLinks
 */
@Data
public class ListLinks {

  private String self;

  private String next;

  private String prev;

  private String last;

  private String first;
}
