package com.SIMRacingApps.SIMPlugins.IODrivers.jnaerator;

import com.sun.jna.Pointer;
import com.sun.jna.Structure;

import java.util.Arrays;
import java.util.List;

/**
 * <i>native declaration : SharedFileOut.h:92</i><br>
 * This file was autogenerated by <a href="http://jnaerator.googlecode.com/">JNAerator</a>,<br>
 * a tool written by <a href="http://ochafik.com/">Olivier Chafik</a> that <a href="http://code.google.com/p/jnaerator/wiki/CreditsAndLicense">uses a few opensource projects.</a>.<br>
 * For help, please visit <a href="http://nativelibs4java.googlecode.com/">NativeLibs4Java</a> , <a href="http://rococoa.dev.java.net/">Rococoa</a>, or <a href="http://jna.dev.java.net/">JNA</a>.
 */
public class SPageFileGraphic extends Structure {
  public volatile int packetId;
  /**
   * C type : AC_STATUS
   */
  public volatile int status;
  /**
   * C type : AC_SESSION_TYPE
   */
  public volatile int session;
  /**
   * C type : wchar_t[15]
   */
  public volatile char[] currentTime = new char[15];
  /**
   * C type : wchar_t[15]
   */
  public volatile char[] lastTime = new char[15];
  /**
   * C type : wchar_t[15]
   */
  public volatile char[] bestTime = new char[15];
  /**
   * C type : wchar_t[15]
   */
  public volatile char[] split = new char[15];
  public volatile int completedLaps;
  public volatile int position;
  public volatile int iCurrentTime;
  public volatile int iLastTime;
  public volatile int iBestTime;
  public volatile float sessionTimeLeft;
  public volatile float distanceTraveled;
  public volatile int isInPit;
  public volatile int currentSectorIndex;
  public volatile int lastSectorTime;
  public volatile int numberOfLaps;
  /**
   * C type : wchar_t[33]
   */
  public volatile char[] tyreCompound = new char[33];
  public volatile float replayTimeMultiplier;
  public volatile float normalizedCarPosition;
  /**
   * C type : float[3]
   */
  public volatile float[] carCoordinates = new float[3];
  public volatile float penaltyTime;
  /**
   * C type : AC_FLAG_TYPE
   */
  public volatile int flag;
  public volatile int idealLineOn;
  public volatile int isInPitLane;
  public volatile float surfaceGrip;

  public SPageFileGraphic() {
    super();
  }

  protected List<String> getFieldOrder() {
    return Arrays
        .asList("packetId", "status", "session", "currentTime", "lastTime", "bestTime", "split",
            "completedLaps", "position", "iCurrentTime", "iLastTime", "iBestTime",
            "sessionTimeLeft", "distanceTraveled", "isInPit", "currentSectorIndex",
            "lastSectorTime", "numberOfLaps", "tyreCompound", "replayTimeMultiplier",
            "normalizedCarPosition", "carCoordinates", "penaltyTime", "flag", "idealLineOn",
            "isInPitLane", "surfaceGrip");
  }

  public SPageFileGraphic(Pointer peer) {
    super(peer);
  }

  public static class ByReference extends SPageFileGraphic implements Structure.ByReference {

  }

  ;

  public static class ByValue extends SPageFileGraphic implements Structure.ByValue {

  }

  ;
}
