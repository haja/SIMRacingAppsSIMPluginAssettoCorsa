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
import com.SIMRacingApps.Gauge;
import com.SIMRacingApps.SIMPlugins.AC.ACSIMPlugin;
import com.SIMRacingApps.Track;

import java.util.Map;

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
