package uk.gov.crowncommercial.dsd.api.catalogue.exception;

import org.apache.camel.Exchange;
import org.apache.camel.ValidationException;

/**
 *
 */
public class RequestValidationException extends ValidationException {

  /**
   *
   */
  private static final long serialVersionUID = 1L;

  public RequestValidationException(final Exchange exchange, final String message) {
    super(exchange, message);
  }

}
