package net.evenh.bysykkel.stations;

import net.evenh.bysykkel.domain.ComplexStationList;
import net.evenh.bysykkel.domain.Lock;
import net.evenh.bysykkel.domain.Station;
import net.evenh.bysykkel.exceptions.CommunicationFailureException;
import net.evenh.bysykkel.exceptions.InitializationException;
import net.evenh.bysykkel.exceptions.UnknownStationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import rx.Observable;
import rx.Subscriber;

/**
 * Manages information about the various {@link Station}s.
 */
public class DefaultStationService implements StationService {
  private static final Logger log = LoggerFactory.getLogger(DefaultStationService.class);
  private StationApi api;

  /**
   * Constructs an instance ready to perform network calls.
   *
   * @param client A populated Retrofit client.
   * @throws InitializationException Thrown if the Retrofit client is null.
   */
  public DefaultStationService(Retrofit client) throws InitializationException {
    if (client == null) {
      throw new InitializationException("Retrofit client cannot be null");
    }

    this.api = client.create(StationApi.class);
  }

  @Override
  public Observable<List<Station>> getAllStations() throws CommunicationFailureException {
    return Observable.create(new Observable.OnSubscribe<List<Station>>() {
      @Override
      public void call(final Subscriber<? super List<Station>> subscriber) {
        if (!subscriber.isUnsubscribed()) {
          api.getAllStations().enqueue(new Callback<ComplexStationList>() {
            @Override
            public void onResponse(Call<ComplexStationList> call, Response<ComplexStationList> response) {
              List<Station> listWithLocks = decorateListWithAvailability(response.body().getStations());

              subscriber.onNext(listWithLocks);
              if (!subscriber.isUnsubscribed()) {
                subscriber.onCompleted();
              }
            }

            @Override
            public void onFailure(Call<ComplexStationList> call, Throwable throwable) {
              log.error("Got error while fetching station list", throwable);
              subscriber.onError(new CommunicationFailureException(throwable.getMessage()));
            }
          });
        }
      }
    });
  }

  @Override
  public Observable<Station> getStation(final Long id) throws UnknownStationException, CommunicationFailureException {
    return Observable.create(new Observable.OnSubscribe<Station>() {
      @Override
      public void call(final Subscriber<? super Station> subscriber) {
        if (id == null) {
          subscriber.onError(new UnknownStationException("Won't process station where id is null"));
        }

        if (!subscriber.isUnsubscribed()) {
          api.getAllStations().enqueue(new Callback<ComplexStationList>() {
            @Override
            public void onResponse(Call<ComplexStationList> call, Response<ComplexStationList> response) {
              List<Station> listWithLocks = decorateListWithAvailability(response.body().getStations());

              for (Station station : listWithLocks) {
                if (station.getId().equals(id)) {
                  log.debug("Found station with id {} in the list of stations", id);
                  subscriber.onNext(station);
                  subscriber.onCompleted();

                  return;
                }
              }

              subscriber.onError(new UnknownStationException("Could not find station with id " + id));
            }

            @Override
            public void onFailure(Call<ComplexStationList> call, Throwable throwable) {
              log.error("Got error while fetching station list for filtering", throwable);
              subscriber.onError(new CommunicationFailureException(throwable.getMessage()));
            }
          });
        }
      }
    });
  }

  private List<Station> decorateListWithAvailability(final List<Station> stations) throws CommunicationFailureException {
    try {
      List<Station> updatedList = new ArrayList<>();
      List<Lock> lockList = api.getLockStatus().execute().body().getLocks();

      for (Station station : stations) {

        for (Lock lock : lockList) {
          if (station.getId().equals(lock.getId())) {
            station.setLockStatus(lock);
          }

          updatedList.add(station);
        }
      }

      return updatedList;
    } catch (IOException e) {
      throw new CommunicationFailureException("Could not get lock status for stations");
    }
  }
}
