/*
 * Copyright (c) 2020 Harald Jagenteufel.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.SIMRacingApps.SIMPlugins.AC.IODrivers;

import com.SIMRacingApps.SIMPlugins.AC.IODrivers.jnaerator.SPageFilePhysics;
import com.SIMRacingApps.Server;
import com.sun.jna.Pointer;

import java.util.TimerTask;
import java.util.function.Consumer;

public class PhysicsAccessor extends TimedShmAccessor<SPageFilePhysics> {

  private final static String MEMMAPFILENAME_PHYSICS = "Local\\acpmf_physics";
  private final static long PHYSICS_UPDATE_PERIOD = 10L;

  public PhysicsAccessor(Consumer<SPageFilePhysics> physicsMemoryConsumer, Runnable stopNotifier) {
    super(physicsMemoryConsumer, stopNotifier);
  }

  @Override
  protected PhysicsTask getTask(SharedMemoryAccess shm) {
    return new PhysicsTask(shm);
  }

  @Override
  protected String getMemMapFileName() {
    return MEMMAPFILENAME_PHYSICS;
  }

  @Override
  protected SPageFilePhysics supplyStruct(Pointer pointer) {
    return new SPageFilePhysics(pointer);
  }

  @Override
  protected long getUpdatePeriod() {
    return PhysicsAccessor.PHYSICS_UPDATE_PERIOD;
  }

  private class PhysicsTask extends TimerTask {
    private SharedMemoryAccess sharedMemory;

    public PhysicsTask(SharedMemoryAccess shm) {
      sharedMemory = shm;
    }

    @Override
    public void run() {
      try {
        final SPageFilePhysics physics = sharedMemory.readStruct();
        consumer.accept(physics);
        Server.logger().finest("ACPhysicsAccessor read gas: " + physics.gas);
      } catch (NotInitializedException e) {
        sharedMemory.close();
        stop();
      }
    }
  }
}
