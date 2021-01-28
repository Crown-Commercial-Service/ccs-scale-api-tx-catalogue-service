package uk.gov.crowncommercial.dsd.api.catalogue.model;

import java.util.HashSet;
import java.util.Set;
import lombok.Value;

/**
 *
 */
@Value
public class ProductList {

  Set<Product> products = new HashSet<>();

}
