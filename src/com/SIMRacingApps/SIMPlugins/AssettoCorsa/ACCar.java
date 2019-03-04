package com.SIMRacingApps.SIMPlugins.AssettoCorsa;

import com.SIMRacingApps.Car;
import com.SIMRacingApps.Gauge;
import com.SIMRacingApps.SIMPlugins.AssettoCorsa.gauges.ThrottleGauge;

import java.util.Map;
import java.util.TreeMap;

/**
 * @author Harald Jagenteufel
 * @copyright Copyright (C) 2019 Harald Jagenteufel
 * @since 1.8
 * @license Apache License 2.0
 */
public class ACCar extends Car {

  private final ACSimPlugin m_simPlugin;

  protected Map<String, Gauge> m_gauges = new TreeMap<>();

  public ACCar(ACSimPlugin SIMPlugin) {
    super(SIMPlugin);
    m_simPlugin = SIMPlugin;
    _initialize();
  }

  private void _initialize() {
    if (m_simPlugin.internals().isSessionRunning()) {
      return;
    }

    _setGauge(new ThrottleGauge(m_simPlugin, this));
  }

  private void setGauge(Gauge gauge) {
    m_gauges.put(gauge.getType().getString().toLowerCase(), gauge);
  }

}
