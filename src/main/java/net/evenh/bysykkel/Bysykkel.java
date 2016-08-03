package net.evenh.bysykkel;

import net.evenh.bysykkel.exceptions.InitializationException;
import net.evenh.bysykkel.stations.DefaultStationService;
import net.evenh.bysykkel.stations.StationService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * The main entry point for using the Bysykkel API library.
 */
public final class Bysykkel {
  static final Logger log = LoggerFactory.getLogger(Bysykkel.class);

  static Bysykkel instance = new Bysykkel();
  final String API_HOST = "http://oslobysykkel.no";

  Retrofit client;
  Cache configuredCache;

  /**
   * Gets or creates a new instance (if none exists) of the Bysykkel API. <p>Caching is disabled by
   * default and should be enabled by {@link #enableCache(File, Integer)}.</p>
   */
  public static Bysykkel getInstance() {
    return instance;
  }

  Bysykkel() {
    log.debug("Instantiating a new instance of the Bysykkel API");
    this.client = createRetrofitClient();
  }

  Retrofit createRetrofitClient() {
    log.debug("Creating Retrofit client");

    return new Retrofit.Builder()
      .baseUrl(API_HOST)
      .addConverterFactory(GsonConverterFactory.create())
      .client(createHttpClient())
      .build();
  }

  OkHttpClient createHttpClient() {
    OkHttpClient.Builder builder = new OkHttpClient.Builder();

    if (configuredCache != null) {
      builder.cache(configuredCache);
    }

    return builder.build();
  }

  /**
   * Enables caching.
   *
   * @param cache           A {@link File} instance pointing to a directory where the cacheDirectory
   *                        should be stored.
   * @param sizeInMegabytes The max size of the cacheDirectory in megabytes.
   * @return An instance with cache.
   * @throws InitializationException If cacheDirectory is null, not readable or not writable.
   */
  public Bysykkel enableCache(File cache, Integer sizeInMegabytes) throws InitializationException {
    if (cache == null || !cache.isDirectory() || !cache.canRead() || !cache.canWrite()) {
      throw new InitializationException("Invalid cacheDirectory specified");
    }

    if (sizeInMegabytes == null || sizeInMegabytes == 0) {
      throw new InitializationException("Cache size cannot be null or zero");
    }

    log.info("Cache specified, recreating network client");

    final int cacheSize = sizeInMegabytes * 1024 * 1024;

    this.configuredCache = new Cache(cache, cacheSize);
    this.client = createRetrofitClient();

    return getInstance();
  }

  /**
   * Disables caching.
   *
   * @return An instance without cache.
   */
  public Bysykkel disableCache() {
    log.info("Disabling cache");

    this.configuredCache = null;
    this.client = createRetrofitClient();

    return getInstance();
  }

  /**
   * Provides an initialized {@link DefaultStationService}.
   */
  public StationService stations() throws InitializationException {
    return new DefaultStationService(client);
  }

  @Override
  public String toString() {
    return "Bysykkel["
      + "initialized=" + (client != null)
      + ", cacheEnabled=" + (configuredCache != null) + "]";
  }
}
