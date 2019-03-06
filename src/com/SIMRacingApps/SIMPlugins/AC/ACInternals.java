package com.SIMRacingApps.SIMPlugins.AC;

import com.SIMRacingApps.SIMPlugins.AC.IODrivers.GraphicsAccessor;
import com.SIMRacingApps.SIMPlugins.AC.IODrivers.PhysicsAccessor;
import com.SIMRacingApps.SIMPlugins.AC.IODrivers.SessionAccessor;
import com.SIMRacingApps.SIMPlugins.AC.IODrivers.jnaerator.SPageFileGraphic;
import com.SIMRacingApps.SIMPlugins.AC.IODrivers.jnaerator.SPageFilePhysics;
import com.SIMRacingApps.SIMPlugins.AC.IODrivers.jnaerator.SPageFileStatic;
import com.SIMRacingApps.Server;

public class ACInternals {

  private final PhysicsAccessor physicsAccessor;
  private final SessionAccessor sessionAccessor;
  private final GraphicsAccessor graphicsAccessor;
  private SPageFilePhysics currentPhysics;
  private SPageFileStatic sessionStatic;
  private SPageFileGraphic currentGraphics;
  private volatile boolean initialized = false;

  ACInternals() {
    physicsAccessor = new PhysicsAccessor(
        phy -> this.currentPhysics = phy
        , () -> this.initialized = false);
    sessionAccessor = new SessionAccessor(
        sess -> this.sessionStatic = sess
        , () -> {});
    graphicsAccessor = new GraphicsAccessor(
        graph -> this.currentGraphics = graph
        , () -> {});
  }

  public boolean isSessionRunning() {
    // TODO check if session is running
    //  is this just 'initialized'?
    return sessionStatic != null && sessionStatic.acVersion != null;
  }

  public SPageFilePhysics getCurrentPhysics() {
    return currentPhysics;
  }

  public SPageFileStatic getSessionStatic() {
    return sessionStatic;
  }

  public SPageFileGraphic getCurrentGraphics() {
    return currentGraphics;
  }

  public boolean init() {
    if (initialized) {
      return true;
    }
    return _init();
  }

  private boolean _init() {
    Server.logger().info("ACInternals _init");
    initialized = physicsAccessor.start() &&
        sessionAccessor.start() &&
        graphicsAccessor.start();
    return initialized;
  }

  public void close() {
    this.initialized = false;
    physicsAccessor.stop();
    sessionAccessor.stop();
    graphicsAccessor.stop();
  }
}
