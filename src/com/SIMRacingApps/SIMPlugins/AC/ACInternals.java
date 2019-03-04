package com.SIMRacingApps.SIMPlugins.AC;

import com.SIMRacingApps.SIMPlugins.AC.IODrivers.PhysicsAccessor;
import com.SIMRacingApps.SIMPlugins.AC.IODrivers.PhysicsMemory;
import com.SIMRacingApps.SIMPlugins.AC.IODrivers.SharedMemoryAccess;
import com.SIMRacingApps.Server;

public class ACInternals {

  private final PhysicsAccessor physicsAccessor;
  private PhysicsMemory currentPhysics;
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

  public PhysicsMemory getCurrentPhysics() {
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
