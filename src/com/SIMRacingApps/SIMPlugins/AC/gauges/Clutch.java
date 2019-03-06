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
public class Clutch extends ACGauge {

  public Clutch(ACSIMPlugin plugin, Car car) {
    super(Type.CLUTCH, car, new ACTrack(plugin), null, null, plugin);
  }

  @Override
  public Data getValueCurrent(String UOM) {
    final int value = Math.round(plugin.internals().getCurrentPhysics().clutch * 100);
    return new Data(this.m_type, value,"%", State.NORMAL);
  }
}