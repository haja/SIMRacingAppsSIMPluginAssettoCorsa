package com.SIMRacingApps.SIMPlugins.AC.IODrivers;

import com.SIMRacingApps.SIMPlugins.AC.IODrivers.SharedMemoryAccess.NotInitializedException;

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

  public static final long PHYSICS_UPDATE_PERIOD = 10L;

  private Timer timer = null;

  private final Consumer<PhysicsMemory> physicsMemoryConsumer;
  private final Runnable stopNotifier;

  public PhysicsAccessor(Consumer<PhysicsMemory> physicsMemoryConsumer, Runnable stopNotifier) {
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
        physicsMemoryConsumer.accept(sharedMemory.readPhysics());
      } catch (NotInitializedException e) {
        sharedMemory.close();
        stop();
      }
    }
  }
}
