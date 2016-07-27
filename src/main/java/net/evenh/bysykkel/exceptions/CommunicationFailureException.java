package net.evenh.bysykkel.exceptions;

/**
 * Exception thrown if the library recieves an unexpected HTTP response code.
 */
public class CommunicationFailureException extends RuntimeException {
  public CommunicationFailureException() {
    super();
  }

  public CommunicationFailureException(String message) {
    super(message);
  }

  public CommunicationFailureException(String message, Throwable cause) {
    super(message, cause);
  }
}
