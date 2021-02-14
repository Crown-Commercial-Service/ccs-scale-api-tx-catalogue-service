package uk.gov.crowncommercial.dsd.api.catalogue.model;

import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.TypeRef;
import lombok.Data;

/**
 * Image
 */
@Data
@JsonPropertyOrder({"id", "styles"})
public class Image {

  @JsonProperty("id")
  private String id;

  private List<ImageStyle> styles;

  @JsonProperty("styles")
  @JsonAlias("attributes")
  public void unpackImageData(final Map<String, Object> imageData) {
    styles = JsonPath.parse(imageData).read("$.styles", new TypeRef<List<ImageStyle>>() {});
  }
}
