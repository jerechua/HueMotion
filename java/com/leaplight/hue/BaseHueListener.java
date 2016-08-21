package com.leaplight.hue;

import com.philips.lighting.hue.sdk.PHAccessPoint;
import com.philips.lighting.hue.sdk.PHMessageType;
import com.philips.lighting.hue.sdk.PHSDKListener;
import com.philips.lighting.model.PHBridge;
import java.util.List;

abstract class BaseHueListener implements PHSDKListener {

  @Override public void onAccessPointsFound(List<PHAccessPoint> accessPoints) {}

  @Override public void onAuthenticationRequired(PHAccessPoint accessPoint) {}

  @Override public void onBridgeConnected(PHBridge bridge, String username) {}

  @Override public void onCacheUpdated(List cacheNotificationsList, PHBridge bridge) {}

  @Override public void onConnectionLost(PHAccessPoint accessPoint) {}

  @Override public void onConnectionResumed(PHBridge bridge) {}

  @Override public void onError(int code, final String message) {}

  @Override public void onParsingErrors(List parsingErrorsList) {}
}