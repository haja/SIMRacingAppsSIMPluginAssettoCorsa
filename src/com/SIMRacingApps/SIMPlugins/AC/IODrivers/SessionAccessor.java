package com.SIMRacingApps.SIMPlugins.AC.IODrivers;

import com.SIMRacingApps.SIMPlugins.AC.IODrivers.jnaerator.SPageFileStatic;
import com.SIMRacingApps.Server;
import com.sun.jna.Pointer;

import java.util.TimerTask;
import java.util.function.Consumer;

/**
 * @author Harald Jagenteufel
 * @copyright Copyright (C) 2019 Harald Jagenteufel
 * @since 1.8
 * @license Apache License 2.0
 */
public class SessionAccessor extends TimedShmAccessor<SPageFileStatic> {

  private final static String MEMMAPFILENAME_SESSION = "Local\\acpmf_static";
  // TODO this should be once per session. how to do that?
  public static final long SESSION_UPDATE_PERIOD = 5000L;

  public SessionAccessor(Consumer<SPageFileStatic> staticConsumer, Runnable stopNotifier) {
    super(staticConsumer, stopNotifier);
  }


  @Override
  protected long getUpdatePeriod() {
    return SESSION_UPDATE_PERIOD;
  }

  @Override
  protected TimerTask getTask(SharedMemoryAccess shm) {
    return new SessionStaticTask(shm);
  }

  @Override
  protected String getMemMapFileName() {
    return MEMMAPFILENAME_SESSION;
  }

  @Override
  protected SPageFileStatic supplyStruct(Pointer pointer) {
    return new SPageFileStatic(pointer);
  }

  private class SessionStaticTask extends TimerTask {
    private SharedMemoryAccess sharedMemory;

    public SessionStaticTask(SharedMemoryAccess shm) {
      sharedMemory = shm;
    }

    @Override
    public void run() {
      try {
        final SPageFileStatic session = sharedMemory.readStruct();
        consumer.accept(session);
        Server.logger().finest(
            "ACStaticSessionAccessor AC version: " + new String(session.acVersion));
      } catch (NotInitializedException e) {
        sharedMemory.close();
        stop();
      }
    }
  }
}
