/**
 *
 */
package com.SIMRacingApps.SIMPlugins.AC;

import com.SIMRacingApps.Data;
import com.SIMRacingApps.Data.State;
import com.SIMRacingApps.SIMPlugin;
import com.SIMRacingApps.Server;
import com.SIMRacingApps.Session;

/**
 * @author Harald Jagenteufel
 * @copyright Copyright (C) 2019 Harald Jagenteufel
 * @since 1.8
 * @license Apache License 2.0
 */
public class ACSIMPlugin extends SIMPlugin {

  private ACInternals m_internals;
  private ACSession m_session;

  public ACSIMPlugin() throws SIMPluginException {
    m_internals = new ACInternals();
    m_session = new ACSession(this);
    Server.logger().info("AssettoCorsaSIMPluging created");
  }

  @Override
  public Data getSIMName() {
    return super.getSIMName().setValue("com/SIMRacingApps/SIMPlugins/AC").setState(Data.State.NORMAL);
  }

  @Override
  public Data getSIMData(String... args) {
    Server.logger().info("AssettoCorsaSIMPluging getSIMData called - not implemented");
    return new Data("not implemented", "not implemented", State.ERROR);
  }

  @Override
  public Session getSession() {
    // TODO implement proper session
    return m_session;
  }

  @Override
  protected boolean isActive() {
    return init();
  }

  protected boolean waitForDataReady() {
    if (!init()) {
      return false;
    }
    try {
      // TODO how much sleep is needed?
      Thread.sleep(100L);
    } catch (InterruptedException e) {
    }
    return init();
  }

  protected boolean isConnected() {
    // TODO detect if connection was successful
    return init();
  }

  private boolean init() {
    return m_internals.init();
  }

  @Override
  public void close() {
    m_internals.close();
    super.close();
  }

  public ACInternals internals() {
    return m_internals;
  }

}
