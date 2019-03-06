package com.SIMRacingApps.SIMPlugins.AC;

import com.SIMRacingApps.*;
import com.SIMRacingApps.Data.State;

/**
 * @author Harald Jagenteufel
 * @copyright Copyright (C) 2019 Harald Jagenteufel
 * @since 1.8
 * @license Apache License 2.0
 */
public class ACSession extends Session {

  private final ACSIMPlugin plugin;
  private ACCar carSelf;

  public ACSession(ACSIMPlugin simPlugin) {
    super(simPlugin);
    plugin = simPlugin;
    carSelf = new ACCar(plugin);
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
    return new ACTrack(plugin);
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
