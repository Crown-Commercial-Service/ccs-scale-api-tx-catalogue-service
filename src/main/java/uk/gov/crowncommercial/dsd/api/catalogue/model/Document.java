package uk.gov.crowncommercial.dsd.api.catalogue.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Value;

/**
 * Document
 */
@Value
public class Document {

  private String group;
  private String url;

  @JsonAlias("content_type")
  private String contentType;
}
