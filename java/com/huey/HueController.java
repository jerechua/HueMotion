package com.huey;

import com.philips.lighting.hue.sdk.PHHueSDK;
import com.philips.lighting.model.PHBridge;
import com.philips.lighting.model.PHBridgeResourcesCache;
import com.philips.lighting.model.PHLight;
import java.util.List;

public final class HueController {

  private static final int CONNECT_WAIT_INTERVAL = 100;
  private static final String COMPATIBLE_SDK_VERSION = "1.11.2";

  private final String appName;
  private final PHHueSDK sdk;
  private final HueListener listener;

  public HueController(String appName) {
    this.appName = appName;
    // TODO: For next version, support multiple bridges. This probably means that this code will
    // need to be moved to a "connect()" function.
    this.sdk = PHHueSDK.getInstance();
    this.listener = new HueListener(sdk);
    setupSDK(appName);
  }

  private PHHueSDK setupSDK(String appName) {
    if (!sdk.getSDKVersion().equals(COMPATIBLE_SDK_VERSION)) {
      System.out.println("Expected SDK version to be: " + COMPATIBLE_SDK_VERSION);
      System.exit(1);
    }

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

  public PHBridgeResourcesCache getCache() {
    return listener.getCache();
  }

  public List<PHLight> getAllLights() {
    return getCache().getAllLights();
  }
}