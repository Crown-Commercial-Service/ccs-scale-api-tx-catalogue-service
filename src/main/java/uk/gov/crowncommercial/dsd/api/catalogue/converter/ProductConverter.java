package uk.gov.crowncommercial.dsd.api.catalogue.converter;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.apache.camel.Converter;
import org.apache.camel.TypeConverters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.databind.ObjectMapper;
import uk.gov.crowncommercial.dsd.api.catalogue.model.Image;
import uk.gov.crowncommercial.dsd.api.catalogue.model.Product;

/**
 *
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
   *
   *
   * @param product
   * @param imagesData the Spree include data filtered to images only
   * @return the product with image data
   */
  @SuppressWarnings("unchecked")
  public Product addImagesToProduct(final Product product,
      final List<Map<String, Object>> imagesData) {

    final List<Image> productImages = imagesData.parallelStream()
        .filter(imgData -> imgData.getOrDefault("id", "").equals(product.getImageId()))
        .map(imgData -> {
          imgData.put("styles", ((Map<String, Object>) imgData.get("attributes")).get("styles"));
          return objectMapper.convertValue(imgData, Image.class);
        }).collect(Collectors.toList());

    product.setImages(productImages);
    return product;
  }

}
