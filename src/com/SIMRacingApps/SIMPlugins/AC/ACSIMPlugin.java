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

/**
 *
 */
package com.SIMRacingApps.SIMPlugins.AC;

import com.SIMRacingApps.Data;
import com.SIMRacingApps.Data.State;
import com.SIMRacingApps.SIMPlugin;
import com.SIMRacingApps.Server;
import com.SIMRacingApps.Session;

public class ACSIMPlugin extends SIMPlugin {

  private ACInternals m_internals;
  private ACSession m_session;

  public ACSIMPlugin() throws SIMPluginException {
    super();
    m_internals = new ACInternals();
    m_session = new ACSession(this);
    Server.logger().info("AssettoCorsaSIMPluging created");
  }

  @Override
  public Data getSIMName() {
    return super.getSIMName().setValue("AC").setState(Data.State.NORMAL);
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
    return internals().isSessionRunning();
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
