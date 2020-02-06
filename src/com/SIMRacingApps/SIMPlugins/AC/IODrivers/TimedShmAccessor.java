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

import com.SIMRacingApps.Server;
import com.SIMRacingApps.Windows;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;

import java.util.Timer;
import java.util.TimerTask;
import java.util.function.Consumer;

public abstract class TimedShmAccessor<S extends Structure> {
  protected final Consumer<S> consumer;
  protected final Runnable stopNotifier;

  protected Timer timer = null;

  public TimedShmAccessor(
      Consumer<S> consumer, Runnable stopNotifier) {
    this.consumer = consumer;
    this.stopNotifier = stopNotifier;
  }

  public boolean start() {
    SharedMemoryAccess shm = new SharedMemoryAccess();
    if (shm.init()) {
      timer = new Timer("AC-shared-memory-timer");
      timer.scheduleAtFixedRate(getTask(shm), 0L, getUpdatePeriod());
      return true;
    }
    return false;
  }

  public void stop() {
    if (timer != null) {
      timer.cancel();
    }
    if (stopNotifier != null) {
      stopNotifier.run();
    }
  }

  protected abstract long getUpdatePeriod();

  protected abstract TimerTask getTask(SharedMemoryAccess shm);

  protected abstract String getMemMapFileName();

  protected abstract S supplyStruct(Pointer pointer);

  /**
   * @author Harald Jagenteufel
   * @copyright Copyright (C) 2019 Harald Jagenteufel
   * @since 1.8
   * @license Apache License 2.0
   */
  public class SharedMemoryAccess {

    private boolean m_initialized = false;
    private Windows.Handle m_hMemMapFile;
    private Windows.Pointer m_pSharedMem;
    private S sharedStruct;

    /**
     * call this method until it returns true
     * @return true if initialized, false otherwise;
     */
    public boolean init() {
      if (!m_initialized) {
        Server.logger().info("ACSharedMemoryAccess init");

        m_hMemMapFile = Windows.openFileMapping(getMemMapFileName());

        if (m_hMemMapFile != null) {
          m_pSharedMem = Windows.mapViewOfFile(m_hMemMapFile);

          if (m_pSharedMem != null) {
            sharedStruct = m_pSharedMem.asStructure(TimedShmAccessor.this::supplyStruct);
            Server.logger().info("ACSharedMemoryAccess init done");
            m_initialized = true;
          }
          else {
            Server.logger().severe(String.format(
                "Error(%d) mapping Shared Memeory %s",
                Windows.getLastError(),
                Windows.getLastErrorMessage()
            ));
            Windows.closeHandle(m_hMemMapFile);
          }
        }
        else {
          if (Windows.getLastError() != Windows.ERROR_FILE_NOT_FOUND) {        //don't print file doesn't exists. AC may not be running
            Server.logger().severe(String.format(
                "Error(%d) opening Shared Memeory %s",
                Windows.getLastError(),
                Windows.getLastErrorMessage()
            ));
          }
        }
      }
      return m_initialized;
    }

    public S readStruct() throws NotInitializedException {
      if (m_initialized) {
        sharedStruct.read();
        return sharedStruct;
      }
      throw new NotInitializedException();
    }

    public void close() {
      if (m_initialized) {
        Windows.unmapViewOfFile(m_pSharedMem);
        Windows.closeHandle(m_hMemMapFile);
        sharedStruct = null;
        m_initialized = false;
      }
    }

    public void finalize() throws Throwable {
      close();
      super.finalize();
    }
  }

  public static class NotInitializedException extends Exception {
  }
}
