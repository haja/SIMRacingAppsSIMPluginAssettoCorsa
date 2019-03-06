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

  private final ACSIMPlugin m_simPlugin;
  private ACCar m_carSelf;

  public ACSession(ACSIMPlugin simPlugin) {
    super(simPlugin);
    m_simPlugin = simPlugin;
    m_carSelf = new ACCar(m_simPlugin);
    Server.logger().info("Assetto Corsa Session Created");
  }

  @Override
  public ACCar getCar(String car) {
    updateCar();
    return m_carSelf;
  }

  private void updateCar() {
    if (!m_carSelf.isValid()) {
      m_carSelf.initialize();
    }
  }

  @Override
  public Track getTrack() {
    return new ACTrack(m_simPlugin);
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
    type.setValue(Type.PRACTICE);
    type.setState(State.NORMAL);
    return type;
  }

  @Override
  public Data getId() {
    final Data id = super.getId();
    id.setValue("1");
    id.setState(State.NORMAL);
    return id;
  }
}
