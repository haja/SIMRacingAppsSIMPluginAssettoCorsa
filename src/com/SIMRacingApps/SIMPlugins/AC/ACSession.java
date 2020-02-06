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

import com.SIMRacingApps.*;
import com.SIMRacingApps.Data.State;

public class ACSession extends Session {

  private final ACSIMPlugin plugin;
  private ACCar carSelf;
  private ACTrack acTrack;

  public ACSession(ACSIMPlugin simPlugin) {
    super(simPlugin);
    plugin = simPlugin;
    carSelf = new ACCar(plugin);
    acTrack = new ACTrack(plugin);
    Server.logger().info("Assetto Corsa Session Created");
  }

  @Override
  public ACCar getCar(String car) {
    updateCar();
    return carSelf;
  }

  private void updateCar() {
    if (!carSelf.isValid()) {
      carSelf.initialize();
    }
  }

  @Override
  public ACTrack getTrack() {
    return acTrack;
  }

  @Override
  public Data getMessages() {
    final Data messages = super.getMessages();
    messages.setValue(";;");
    return messages;
  }

  @Override
  public Data getType() {
    final Data type = super.getType();
    setValue(type, Type.PRACTICE);
    return type;
  }

  @Override
  public Data getId() {
    final Data id = super.getId();
    setValue(id, "1");
    return id;
  }

  @Override
  public Data getLap() {
    final Data lap = super.getLap();
    setValue(lap, plugin.internals().getCurrentGraphics().completedLaps);
    return lap;
  }

  @Override
  public Data getLaps() {
    final Data laps = super.getLaps();
    setValue(laps, plugin.internals().getCurrentGraphics().numberOfLaps);
    return laps;
  }

  @Override
  public Data getTimeRemaining() {
    final Data timeRemaining = super.getTimeRemaining();
    setValue(timeRemaining, plugin.internals().getCurrentGraphics().sessionTimeLeft);
    return timeRemaining;
  }

  private void setValue(Data data, Object val) {
    if (plugin.isConnected()) {
      data.setValue(val);
      data.setState(State.NORMAL);
    }
  }
}
