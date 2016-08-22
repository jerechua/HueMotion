package com.huey;

import com.google.common.collect.ImmutableMap;
import com.philips.lighting.model.PHGroup;
import com.philips.lighting.model.PHLight;
import com.philips.lighting.model.PHBridge;
import java.util.Map;

/**
 * Wrapper class for {@link com.philips.lighting.model.PHLight}.
 *
 * TODO: This object may be short-lived if the cache updates? Need to verify.
 */
public final class HueLights {

  // Map of light ID to the light object.
  private final ImmutableMap<String, PHLight> lights;
  private final PHGroup group;
  private final PHBridge bridge;

  // Don't allow other people to create this class.
  HueLights(PHBridge bridge, ImmutableMap<String, PHLight> lights) {
    this(bridge, lights, null);
  }

  // Don't allow other people to create this class.
  HueLights(PHBridge bridge, ImmutableMap<String, PHLight> lights, PHGroup group) {
    this.bridge = bridge;
    this.lights = lights;
    this.group = group;
  }

  /** Returns whether or not this group of lights is "all" the lights */
  public boolean isAllLights() {
    return group == null;
  }

  /** Returns the number of lights there are in the group */
  public int size() {
    return lights.size();
  }

  public void setRGB() {
    System.out.println();

  }
}