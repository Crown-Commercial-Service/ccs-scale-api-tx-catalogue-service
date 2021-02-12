package uk.gov.crowncommercial.dsd.api.catalogue.config;

/**
 * Global constant values
 */
public final class Constants {

  // Routing related
  public static final String ROUTE_ID_LIST_PRODUCTS = "list-products";
  public static final String ROUTE_LIST_PRODUCTS = "direct:" + ROUTE_ID_LIST_PRODUCTS;
  public static final String ROUTE_ID_GET_PRODUCT = "get-product";
  public static final String ROUTE_GET_PRODUCT = "direct:" + ROUTE_ID_GET_PRODUCT;
  public static final String ROUTE_FINALISE_RESPONSE = "direct:finalise-response";

  // Custom Exchange properties
  public static final String EXPROP_SPREE_IMAGE_DATA = "SpreeImageData";

}
