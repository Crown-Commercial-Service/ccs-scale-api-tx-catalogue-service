package uk.gov.crowncommercial.dsd.api.catalogue.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * InlineResponse200
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen",
    date = "2021-02-01T13:35:17.494247Z[Europe/London]")
public class InlineResponse200 {
  @JsonProperty("products")
  private List<Product> products = null;

  @JsonProperty("meta")
  private ProductListMeta meta;

  @JsonProperty("list_links")
  private ListLinks listLinks;

  public InlineResponse200 products(final List<Product> products) {
    this.products = products;
    return this;
  }

  public InlineResponse200 addProductsItem(final Product productsItem) {
    if (products == null) {
      products = new ArrayList<>();
    }
    products.add(productsItem);
    return this;
  }

  /**
   * Get products
   *
   * @return products
   */
  public List<Product> getProducts() {
    return products;
  }

  public void setProducts(final List<Product> products) {
    this.products = products;
  }

  public InlineResponse200 meta(final ProductListMeta meta) {
    this.meta = meta;
    return this;
  }

  /**
   * Get meta
   *
   * @return meta
   */
  public ProductListMeta getMeta() {
    return meta;
  }

  public void setMeta(final ProductListMeta meta) {
    this.meta = meta;
  }

  public InlineResponse200 listLinks(final ListLinks listLinks) {
    this.listLinks = listLinks;
    return this;
  }

  /**
   * Get listLinks
   *
   * @return listLinks
   */
  public ListLinks getListLinks() {
    return listLinks;
  }

  public void setListLinks(final ListLinks listLinks) {
    this.listLinks = listLinks;
  }


  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    final InlineResponse200 inlineResponse200 = (InlineResponse200) o;
    return Objects.equals(products, inlineResponse200.products)
        && Objects.equals(meta, inlineResponse200.meta)
        && Objects.equals(listLinks, inlineResponse200.listLinks);
  }

  @Override
  public int hashCode() {
    return Objects.hash(products, meta, listLinks);
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder();
    sb.append("class InlineResponse200 {\n");

    sb.append("    products: ").append(toIndentedString(products)).append("\n");
    sb.append("    meta: ").append(toIndentedString(meta)).append("\n");
    sb.append("    listLinks: ").append(toIndentedString(listLinks)).append("\n");
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

