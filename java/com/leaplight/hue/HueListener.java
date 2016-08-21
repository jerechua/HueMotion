package com.leaplight.hue;

import com.philips.lighting.hue.sdk.PHAccessPoint;
import com.philips.lighting.hue.sdk.PHHueSDK;
import com.philips.lighting.hue.sdk.PHMessageType;
import com.philips.lighting.hue.sdk.PHSDKListener;
import com.philips.lighting.model.PHBridge;
import java.util.List;

final class HueListener extends BaseHueListener {

  private final PHHueSDK sdk;

  HueListener(PHHueSDK sdk) {
    this.sdk = sdk;
  }

  @Override public void onAccessPointsFound(List<PHAccessPoint> accessPoints) {
    for (PHAccessPoint accessPoint : accessPoints) {
      System.out.println(accessPoint);
    }
  }

  @Override public void onCacheUpdated(List cacheNotificationsList, PHBridge bridge) {
    if (cacheNotificationsList.contains(PHMessageType.LIGHTS_CACHE_UPDATED)) {
       System.out.println("Lights Cache Updated ");
    }
  }

}