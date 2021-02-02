package uk.gov.crowncommercial.dsd.api.catalogue.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * ListLinks
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen",
    date = "2021-02-01T13:35:17.494247Z[Europe/London]")
public class ListLinks {
  @JsonProperty("self")
  private String self;

  @JsonProperty("next")
  private String next;

  @JsonProperty("prev")
  private String prev;

  @JsonProperty("last")
  private String last;

  @JsonProperty("first")
  private String first;

  public ListLinks self(final String self) {
    this.self = self;
    return this;
  }

  /**
   * URL to the current page of the listing
   *
   * @return self
   */
  public String getSelf() {
    return self;
  }

  public void setSelf(final String self) {
    this.self = self;
  }

  public ListLinks next(final String next) {
    this.next = next;
    return this;
  }

  /**
   * URL to the next page of the listing
   *
   * @return next
   */
  public String getNext() {
    return next;
  }

  public void setNext(final String next) {
    this.next = next;
  }

  public ListLinks prev(final String prev) {
    this.prev = prev;
    return this;
  }

  /**
   * URL to the previous page of the listing
   *
   * @return prev
   */
  public String getPrev() {
    return prev;
  }

  public void setPrev(final String prev) {
    this.prev = prev;
  }

  public ListLinks last(final String last) {
    this.last = last;
    return this;
  }

  /**
   * URL to the last page of the listing
   *
   * @return last
   */
  public String getLast() {
    return last;
  }

  public void setLast(final String last) {
    this.last = last;
  }

  public ListLinks first(final String first) {
    this.first = first;
    return this;
  }

  /**
   * URL to the first page of the listing
   *
   * @return first
   */
  public String getFirst() {
    return first;
  }

  public void setFirst(final String first) {
    this.first = first;
  }


  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    final ListLinks listLinks = (ListLinks) o;
    return Objects.equals(self, listLinks.self) && Objects.equals(next, listLinks.next)
        && Objects.equals(prev, listLinks.prev) && Objects.equals(last, listLinks.last)
        && Objects.equals(first, listLinks.first);
  }

  @Override
  public int hashCode() {
    return Objects.hash(self, next, prev, last, first);
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder();
    sb.append("class ListLinks {\n");

    sb.append("    self: ").append(toIndentedString(self)).append("\n");
    sb.append("    next: ").append(toIndentedString(next)).append("\n");
    sb.append("    prev: ").append(toIndentedString(prev)).append("\n");
    sb.append("    last: ").append(toIndentedString(last)).append("\n");
    sb.append("    first: ").append(toIndentedString(first)).append("\n");
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

