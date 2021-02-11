package uk.gov.crowncommercial.dsd.api.catalogue.model;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * Image
 */
@Data
public class Image {

  @JsonProperty("id")
  private String id;

  @JsonProperty("styles")
  private List<ImageStyle> styles = null;
}
