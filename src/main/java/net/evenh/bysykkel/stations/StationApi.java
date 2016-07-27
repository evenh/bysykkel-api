package net.evenh.bysykkel.stations;

import net.evenh.bysykkel.domain.ComplexLockList;
import net.evenh.bysykkel.domain.ComplexStationList;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Interacts with the API provided by Oslo Bysykkel.
 */
interface StationApi {
  @GET("api/v1/stations.json")
  Call<ComplexStationList> getAllStations();

  @GET("api/v1/stations/availability")
  Call<ComplexLockList> getLockStatus();
}
