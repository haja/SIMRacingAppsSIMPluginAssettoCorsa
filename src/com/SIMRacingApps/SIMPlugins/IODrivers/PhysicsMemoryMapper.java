package com.SIMRacingApps.SIMPlugins.IODrivers;

import com.SIMRacingApps.SIMPlugins.IODrivers.jnaerator.SPageFilePhysics;
import com.SIMRacingApps.Windows;

/**
 * @author Harald Jagenteufel
 * @copyright Copyright (C) 2019 Harald Jagenteufel
 * @since 1.8
 * @license Apache License 2.0
 */
public class PhysicsMemoryMapper {

  public static SPageFilePhysics map(Windows.Pointer sharedMemory) {
    final SPageFilePhysics sPageFilePhysics = new SPageFilePhysics(sharedMemory.get());
    sPageFilePhysics.read();
    return sPageFilePhysics;
  }

}
