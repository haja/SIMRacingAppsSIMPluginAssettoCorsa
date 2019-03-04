package com.SIMRacingApps.SIMPlugins.AC;

import com.SIMRacingApps.Track;

public class ACTrack extends Track {

  public ACTrack(ACSIMPlugin plugin) {
    super(plugin);
  }

  @Override
  protected boolean _loadTrack() {
    return false;
  }
}
