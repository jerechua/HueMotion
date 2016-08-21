package com.huey;

import com.philips.lighting.hue.sdk.PHAccessPoint;
import com.philips.lighting.hue.sdk.PHMessageType;
import com.philips.lighting.hue.sdk.PHSDKListener;
import com.philips.lighting.model.PHBridge;
import java.util.List;

abstract class BaseHueListener implements PHSDKListener {

  @Override public void onAccessPointsFound(List<PHAccessPoint> accessPoints) {
    System.out.println("Access points found");
    System.out.println(accessPoints);
  }

  @Override public void onAuthenticationRequired(PHAccessPoint accessPoint) {
    System.out.println("authentication required");
    System.out.println(accessPoint);
  }

  @Override public void onBridgeConnected(PHBridge bridge, String username) {
    System.out.println("onBridgeConnected");
    System.out.println(bridge);
    System.out.println(username);
  }

  @Override public void onCacheUpdated(List<Integer> cacheNotificationsList, PHBridge bridge) {
    System.out.println("onCacheUpdated");
    System.out.println(cacheNotificationsList);
    System.out.println(bridge);
  }

  @Override public void onConnectionLost(PHAccessPoint accessPoint) {
    System.out.println("onConnectionLost");
    System.out.println(accessPoint);
  }

  @Override public void onConnectionResumed(PHBridge bridge) {
    System.out.println("onConnectionResumed");
    System.out.println(bridge);
  }

  @Override public void onError(int code, final String message) {
    System.out.println("onError");
    System.out.println(code);
    System.out.println(message);
  }

  @Override public void onParsingErrors(List parsingErrorsList) {
    System.out.println("onParsingErrors");
    System.out.println(parsingErrorsList);
  }
}