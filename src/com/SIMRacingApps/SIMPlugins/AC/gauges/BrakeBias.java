package com.SIMRacingApps.SIMPlugins.AC.gauges;

import com.SIMRacingApps.Car;
import com.SIMRacingApps.Data;
import com.SIMRacingApps.Data.State;
import com.SIMRacingApps.SIMPlugins.AC.ACSIMPlugin;
import com.SIMRacingApps.SIMPlugins.AC.ACTrack;

/**
 * @author Harald Jagenteufel
 * @copyright Copyright (C) 2019 Harald Jagenteufel
 * @since 1.8
 * @license Apache License 2.0
 */
public class BrakeBias extends ACGauge {

  public BrakeBias(ACSIMPlugin plugin, Car car) {
    super(Type.BRAKEBIASADJUSTMENT, car, new ACTrack(plugin), null, null, plugin);
  }

  @Override
  public Data getValueCurrent(String UOM) {
    return new Data(this.m_type, plugin.internals().getCurrentPhysics().brakeBias,"%", State.NORMAL);
  }
}