package com.SIMRacingApps.SIMPlugins.AssettoCorsa;

import com.SIMRacingApps.Windows;

import java.nio.ByteBuffer;

/**
 * @author Harald Jagenteufel
 * @copyright Copyright (C) 2019 Harald Jagenteufel
 * @since 1.8
 * @license Apache License 2.0
 */
public class PhysicsMemoryMapper {

  // TODO get correct size from AC docs
  private static final long PHYSICS_BUFFER_SIZE = 6 * 4;

  public static PhysicsMemory map(Windows.Pointer sharedMemory) {
    final ByteBuffer b =
        sharedMemory.getByteBuffer(0, PHYSICS_BUFFER_SIZE).asReadOnlyBuffer();
    return new PhysicsMemory(
        b.getInt(), b.getFloat(), b.getFloat(), b.getFloat(), b.getInt(), b.getInt());
  }

}
