package net.evenh.bysykkel.exceptions;

/**
 * Exception thrown if initialization failed.
 */
public class InitializationException extends RuntimeException {
  public InitializationException() {
    super();
  }

  public InitializationException(String message) {
    super(message);
  }

  public InitializationException(String message, Throwable cause) {
    super(message, cause);
  }
}
