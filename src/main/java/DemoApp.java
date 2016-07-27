import net.evenh.bysykkel.Bysykkel;
import net.evenh.bysykkel.domain.Station;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import rx.Observable;
import rx.functions.Action0;
import rx.functions.Action1;

/**
 * Just for playing around until tests are added.
 */
public class DemoApp {
  private static final Logger log = LoggerFactory.getLogger(DemoApp.class);

  public static void main(String... args) {
    Bysykkel api = Bysykkel.getInstance();

    getOne(157L, api);
  }

  private static void getOne(final long id, Bysykkel api) {
    api.stations().getStation(id).subscribe(new Action1<Station>() {
      @Override
      public void call(Station station) {
        log.info("Got station: {}", station);
        log.info("Free: {}, Locked: {}", station.getLockStatus().getAvailableBikes(), station.getLockStatus().getUsedLocks());
      }
    }, new Action1<Throwable>() {
      @Override
      public void call(Throwable throwable) {
        log.error("Got error while getting 1 station: {}", throwable);
      }
    }, new Action0() {
      @Override
      public void call() {
        log.info("Fetching of station with {} complete", id);
      }
    });
  }

  private static void getAll(Bysykkel api) {
    Observable<List<Station>> stationList = api.stations().getAllStations();

    stationList.subscribe(
      new Action1<List<Station>>() {
        // On next
        @Override
        public void call(List<Station> stations) {
          log.info("Got station list: {}", stations);
        }
      },
      new Action1<Throwable>() {
        // On error
        @Override
        public void call(Throwable throwable) {
          log.error("Got error", throwable);
        }
      },
      new Action0() {
        @Override
        public void call() {
          log.info("Complete!");
        }
      });
  }
}
