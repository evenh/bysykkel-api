package net.evenh.bysykkel.exceptions;

/**
 * Thrown if a station was not found.
 */
public class UnknownStationException extends RuntimeException {
  public UnknownStationException() {
    super();
  }

  public UnknownStationException(String message) {
    super(message);
  }

  public UnknownStationException(String message, Throwable cause) {
    super(message, cause);
  }
}
