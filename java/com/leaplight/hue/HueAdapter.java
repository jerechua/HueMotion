package com.leaplight.hue;

import com.philips.lighting.hue.sdk.PHHueSDK;

public final class HueAdapter {

  private final String appName;

  public HueAdapter(String appName) {
    this.appName = appName;
    setupSDK(appName);
  }

  private void setupSDK(String appName) {
    PHHueSDK sdk = PHHueSDK.getInstance();
    sdk.setAppName(appName);
    sdk.getNotificationManager().registerSDKListener(new HueListener(sdk));

    HueBridgeFinder.newFinder(sdk)
      .searchUPNP(true)
      .searchPortal(true)
      .find();

  }
}