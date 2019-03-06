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
