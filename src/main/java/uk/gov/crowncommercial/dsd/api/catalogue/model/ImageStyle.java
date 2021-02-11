package uk.gov.crowncommercial.dsd.api.catalogue.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * ImageStyle
 */
@Data
public class ImageStyle {

  @JsonProperty("url")
  private String url;

  @JsonProperty("width")
  private Integer width;

  @JsonProperty("height")
  private Integer height;
}
