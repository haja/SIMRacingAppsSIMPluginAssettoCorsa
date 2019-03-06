package com.SIMRacingApps.SIMPlugins.AC.gauges;

import com.SIMRacingApps.Car;
import com.SIMRacingApps.Gauge;
import com.SIMRacingApps.SIMPlugins.AC.ACSIMPlugin;
import com.SIMRacingApps.Track;

import java.util.Map;

/**
 * @author Harald Jagenteufel
 * @copyright Copyright (C) 2019 Harald Jagenteufel
 * @since 1.8
 * @license Apache License 2.0
 */
public class ACGauge extends Gauge {

  protected final ACSIMPlugin plugin;

  public ACGauge(
      String type
      , Car car
      , Track track
      , Map<String, Map<String, Map<String, Object>>> simGaugesBefore
      , Map<String, Map<String, Map<String, Object>>> simGaugesAfter,
      ACSIMPlugin plugin) {
    super(type, car, track, simGaugesBefore, simGaugesAfter);
    this.plugin = plugin;
  }

}
