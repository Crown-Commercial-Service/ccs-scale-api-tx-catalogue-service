package uk.gov.crowncommercial.dsd.api.catalogue.config;

/**
 * Global constant values
 */
public final class Constants {

  public static final String MEDIATYPE_APP_VND_JSON = "application/vnd.api+json";

  // Routing related
  public static final String ROUTE_ID_LIST_PRODUCTS = "list-products";
  public static final String ROUTE_LIST_PRODUCTS = "direct:" + ROUTE_ID_LIST_PRODUCTS;
  public static final String ROUTE_FINALISE_RESPONSE = "direct:finalise-response";

}
