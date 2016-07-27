package net.evenh.bysykkel;

import net.evenh.bysykkel.exceptions.InitializationException;
import net.evenh.bysykkel.stations.DefaultStationService;
import net.evenh.bysykkel.stations.StationService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * The main entry point for using the Bysykkel API library.
 */
public final class Bysykkel {
  private static final Logger log = LoggerFactory.getLogger(Bysykkel.class);

  private static final Bysykkel instance = new Bysykkel();
  private static final String API_HOST = "http://oslobysykkel.no";

  private Retrofit client;

  /**
   * Gets or creates a new instance (if none exists) of the Bysykkel API.
   */
  public static Bysykkel getInstance() {
    return instance;
  }

  private Bysykkel() {
    log.debug("Instantiating a new instance of the Bysykkel API");
    this.client = createHttpClient();
  }

  private Retrofit createHttpClient() {
    log.debug("Creating Retrofit client");

    return new Retrofit.Builder()
      .baseUrl(API_HOST)
      .addConverterFactory(GsonConverterFactory.create())
      .build();
  }

  /**
   * Provides an initialized {@link DefaultStationService}.
   */
  public StationService stations() {
    try {
      return new DefaultStationService(client);
    } catch (InitializationException ie) {
      log.error("Caught exception while returning station service", ie);
      return null;
    }
  }

  @Override
  public String toString() {
    return "Bysykkel[initialized=" + (client != null) + "]";
  }
}
