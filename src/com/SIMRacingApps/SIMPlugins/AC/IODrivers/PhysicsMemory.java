package com.SIMRacingApps.SIMPlugins.AC.IODrivers;

/**
 * @author Harald Jagenteufel
 * @copyright Copyright (C) 2019 Harald Jagenteufel
 * @since 1.8
 * @license Apache License 2.0
 */
public class PhysicsMemory {

  public final int packetId;
  public final float gas;
  public final float brake;
  public final float fuel;
  public final int gear;
  public final int rpms;

  public PhysicsMemory(int packetId, float gas, float brake, float fuel, int gear, int rpms) {
    this.packetId = packetId;
    this.gas = gas;
    this.brake = brake;
    this.fuel = fuel;
    this.gear = gear;
    this.rpms = rpms;
  }
}
