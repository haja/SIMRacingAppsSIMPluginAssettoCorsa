package com.SIMRacingApps.SIMPlugins.AssettoCorsa;

import com.SIMRacingApps.Car;
import com.SIMRacingApps.Server;
import com.SIMRacingApps.Session;

/**
 * @author Harald Jagenteufel
 * @copyright Copyright (C) 2019 Harald Jagenteufel
 * @since 1.8
 * @license Apache License 2.0
 */
public class ACSession extends Session {

  private final ACSimPlugin m_simPlugin;
  private final ACCar m_carSelf;

  public ACSession(ACSimPlugin simPlugin) {
    super(simPlugin);
    m_simPlugin = simPlugin;
    m_carSelf = new ACCar(m_simPlugin);
    Server.logger().info("Assetto Corsa Session Created");
  }

  public Car getCar(String car) {
    return m_carSelf;
  }
}
