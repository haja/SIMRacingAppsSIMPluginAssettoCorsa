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

/**
 * Returns the Steering Angle.
 */
public class Steering extends ACGauge {
  public Steering(ACSIMPlugin plugin, Car car) {
    super(Type.STEERING, car, new ACTrack(plugin), null, null, plugin);
  }

  @Override
  public Data getValueCurrent(String UOM) {
    final int value = fromRange(plugin.internals().getCurrentPhysics().steerAngle);
    return new Data(this.m_type, value,UOM, State.NORMAL);
  }

  private int fromRange(float range) {
    return Math.round(range * 90);
  }
}
