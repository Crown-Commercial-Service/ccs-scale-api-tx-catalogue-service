package uk.gov.crowncommercial.dsd.api.catalogue.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * InlineObject1
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen",
    date = "2021-02-01T13:35:17.494247Z[Europe/London]")
public class InlineObject1 {
  @JsonProperty("user")
  private CatalogAccountUser user;

  public InlineObject1 user(final CatalogAccountUser user) {
    this.user = user;
    return this;
  }

  /**
   * Get user
   *
   * @return user
   */

  public CatalogAccountUser getUser() {
    return user;
  }

  public void setUser(final CatalogAccountUser user) {
    this.user = user;
  }


  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    final InlineObject1 inlineObject1 = (InlineObject1) o;
    return Objects.equals(user, inlineObject1.user);
  }

  @Override
  public int hashCode() {
    return Objects.hash(user);
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder();
    sb.append("class InlineObject1 {\n");

    sb.append("    user: ").append(toIndentedString(user)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces (except the first line).
   */
  private String toIndentedString(final Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

