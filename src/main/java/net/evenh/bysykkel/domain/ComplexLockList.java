package net.evenh.bysykkel.domain;

import java.util.List;

/**
 * A wrapper for consuming the Bysykkel API. Not meant for external use.
 */
public class ComplexLockList {
  private List<Lock> stations;

  public List<Lock> getLocks() {
    return stations;
  }
}
