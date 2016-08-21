package com.leaplight.hue;

import com.philips.lighting.hue.sdk.PHHueSDK;
import com.philips.lighting.hue.sdk.PHBridgeSearchManager;

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

    PHBridgeSearchManager searchManager
        = (PHBridgeSearchManager)sdk.getSDKService(PHHueSDK.SEARCH_BRIDGE);
    searchManager.search(true, true);
  }
}