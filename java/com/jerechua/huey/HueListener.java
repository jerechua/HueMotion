package com.jerechua.huey;

import com.philips.lighting.hue.sdk.PHAccessPoint;
import com.philips.lighting.hue.sdk.PHHueSDK;
import com.philips.lighting.hue.sdk.PHMessageType;
import com.philips.lighting.hue.sdk.PHMessageType;
import com.philips.lighting.hue.sdk.PHSDKListener;
import com.philips.lighting.model.PHBridge;
import com.philips.lighting.model.PHBridgeResourcesCache;
import java.util.List;

final class HueListener extends BaseHueListener {

  private final PHHueSDK sdk;
  private final HueProperties properties;

  // Synchronized. Checks whether or not the selected bridge is connected.
  private Boolean isBridgeConnected = false;

  HueListener(HueProperties properties, PHHueSDK sdk) {
    this.properties = properties;
    this.sdk = sdk;
  }

  @Override public void onAccessPointsFound(List<PHAccessPoint> accessPoints) {

    if (accessPoints.size() <= 0) {
      // TODO: Consider re-searching access points here.
      System.out.println("No access points found");
      return;
    }
    sdk.getAccessPointsFound().clear();
    sdk.getAccessPointsFound().addAll(accessPoints);

    // TODO: Connect to multiple access points.
    connect(accessPoints.get(0));
  }

  @Override public void onAuthenticationRequired(PHAccessPoint accessPoint) {
    System.out.println("Authentication is required! Please press the bridge button.");
    sdk.startPushlinkAuthentication(accessPoint);
  }

  @Override public void onBridgeConnected(PHBridge bridge, String username) {
    System.out.println("Successfully connected to bridge.");
    properties.updateUserId(username);
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
    if (properties.hasUserId()) {
      accessPoint.setUsername(properties.getUserId());
    }
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