package com.SIMRacingApps.SIMPlugins.AC.gauges;

import com.SIMRacingApps.Car;
import com.SIMRacingApps.Data;
import com.SIMRacingApps.Data.State;
import com.SIMRacingApps.SIMPlugins.AC.ACSIMPlugin;
import com.SIMRacingApps.SIMPlugins.AC.ACTrack;

/**
 * @author Harald Jagenteufel
 * @copyright Copyright (C) 2019 Harald Jagenteufel
 * @license Apache License 2.0
 * @since 1.8
 */
public class Gear extends ACGauge {

  public Gear(ACSIMPlugin plugin, Car car) {
    super(Type.GEAR, car, new ACTrack(plugin), null, null, plugin);
  }

  @Override
  public Data getValueCurrent(String UOM) {
    return new Data(this.m_type, getGear(), "", State.NORMAL);
  }

  private String getGear() {
    final int gear = plugin.internals().getCurrentPhysics().gear;
    switch (gear) {
      case 0:
        return "R";
      case 1:
        return "N";
      default:
        return String.valueOf(gear - 1);
    }
  }
}
