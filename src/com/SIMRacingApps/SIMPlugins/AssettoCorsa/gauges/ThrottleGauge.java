package com.SIMRacingApps.SIMPlugins.AssettoCorsa.gauges;

import com.SIMRacingApps.Car;
import com.SIMRacingApps.Data;
import com.SIMRacingApps.Data.State;
import com.SIMRacingApps.SIMPlugins.AssettoCorsa.ACSimPlugin;

/**
 * @author Harald Jagenteufel
 * @copyright Copyright (C) 2019 Harald Jagenteufel
 * @since 1.8
 * @license Apache License 2.0
 */
public class ThrottleGauge extends ACGauge {

  private final ACSimPlugin m_plugin;

  public ThrottleGauge(ACSimPlugin plugin, Car car) {
    super(Type.THROTTLE, car, null, null, null);
    m_plugin = plugin;
  }

  @Override
  public Data getValueCurrent(String UOM) {
    return new Data(this.m_type, m_plugin.internals().getCurrentPhysics().gas,"%", State.NORMAL);
  }
}
