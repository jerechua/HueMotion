package com.huey;

import com.philips.lighting.hue.sdk.PHAccessPoint;
import com.philips.lighting.hue.sdk.PHHueSDK;
import com.philips.lighting.hue.sdk.PHMessageType;
import com.philips.lighting.hue.sdk.PHMessageType;
import com.philips.lighting.hue.sdk.PHSDKListener;
import com.philips.lighting.model.PHBridge;
import com.philips.lighting.model.PHBridgeResourcesCache;
import java.util.List;

final class HueListener extends BaseHueListener {

  // TODO: Store this info somewhere?
  private static final String USERNAME = "sj9XU74wfQxbjvVrzebxdjX4XbzTN6BNe0imGwTv";

  private final PHHueSDK sdk;

  // Synchronized. Checks whether or not the selected bridge is connected.
  private Boolean isBridgeConnected = false;

  HueListener(PHHueSDK sdk) {
    this.sdk = sdk;
  }

  @Override public void onAccessPointsFound(List<PHAccessPoint> accessPoints) {

    if (accessPoints.size() <= 0) {
      // TODO: Consider re-searching access points here.
      System.out.println("No access points found");
      return;
    }

    for (PHAccessPoint accessPoint : accessPoints) {
      System.out.println(accessPoint);
    }
    sdk.getAccessPointsFound().clear();
    sdk.getAccessPointsFound().addAll(accessPoints);

    // TODO: Connect to multiple access points.
    connect(accessPoints.get(0));
  }

  @Override public void onAuthenticationRequired(PHAccessPoint accessPoint) {
    System.out.println("Authentication is required! Please press the bridge button.");
    sdk.startPushlinkAuthentication(accessPoint) ;
  }

  @Override public void onBridgeConnected(PHBridge bridge, String username) {
    System.out.println("Successfully connected to bridge.");
    sdk.setSelectedBridge(bridge);
    sdk.enableHeartbeat(bridge, PHHueSDK.HB_INTERVAL);
    synchronized(isBridgeConnected) {
      isBridgeConnected = true;
    }
  }

  @Override public void onError(int code, final String message) {
    switch (code) {
      case PHMessageType.PUSHLINK_BUTTON_NOT_PRESSED:
        System.out.println("Pushlink not pressed! Waiting..");
        break;
      case PHMessageType.PUSHLINK_AUTHENTICATION_FAILED:
        System.out.println("Pushlink failed, possibly due to button not being pressed. Good bye.");
        System.exit(1);
      default:
        System.out.println("onError");
        System.out.println(code);
        System.out.println(message);
    }
  }

  // These two get updated too frequently.. why?
  @Override public void onCacheUpdated(List<Integer> cacheNotificationsList, PHBridge bridge) {}
  @Override public void onConnectionResumed(PHBridge bridge) {}

  private void connect(PHAccessPoint accessPoint) {
    System.out.println("Connecting to access point: " + accessPoint.getMacAddress());
    accessPoint.setUsername(USERNAME);
    sdk.connect(accessPoint);
  }

  private void setConnected() {
    synchronized(isBridgeConnected) {
      isBridgeConnected = true;
    }
  }

  public boolean isConnected() {
    synchronized(isBridgeConnected) {
      return isBridgeConnected;
    }
  }

  public PHBridge getSelectedBridge() {
    return sdk.getSelectedBridge();
  }

  public PHBridgeResourcesCache getSelectedBridgeCache() {
    return sdk.getSelectedBridge().getResourceCache();
  }
}