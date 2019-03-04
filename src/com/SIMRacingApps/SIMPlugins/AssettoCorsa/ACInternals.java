package com.SIMRacingApps.SIMPlugins.AssettoCorsa;

import com.SIMRacingApps.SIMPlugins.AssettoCorsa.IODrivers.PhysicsAccessor;
import com.SIMRacingApps.SIMPlugins.AssettoCorsa.IODrivers.PhysicsMemory;
import com.SIMRacingApps.SIMPlugins.AssettoCorsa.IODrivers.SharedMemoryAccess;

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
    SharedMemoryAccess shm = new SharedMemoryAccess();
    if (shm.init()) {
      physicsAccessor.start(shm);
      return true;
    }
    return false;
  }

  public void close() {
    this.initialized = false;
    physicsAccessor.stop();
  }
}
