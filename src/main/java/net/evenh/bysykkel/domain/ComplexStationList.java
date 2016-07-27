package net.evenh.bysykkel.domain;

import java.util.List;

/**
 * A wrapper for consuming the Bysykkel API. Not meant for external use.
 */
public class ComplexStationList {
  List<Station> stations;

  public List<Station> getStations() {
    return stations;
  }
}
