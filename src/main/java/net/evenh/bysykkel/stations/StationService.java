package net.evenh.bysykkel.stations;

import net.evenh.bysykkel.domain.Station;
import net.evenh.bysykkel.exceptions.CommunicationFailureException;
import net.evenh.bysykkel.exceptions.UnknownStationException;

import java.util.List;

import rx.Observable;

/**
 * Returns a list of all available {@link Station}s reported by the Bysykkel API.
 */
public interface StationService {
  /**
   * Fetches a list of all the different bike racks available.
   *
   * @return An {@link Observable} list of {@link Station}s.
   * @throws CommunicationFailureException If there is a failure when communicating with the
   *                                       Bysykkel API, this exception will be returned in the
   *                                       error handler of the {@link Observable}.
   */
  Observable<List<Station>> getAllStations() throws CommunicationFailureException;

  /**
   * Fetches a specific {@link Station}.
   *
   * @param stationId The identifier for this specific bike rack.
   * @return An {@link Observable} {@link Station}.
   * @throws UnknownStationException       If the station id is <code>null</code> or not found in
   *                                       the Bysykkel API, this exception will be returned in the
   *                                       error handler of the {@link Observable}.
   * @throws CommunicationFailureException If there is a failure when communicating with the
   *                                       Bysykkel API, this exception will be returned in the
   *                                       error handler of the {@link Observable}.
   */
  Observable<Station> getStation(final Long stationId) throws UnknownStationException, CommunicationFailureException;
}
