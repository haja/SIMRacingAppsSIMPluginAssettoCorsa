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
public class Tachometer extends ACGauge {

  public Tachometer(ACSIMPlugin plugin, Car car) {
    super(Type.TACHOMETER, car, new ACTrack(plugin), null, null, plugin);

    // somewhat arbitrary offset from max-rpms
    // TODO can we get that from sim? is there an algorithm for this?
    int maxRpm = plugin.internals().getSessionStatic().maxRpm;
    int shiftLights = maxRpm - 1400;
    int shift = maxRpm - 500;
    int shiftBlink = maxRpm - 100;
    _addStateRange("", "SHIFTLIGHTS", shiftLights, shift, "rev/min");
    _addStateRange("", "SHIFT", shift, shiftBlink, "rev/min");
    _addStateRange("", "SHIFTBLINK", shiftBlink, 999999.0, "rev/min");
  }

  @Override
  public Data getValueCurrent(String UOM) {
    return new Data(this.m_type, plugin.internals().getCurrentPhysics().rpms, "rev/min",
        State.NORMAL);
  }
}
