package net.evenh.bysykkel.domain;

/**
 * Represents a geographical coordinate.
 */
public class Coordinate {
  private double x;
  private double y;

  public double getX() {
    return x;
  }

  public double getY() {
    return y;
  }

  @Override
  public String toString() {
    return "Coordinate{"
      + "x=" + x
      + ", y=" + y
      + '}';
  }
}
