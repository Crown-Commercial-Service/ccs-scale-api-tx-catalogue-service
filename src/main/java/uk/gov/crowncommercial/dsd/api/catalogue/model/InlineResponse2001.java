package uk.gov.crowncommercial.dsd.api.catalogue.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * InlineResponse2001
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen",
    date = "2021-02-01T13:35:17.494247Z[Europe/London]")
public class InlineResponse2001 {
  @JsonProperty("product")
  private Product product;

  @JsonProperty("meta")
  private Object meta;

  public InlineResponse2001 product(final Product product) {
    this.product = product;
    return this;
  }

  /**
   * Get product
   *
   * @return product
   */
  public Product getProduct() {
    return product;
  }

  public void setProduct(final Product product) {
    this.product = product;
  }

  public InlineResponse2001 meta(final Object meta) {
    this.meta = meta;
    return this;
  }

  /**
   * Get meta
   *
   * @return meta
   */
  public Object getMeta() {
    return meta;
  }

  public void setMeta(final Object meta) {
    this.meta = meta;
  }


  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    final InlineResponse2001 inlineResponse2001 = (InlineResponse2001) o;
    return Objects.equals(product, inlineResponse2001.product)
        && Objects.equals(meta, inlineResponse2001.meta);
  }

  @Override
  public int hashCode() {
    return Objects.hash(product, meta);
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder();
    sb.append("class InlineResponse2001 {\n");

    sb.append("    product: ").append(toIndentedString(product)).append("\n");
    sb.append("    meta: ").append(toIndentedString(meta)).append("\n");
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

