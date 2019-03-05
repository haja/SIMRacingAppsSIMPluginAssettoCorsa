package com.SIMRacingApps.SIMPlugins.IODrivers;

import com.SIMRacingApps.SIMPlugins.IODrivers.SharedMemoryAccess.NotInitializedException;
import com.SIMRacingApps.SIMPlugins.IODrivers.jnaerator.SPageFilePhysics;
import com.SIMRacingApps.Server;

import java.util.Timer;
import java.util.TimerTask;
import java.util.function.Consumer;

/**
 * @author Harald Jagenteufel
 * @copyright Copyright (C) 2019 Harald Jagenteufel
 * @since 1.8
 * @license Apache License 2.0
 */
public class PhysicsAccessor {

  // TODO this should be 10 milisec instead
  public static final long PHYSICS_UPDATE_PERIOD = 1000L;

  private Timer timer = null;

  private final Consumer<SPageFilePhysics> physicsMemoryConsumer;
  private final Runnable stopNotifier;

  public PhysicsAccessor(Consumer<SPageFilePhysics> physicsMemoryConsumer, Runnable stopNotifier) {
    this.physicsMemoryConsumer = physicsMemoryConsumer;
    this.stopNotifier = stopNotifier;
  }

  public void start(SharedMemoryAccess shm) {
    timer = new Timer("AC-physics-timer");
    timer.scheduleAtFixedRate(new PhysicsTask(shm), 0L, PHYSICS_UPDATE_PERIOD);
  }

  public void stop() {
    if (timer != null) {
      timer.cancel();
    }
    if (stopNotifier != null) {
      stopNotifier.run();
    }
  }

  private class PhysicsTask extends TimerTask {
    private SharedMemoryAccess sharedMemory;

    public PhysicsTask(SharedMemoryAccess shm) {
      sharedMemory = shm;
    }

    @Override
    public void run() {
      try {
        final SPageFilePhysics physics = sharedMemory.readPhysics();
        physicsMemoryConsumer.accept(physics);
        Server.logger().info("ACPhysicsAccessor read: " + physics.gas);
      } catch (NotInitializedException e) {
        sharedMemory.close();
        stop();
      }
    }
  }
}
