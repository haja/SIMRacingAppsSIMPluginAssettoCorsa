/*
 * Copyright (c) 2020 Harald Jagenteufel.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.SIMRacingApps.SIMPlugins.AC.gauges;

import com.SIMRacingApps.Car;
import com.SIMRacingApps.Data;
import com.SIMRacingApps.Data.State;
import com.SIMRacingApps.SIMPlugins.AC.ACSIMPlugin;
import com.SIMRacingApps.SIMPlugins.AC.ACTrack;
import com.SIMRacingApps.Server;

public class Tachometer extends ACGauge {

  private final static String TAG = "ACTachometer: ";

  private final Gear gearGauge;

  public Tachometer(ACSIMPlugin plugin, Car car, Gear gearGauge) {
    super(Type.TACHOMETER, car, new ACTrack(plugin), null, null, plugin);
    this.gearGauge = gearGauge;

    // somewhat arbitrary offset from max-rpms
    // TODO can we get that from sim? is there an algorithm for this?
    int maxRpm = plugin.internals().getSessionStatic().maxRpm;
    int shiftLights = maxRpm - 1400;
    int shift = maxRpm - 500;
    int shiftBlink = maxRpm - 100;
    _addStateRange("", "SHIFTLIGHTS", shiftLights, shift, "rev/min");
    _addStateRange("", "SHIFT", shift, shiftBlink, "rev/min");
    _addStateRange("", "SHIFTBLINK", shiftBlink, 999999.0, "rev/min");
    double maxRpmMult = (double) maxRpm * getMultiplier().getDouble();
    log("max rpm " + maxRpm + " max rpm multiplied " + maxRpmMult);
    _setMaximum(maxRpmMult, "rev/min");
  }

  @Override
  public Data getValueCurrent(String UOM) {
    final Data data = new Data(this.m_type, plugin.internals().getCurrentPhysics().rpms, "rev/min",
        State.NORMAL);
    String power = String.valueOf(plugin.internals().getSessionStatic().maxPower);
    return _getReturnValue(data, UOM, gearGauge.getGear(), power);
  }

  private void log(String msg) {
    Server.logger().info(TAG + msg);
  }
}
