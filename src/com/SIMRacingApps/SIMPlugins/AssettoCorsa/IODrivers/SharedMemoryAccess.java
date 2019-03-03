package com.SIMRacingApps.SIMPlugins.AssettoCorsa.IODrivers;

import com.SIMRacingApps.SIMPlugins.AssettoCorsa.PhysicsMemory;
import com.SIMRacingApps.SIMPlugins.AssettoCorsa.PhysicsMemoryMapper;
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

  public SharedMemoryAccess() {
    init();
  }

  private boolean init() {
    if (!m_initialized) {
      m_hMemMapFile = Windows.openFileMapping(MEMMAPFILENAME_PHYSICS);

      if (m_hMemMapFile != null) {
        m_pSharedMem = Windows.mapViewOfFile(m_hMemMapFile);

        if (m_pSharedMem != null) {
          // TODO is this all?
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

  public PhysicsMemory read() {
    return PhysicsMemoryMapper.map(m_pSharedMem);
  }

  public void close() {
    if (!m_initialized) {
      Windows.unmapViewOfFile(m_pSharedMem);
      Windows.closeHandle(m_hMemMapFile);
      m_initialized = false;
    }
  }

  public void finalize() throws Throwable {
    close();
    super.finalize();
  }
}
