package com.jerechua.huey;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.philips.lighting.hue.sdk.PHHueSDK;
import com.philips.lighting.model.PHBridge;
import com.philips.lighting.model.PHBridgeResourcesCache;
import com.philips.lighting.model.PHGroup;
import com.philips.lighting.model.PHLight;
import java.util.Map;

public final class HueController {

  private static final int CONNECT_WAIT_INTERVAL = 100;
  // TODO: Use Maven?
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

  private void setupSDK(String appName) {
    if (!sdk.getSDKVersion().equals(COMPATIBLE_SDK_VERSION)) {
      System.out.println("Expected SDK version to be: " + COMPATIBLE_SDK_VERSION);
      // TODO: This should really just  be a warning log.
      System.exit(1);
    }

    sdk.setAppName(appName);
    sdk.getNotificationManager().registerSDKListener(listener);

    HueBridgeFinder.newFinder(sdk)
      .searchUPNP(true)
      .searchPortal(true)
      .find();
  }

  /**
   * The Hue controller logic is all async, call awaitReady() to wait for the controller to be
   * ready.
   */
  public void awaitReady() {
    while (true) {
      if (listener.isConnected()) {
        return;
      }
      System.out.println("Waiting for SDK to connect...");
      try {
        Thread.sleep(CONNECT_WAIT_INTERVAL);
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    }
  }

  /** Retrieves the cache from the bridge. */
  public PHBridgeResourcesCache getSelectedBridgeCache() {
    return listener.getSelectedBridgeCache();
  }

  /** Returns a controller for all lights in the cache. */
  public HueLights getAllHueLights() {
    return new HueLights(
        listener.getSelectedBridge(),
        ImmutableMap.copyOf(getSelectedBridgeCache().getLights()));
  }

  /** Returns the map of <Group name> -> PHGroup */
  public ImmutableMap<String, PHGroup> getGroupsByName() {

    ImmutableMap.Builder<String, PHGroup> groupMapBuilder = ImmutableMap.builder();
    for (PHGroup group : getSelectedBridgeCache().getAllGroups()) {
      groupMapBuilder.put(group.getName(), group);
    }
    return groupMapBuilder.build();
  }

  /** Returns all the lights in the cache with the group named groupName */
  public HueLights getHueLightsByGroup(String groupName)
      throws GroupNotFoundException, LightNotFoundException {

    ImmutableMap<String, PHGroup> allGroups = getGroupsByName();
    if (!allGroups.containsKey(groupName)) {
      throw new GroupNotFoundException();
    }

    Map<String, PHLight> allLights = getSelectedBridgeCache().getLights();
    ImmutableMap.Builder<String, PHLight> groupLightsBuilder = ImmutableMap.builder();

    for (String lightID : allGroups.get(groupName).getLightIdentifiers()) {
      if (!allLights.containsKey(lightID)) {
        throw new LightNotFoundException();
      }
      groupLightsBuilder.put(lightID, allLights.get(lightID));
    }

    return new HueLights(
        listener.getSelectedBridge(),
        groupLightsBuilder.build(),
        allGroups.get(groupName));
  }

  /** Returns the list of group names */
  public ImmutableSet<String> getGroupNames() {
    return getGroupsByName().keySet();
  }

  // Happens when the group does not exist.
  public final class GroupNotFoundException extends Exception {}

  // Happens when the light identifier is listed in the group's lights, but it's not an actual
  // light available.
  public final class LightNotFoundException extends Exception {}
}