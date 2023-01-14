package net.learning.springreactivemongocurdpoc.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * Common exception for application.
 */
@Getter
public class ProductException extends RuntimeException {

  private final HttpStatus status;
  private final String errorMessage;

  public ProductException(HttpStatus status, String errorMessage) {
    super(errorMessage);
    this.errorMessage = errorMessage;
    this.status = status;
  }
}
