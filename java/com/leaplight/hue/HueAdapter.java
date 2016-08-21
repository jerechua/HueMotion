package com.leaplight.hue;

import com.philips.lighting.hue.sdk.PHHueSDK;

public final class HueAdapter {

  public HueAdapter() {
      PHHueSDK sdk = PHHueSDK.getInstance();
      sdk.getNotificationManager().registerSDKListener(new HueListener(sdk));
  }

}