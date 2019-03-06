package com.SIMRacingApps.SIMPlugins.AC.IODrivers;

import com.SIMRacingApps.SIMPlugins.AC.IODrivers.SharedMemoryAccess.NotInitializedException;
import com.SIMRacingApps.SIMPlugins.AC.IODrivers.jnaerator.SPageFileStatic;
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
// TODO factor out accessor code
public class SessionAccessor {

  // TODO this should be once per session. how to do that?
  public static final long SESSION_UPDATE_PERIOD = 5000L;

  private Timer timer = null;

  private final Consumer<SPageFileStatic> staticConsumer;

  public SessionAccessor(Consumer<SPageFileStatic> staticConsumer) {
    this.staticConsumer = staticConsumer;
  }

  public void start(SharedMemoryAccess shm) {
    timer = new Timer("AC-static-session-timer");
    timer.scheduleAtFixedRate(new SessionStaticTask(shm), 0L, SESSION_UPDATE_PERIOD);
  }

  public void stop() {
    if (timer != null) {
      timer.cancel();
    }
  }

  private class SessionStaticTask extends TimerTask {
    private SharedMemoryAccess sharedMemory;

    public SessionStaticTask(SharedMemoryAccess shm) {
      sharedMemory = shm;
    }

    @Override
    public void run() {
      try {
        final SPageFileStatic session = sharedMemory.readStatic();
        staticConsumer.accept(session);
        Server.logger().finest(
            "ACStaticSessionAccessor AC version: " + new String(session.acVersion));
      } catch (NotInitializedException e) {
        sharedMemory.close();
        stop();
      }
    }
  }
}
