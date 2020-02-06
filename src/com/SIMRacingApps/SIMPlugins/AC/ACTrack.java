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

import com.SIMRacingApps.Data;
import com.SIMRacingApps.Data.State;
import com.SIMRacingApps.Track;

public class ACTrack extends Track {

  private final ACSIMPlugin plugin;

  public ACTrack(ACSIMPlugin plugin) {
    super(plugin);
    this.plugin = plugin;
  }

  @Override
  protected boolean _loadTrack() {
    return false;
  }

  @Override
  public Data getName() {
    Data d = super.getName();
    d.setState(State.OFF);
    if (plugin.isConnected()) {
      d.setValue(String.valueOf(plugin.internals().getSessionStatic().track));
      d.setState(State.NORMAL);
    }
    return d;
  }
}
