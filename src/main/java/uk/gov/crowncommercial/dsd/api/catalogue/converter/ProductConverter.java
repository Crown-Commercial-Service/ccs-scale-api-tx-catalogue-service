package uk.gov.crowncommercial.dsd.api.catalogue.converter;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.apache.camel.Converter;
import org.apache.camel.TypeConverters;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.databind.ObjectMapper;
import uk.gov.crowncommercial.dsd.api.catalogue.model.Image;
import uk.gov.crowncommercial.dsd.api.catalogue.model.Product;

/**
 * Product conversion utilities
 */
@Component
public class ProductConverter implements TypeConverters {

  @Autowired
  private ObjectMapper objectMapper;

  /**
   * Registered with Camel as converter but may be invoked directly
   *
   * @param productData the raw Spree product <code>{"data":{}}</code>
   * @return a product
   */
  @SuppressWarnings("unchecked")
  @Converter
  public Product toProduct(final Map<String, Object> productData) {

    final Product product = objectMapper
        .convertValue((Map<String, Object>) productData.get("attributes"), Product.class);
    product.setId((String) productData.get("id"));

    return product;
  }

  /**
   * Extract, unmarshal and transform image data from the Spree response for inclusion with the
   * {@link Product}.
   *
   * Note: for List Products, a single image is identified in the product data by
   * {@link Product#getImageId()} but in Get Product, all images related to the product are returned
   *
   * @param product
   * @param imagesData the Spree include data filtered to image(s) only
   * @return the product with image data
   */
  @SuppressWarnings("unchecked")
  public Product addImagesToProduct(final Product product,
      final List<Map<String, Object>> imagesData) {

    final List<Image> productImages =
        imagesData.parallelStream().filter(imgData -> StringUtils.isBlank(product.getImageId())
            || imgData.getOrDefault("id", "").equals(product.getImageId())).map(imgData -> {
              imgData.put("styles",
                  ((Map<String, Object>) imgData.get("attributes")).get("styles"));
              return objectMapper.convertValue(imgData, Image.class);
            }).collect(Collectors.toList());

    product.setImages(productImages);
    return product;
  }

}
