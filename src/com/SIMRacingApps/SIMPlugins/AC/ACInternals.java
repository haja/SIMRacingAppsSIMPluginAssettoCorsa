package com.SIMRacingApps.SIMPlugins.AC;

import com.SIMRacingApps.SIMPlugins.IODrivers.PhysicsAccessor;
import com.SIMRacingApps.SIMPlugins.IODrivers.SharedMemoryAccess;
import com.SIMRacingApps.SIMPlugins.IODrivers.jnaerator.SPageFilePhysics;
import com.SIMRacingApps.Server;

public class ACInternals {

  private final PhysicsAccessor physicsAccessor;
  private SPageFilePhysics currentPhysics;
  private volatile boolean initialized = false;

  ACInternals() {
    physicsAccessor = new PhysicsAccessor(
        phy -> this.currentPhysics = phy
        , () -> this.initialized = false);
  }

  public boolean isSessionRunning() {
    // TODO check if session is running
    //  is this just 'initialized'?
    return true;
  }

  public SPageFilePhysics getCurrentPhysics() {
    return currentPhysics;
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
