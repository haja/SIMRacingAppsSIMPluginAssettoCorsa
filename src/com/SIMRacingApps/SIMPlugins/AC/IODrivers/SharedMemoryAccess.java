package com.SIMRacingApps.SIMPlugins.AC.IODrivers;

import com.SIMRacingApps.SIMPlugins.AC.IODrivers.jnaerator.SPageFilePhysics;
import com.SIMRacingApps.SIMPlugins.AC.IODrivers.jnaerator.SPageFileStatic;
import com.SIMRacingApps.Server;
import com.SIMRacingApps.Windows;

/**
 * @author Harald Jagenteufel
 * @copyright Copyright (C) 2019 Harald Jagenteufel
 * @since 1.8
 * @license Apache License 2.0
 */
public class SharedMemoryAccess {

  /**
   * provided every 10ms.
   */
  private final static String MEMMAPFILENAME_PHYSICS = "Local\\acpmf_physics";

  private boolean m_initialized = false;
  private Windows.Handle m_hMemMapFile;
  private Windows.Pointer m_pSharedMem;
  private SPageFilePhysics physicsStruct;
  private SPageFileStatic staticStruct;

  /**
   * call this method until it returns true
   * @return true if initialized, flase otherwise;
   */
  public boolean init() {
    if (!m_initialized) {
      Server.logger().info("ACSharedMemoryAccess init");
      m_hMemMapFile = Windows.openFileMapping(MEMMAPFILENAME_PHYSICS);

      if (m_hMemMapFile != null) {
        m_pSharedMem = Windows.mapViewOfFile(m_hMemMapFile);

        if (m_pSharedMem != null) {
          physicsStruct = new SPageFilePhysics(m_pSharedMem.get());
          staticStruct = new SPageFileStatic(m_pSharedMem.get());
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
        if (Windows.getLastError() != Windows.ERROR_FILE_NOT_FOUND) {        //don't print file doesn't exists. iRacing may not be running
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

  public SPageFilePhysics readPhysics() throws NotInitializedException {
    if (m_initialized) {
      physicsStruct.read();
      return physicsStruct;
    }
    throw new NotInitializedException();
  }

  public SPageFileStatic readStatic() throws NotInitializedException {
    if (m_initialized) {
      staticStruct.read();
      return staticStruct;
    }
    throw new NotInitializedException();
  }

  public void close() {
    if (m_initialized) {
      Windows.unmapViewOfFile(m_pSharedMem);
      Windows.closeHandle(m_hMemMapFile);
      physicsStruct = null;
      m_initialized = false;
    }
  }

  public void finalize() throws Throwable {
    close();
    super.finalize();
  }

  public class NotInitializedException extends Exception {
  }
}