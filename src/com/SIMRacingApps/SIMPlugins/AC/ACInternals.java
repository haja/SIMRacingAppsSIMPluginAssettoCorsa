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

package com.SIMRacingApps.SIMPlugins.AC;

import com.SIMRacingApps.SIMPlugins.AC.IODrivers.GraphicsAccessor;
import com.SIMRacingApps.SIMPlugins.AC.IODrivers.PhysicsAccessor;
import com.SIMRacingApps.SIMPlugins.AC.IODrivers.SessionAccessor;
import com.SIMRacingApps.SIMPlugins.AC.IODrivers.jnaerator.SPageFileGraphic;
import com.SIMRacingApps.SIMPlugins.AC.IODrivers.jnaerator.SPageFilePhysics;
import com.SIMRacingApps.SIMPlugins.AC.IODrivers.jnaerator.SPageFileStatic;
import com.SIMRacingApps.Server;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

public class ACInternals {
  private final static String TAG = "ACInternals: ";

  private final PhysicsAccessor physicsAccessor;
  private final SessionAccessor sessionAccessor;
  private final GraphicsAccessor graphicsAccessor;
  private SPageFilePhysics currentPhysics;
  private SPageFileStatic sessionStatic;
  private SPageFileGraphic currentGraphics;
  private volatile boolean initialized = false;
  private List<Consumer<ACEvent>> eventListeners = new LinkedList<>();
  private String prevSession;

  ACInternals() {
    physicsAccessor = new PhysicsAccessor(
        phy -> this.currentPhysics = phy
        , () -> this.initialized = false);
    sessionAccessor = new SessionAccessor(
        this::updateSession
        , () -> {
    });
    graphicsAccessor = new GraphicsAccessor(
        graph -> this.currentGraphics = graph
        , () -> {
    });
  }

  public boolean isSessionRunning() {
    // TODO check if session is running
    //  is this just 'initialized'?
    return sessionStatic != null && sessionStatic.acVersion != null;
  }

  public SPageFilePhysics getCurrentPhysics() {
    return currentPhysics;
  }

  public SPageFileStatic getSessionStatic() {
    return sessionStatic;
  }

  public SPageFileGraphic getCurrentGraphics() {
    return currentGraphics;
  }

  public boolean init() {
    if (initialized) {
      return true;
    }
    return _init();
  }

  private boolean _init() {
    log("_init");
    initialized = physicsAccessor.start() &&
        sessionAccessor.start() &&
        graphicsAccessor.start();
    return initialized;
  }

  public void close() {
    this.initialized = false;
    physicsAccessor.stop();
    sessionAccessor.stop();
    graphicsAccessor.stop();
  }

  private void updateSession(SPageFileStatic sess) {
    this.sessionStatic = sess;

    if (prevSession == null) {
      prevSession = sess.toString();

    } else {
      boolean sessionChanged = !prevSession.equals(sess.toString());
      prevSession = sess.toString();
      if (sessionChanged) {
        log("session changed");
        notify(new ACSessionChangedEvent());
      }
    }
  }

  private void log(String msg) {
    Server.logger().info(TAG + msg);
  }

  private void notify(ACEvent event) {
    eventListeners.forEach(a -> a.accept(event));
  }

  public void registerForEvent(Consumer<ACEvent> eventConsumer) {
    eventListeners.add(eventConsumer);
  }

  public interface ACEvent {
  }

  public static class ACSessionChangedEvent implements ACEvent {
  }

}
