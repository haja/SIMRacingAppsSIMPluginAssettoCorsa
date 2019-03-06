package com.SIMRacingApps.SIMPlugins.AC;

import com.SIMRacingApps.SIMPlugins.AC.IODrivers.PhysicsAccessor;
import com.SIMRacingApps.SIMPlugins.AC.IODrivers.SessionAccessor;
import com.SIMRacingApps.SIMPlugins.AC.IODrivers.SharedMemoryAccess;
import com.SIMRacingApps.SIMPlugins.AC.IODrivers.jnaerator.SPageFilePhysics;
import com.SIMRacingApps.SIMPlugins.AC.IODrivers.jnaerator.SPageFileStatic;
import com.SIMRacingApps.Server;

public class ACInternals {

  private final PhysicsAccessor physicsAccessor;
  private final SessionAccessor sessionAccessor;
  private SPageFilePhysics currentPhysics;
  private SPageFileStatic sessionStatic;
  private volatile boolean initialized = false;

  ACInternals() {
    physicsAccessor = new PhysicsAccessor(
        phy -> this.currentPhysics = phy
        , () -> this.initialized = false);
    sessionAccessor = new SessionAccessor(
        sess -> this.sessionStatic = sess
    );
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

  public boolean init() {
    if (initialized) {
      return true;
    }
    return _init();
  }

  private boolean _init() {
    Server.logger().info("ACInternals _init");
    SharedMemoryAccess shm = new SharedMemoryAccess();
    if (shm.init()) {
      physicsAccessor.start(shm);
      sessionAccessor.start(shm);
      initialized = true;
      return true;
    }
    return false;
  }

  public void close() {
    this.initialized = false;
    physicsAccessor.stop();
  }
}
