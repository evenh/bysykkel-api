package net.evenh.bysykkel;

import net.evenh.bysykkel.exceptions.InitializationException;
import net.evenh.bysykkel.stations.DefaultStationService;
import net.evenh.bysykkel.stations.StationService;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.File;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.assertThat;

@RunWith(JUnit4.class)
public class BysykkelTest {
  private Bysykkel unit;

  @Before
  public void setup() {
    if (Bysykkel.instance == null) {
      Bysykkel.instance = new Bysykkel();
    }

    unit = Bysykkel.getInstance();
  }

  @After
  public void teardown() {
    Bysykkel.instance = null;
  }

  @Test(expected = InitializationException.class)
  public void it_should_throw_exception_on_invalid_cache_directory() {
    unit.enableCache(null, 100);
    unit.enableCache(null, null);
  }

  @Test(expected = InitializationException.class)
  public void it_should_throw_exception_on_invalid_cache_size() {
    unit.enableCache(new File("/tmp"), null);
    unit.enableCache(new File("/tmp"), 0);
  }

  @Test
  public void it_should_enable_caching_properly() {
    unit.enableCache(new File("/tmp"), 10);

    assertThat(unit.toString(), containsString("cacheEnabled=true"));
  }

  @Test
  public void it_should_disable_cache_properly() {
    unit.disableCache();

    assertThat(unit.toString(), containsString("cacheEnabled=false"));
  }

  @Test
  public void it_should_return_a_ready_station_service() {
    final StationService stations = unit.stations();

    assertThat(stations, instanceOf(DefaultStationService.class));
  }

  @Test(expected = InitializationException.class)
  public void it_should_throw_exception_if_invalid_client_for_station_service() throws Exception {
    Bysykkel.instance.client = null;

    final StationService stations = unit.stations();

    assertThat(stations, nullValue());
  }
}
