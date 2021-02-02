package uk.gov.crowncommercial.dsd.api.catalogue.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

/**
 * Document
 */
@Value
public class Document {

  @JsonProperty("group")
  private String group;

  @JsonProperty("url")
  private String url;

  @JsonProperty("content_type")
  private String contentType;

}

