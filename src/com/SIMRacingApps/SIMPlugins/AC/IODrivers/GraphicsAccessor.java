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

import com.SIMRacingApps.SIMPlugins.AC.IODrivers.jnaerator.SPageFileGraphic;
import com.SIMRacingApps.Server;
import com.sun.jna.Pointer;

import java.util.TimerTask;
import java.util.function.Consumer;

public class GraphicsAccessor extends TimedShmAccessor<SPageFileGraphic> {

  private final static String MEMMAPFILENAME_GRAPHICS = "Local\\acpmf_graphics";
  private final static long GRAPHICS_UPDATE_PERIOD = 1000L;

  public GraphicsAccessor(Consumer<SPageFileGraphic> graphicsConsumer, Runnable stopNotifier) {
    super(graphicsConsumer, stopNotifier);
  }

  @Override
  protected PhysicsTask getTask(SharedMemoryAccess shm) {
    return new PhysicsTask(shm);
  }

  @Override
  protected String getMemMapFileName() {
    return MEMMAPFILENAME_GRAPHICS;
  }

  @Override
  protected SPageFileGraphic supplyStruct(Pointer pointer) {
    return new SPageFileGraphic(pointer);
  }

  @Override
  protected long getUpdatePeriod() {
    return GraphicsAccessor.GRAPHICS_UPDATE_PERIOD;
  }

  private class PhysicsTask extends TimerTask {
    private SharedMemoryAccess sharedMemory;

    public PhysicsTask(SharedMemoryAccess shm) {
      sharedMemory = shm;
    }

    @Override
    public void run() {
      try {
        final SPageFileGraphic graphics = sharedMemory.readStruct();
        consumer.accept(graphics);
        Server.logger().finest("ACGraphicsAccessor read position: " + graphics.position);
      } catch (NotInitializedException e) {
        sharedMemory.close();
        stop();
      }
    }
  }
}
