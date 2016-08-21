package com.leaplight.hue;

import com.philips.lighting.hue.sdk.PHHueSDK;

public final class HueController {

  private static final int CONNECT_WAIT_INTERVAL = 100;

  private final String appName;
  private final PHHueSDK sdk;

  public HueController(String appName) {
    this.appName = appName;
    // TODO: For next version, support multiple bridges. This probably means that this code will
    // need to be moved to a "connect()" function.
    this.sdk = setupSDK(appName);
  }

  private PHHueSDK setupSDK(String appName) {

    PHHueSDK sdk = PHHueSDK.getInstance();
    HueListener listener = new HueListener(sdk);

    sdk.setAppName(appName);
    sdk.getNotificationManager().registerSDKListener(listener);

    HueBridgeFinder.newFinder(sdk)
      .searchUPNP(true)
      .searchPortal(true)
      .find();

    while (true) {
      if (listener.isConnected()) {
        return sdk;
      }
      System.out.println("Waiting for SDK to connect...");
      try {
        Thread.sleep(CONNECT_WAIT_INTERVAL);
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    }
  }
}