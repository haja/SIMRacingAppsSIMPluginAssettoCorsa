package com.SIMRacingApps.SIMPlugins.AC;

import com.SIMRacingApps.Car;
import com.SIMRacingApps.Data;
import com.SIMRacingApps.Data.State;
import com.SIMRacingApps.Gauge;
import com.SIMRacingApps.SIMPlugins.AC.gauges.*;

/**
 * @author Harald Jagenteufel
 * @copyright Copyright (C) 2019 Harald Jagenteufel
 * @since 1.8
 * @license Apache License 2.0
 */
public class ACCar extends Car {

  private final ACSIMPlugin simPlugin;
  private boolean valid = false;

  public ACCar(ACSIMPlugin SIMPlugin) {
    super(SIMPlugin);
    simPlugin = SIMPlugin;
    initialize();
  }

  public void initialize() {
    if (!simPlugin.internals().isSessionRunning()) {
      return;
    }

    _setGauge(new Throttle(simPlugin, this));
    _setGauge(new Brake(simPlugin, this));
    _setGauge(new Clutch(simPlugin, this));
    _setGauge(new BrakeBias(simPlugin, this));
    final Gear gear = new Gear(simPlugin, this);
    _setGauge(gear);
    _setGauge(new Tachometer(simPlugin, this, gear));
    _setGauge(new Speedometer(simPlugin, this));
    _setGauge(new EnginePower(simPlugin, this));

    // TODO fix id loading
    m_id = 1;
    m_name = "my super car";
    m_carIdentifier = "I1";

    valid = true;
    _postInitialization();
  }

  @Override
  public boolean isValid() {
    return valid && simPlugin.isConnected();
  }

  @Override
  public boolean isME() {
    // TODO implement
    return true;
  }

  @Override
  public Data getId() {
    // TODO implement
    return new Data("Car/"+m_carIdentifier+"/Id","1","id", State.NORMAL);
  }

  @Override
  protected void _setGauge(Gauge gauge) {
    m_gauges.put(gauge.getType().getString().toLowerCase(), gauge);
  }

}
