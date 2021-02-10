package uk.gov.crowncommercial.dsd.api.catalogue.logic;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

/**
 *
 */
@Component
public class RequestValidator implements Processor {

  @Override
  public void process(final Exchange exchange) throws Exception {
    /*
     * TODO: Validate request... throw RequestValidationException or continue... etc
     */
  }

}
