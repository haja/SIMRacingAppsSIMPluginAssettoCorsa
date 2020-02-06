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

package com.SIMRacingApps.SIMPlugins.AC;

import com.SIMRacingApps.Car;
import com.SIMRacingApps.Data;
import com.SIMRacingApps.Data.State;
import com.SIMRacingApps.Gauge;
import com.SIMRacingApps.SIMPlugins.AC.gauges.*;
import com.SIMRacingApps.Server;

public class ACCar extends Car {

  private final static String TAG = "ACCar: ";

  private final ACSIMPlugin plugin;
  private boolean valid = false;

  public ACCar(ACSIMPlugin SIMPlugin) {
    super(SIMPlugin);
    plugin = SIMPlugin;
    plugin.internals().registerForEvent(event -> initialize());
    initialize();
  }

  public void initialize() {
    if (!plugin.internals().isSessionRunning()) {
      log("initialize: session not running");
      return;
    }

    _setGauge(new Throttle(plugin, this));
    _setGauge(new Brake(plugin, this));
    _setGauge(new Clutch(plugin, this));
    _setGauge(new Steering(plugin, this));
    _setGauge(new BrakeBias(plugin, this));
    final Gear gear = new Gear(plugin, this);
    _setGauge(gear);
    _setGauge(new Tachometer(plugin, this, gear));
    _setGauge(new Speedometer(plugin, this));
    _setGauge(new EnginePower(plugin, this));

    // TODO fix id loading
    m_id = 1;
    m_name = String.valueOf(plugin.internals().getSessionStatic().carModel);
    m_description = String.valueOf(plugin.internals().getSessionStatic().carModel);
    m_carIdentifier = "I1";

    valid = true;
    log("initialize: car initialized");
    _postInitialization();
  }

  private void log(String msg) {
    Server.logger().info(TAG + msg);
  }

  @Override
  public boolean isValid() {
    if (plugin.isConnected()) {
      return valid;
    } else {
      valid = false;
    }
    return false;
  }

  @Override
  public boolean isME() {
    // TODO implement
    return true;
  }

  @Override
  public Data getId() {
    // TODO implement
    return new Data("Car/"+m_carIdentifier+"/Id","1","id", State.NORMAL);
  }

  @Override
  protected void _setGauge(Gauge gauge) {
    m_gauges.put(gauge.getType().getString().toLowerCase(), gauge);
  }

  @Override
  public Data getPosition() {
    Data position = super.getPosition();
    setValue(position, plugin.internals().getCurrentGraphics().position);
    return position;
  }

  @Override
  public Data getLapTime(String lapType, int lapsToAverage) {
    Data d = super.getLapTime(lapType, lapsToAverage);
    String reference = d.getValue("reference").toString();
    if (reference.equals(LapType.BEST)) {
      setValue(d, plugin.internals().getCurrentGraphics().iBestTime);
    }
    if (reference.equals(LapType.CURRENT)) {
      setValue(d, plugin.internals().getCurrentGraphics().iCurrentTime);
    }
    // TODO implement complex lap timings, save lap times
    return d;
  }

  private void setValue(Data data, Object val) {
    if (plugin.isConnected()) {
      data.setValue(val);
      data.setState(State.NORMAL);
    }
  }
}
