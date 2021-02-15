package uk.gov.crowncommercial.dsd.api.catalogue.config;

import lombok.experimental.UtilityClass;

/**
 * Global constant values
 */
@UtilityClass
public final class Constants {

  // Routing related
  public static final String ROUTE_ID_LIST_PRODUCTS = "list-products";
  public static final String ROUTE_LIST_PRODUCTS = "direct:" + ROUTE_ID_LIST_PRODUCTS;
  public static final String ROUTE_ID_GET_PRODUCT = "get-product";
  public static final String ROUTE_GET_PRODUCT = "direct:" + ROUTE_ID_GET_PRODUCT;
  public static final String ROUTE_FINALISE_RESPONSE = "direct:finalise-response";

  // Spree HTTP Endpoints
  public static final String ENDPOINT_SPREE_API_LIST_PRODUCTS = "http://spree-api-list-products";
  public static final String ENDPOINT_SPREE_API_GET_PRODUCT = "http://spree-api-get-product";

  // Custom Exchange properties
  public static final String EXPROP_SPREE_IMAGE_DATA = "SpreeImageData";
  public static final String EXPROP_SPREE_DOCUMENT_DATA = "SpreeDocumentData";
  public static final String EXPROP_SPREE_PRODUCT_PROPS_DATA = "SpreeProductPropsData";

}
