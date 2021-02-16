package uk.gov.crowncommercial.dsd.api.catalogue.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.Data;

/**
 * Supplier
 */
@Data
public class Supplier {

  @Data
  private static class Attributes {

    @JsonProperty("name")
    private String name;

    @JsonProperty("aboutUs")
    @JsonAlias("about_us")
    private String aboutUs;
  }

  @JsonProperty("id")
  private String id;

  // Deserialize ONLY
  @JsonProperty(access = Access.WRITE_ONLY)
  private Attributes attributes;

  /**
   * Serialize unwrapped
   *
   * @return attributes
   */
  @JsonUnwrapped
  public Attributes getBasicAttributes() {
    return attributes;
  }
}
