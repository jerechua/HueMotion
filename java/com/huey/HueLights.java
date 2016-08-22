package com.huey;

import com.google.common.collect.ImmutableMap;
import com.philips.lighting.model.PHLight;
import java.util.Map;

/**
 * Wrapper class for {@link com.philips.lighting.model.PHLight}.
 *
 * TODO: This object may be short-lived if the cache updates? Need to verify.
 */
public final class HueLights {

  // Map of light ID to the light object.
  private final ImmutableMap<String, PHLight> lights;
  private final String groupName;

  // Don't allow other people to create this class.
  HueLights(ImmutableMap<String, PHLight> lights) {
    this(lights, null);
  }

  // Don't allow other people to create this class.
  HueLights(ImmutableMap<String, PHLight> lights, String groupName) {
    this.lights = lights;
    this.groupName = groupName;
  }

  /** Returns whether or not this group of lights is "all" the lights */
  public boolean isAllLights() {
    return groupName == null;
  }

  /** Returns all Hue lights that have color enabled */
  public HueLights getColorEnabledLights() {
    // TODO: Consider just making this static.

    ImmutableMap.Builder<String, PHLight> colorEnabledBuilder = ImmutableMap.builder();
    for (Map.Entry<String, PHLight> entry : lights.entrySet()) {
      if (entry.getValue().supportsColor()) {
        colorEnabledBuilder.put(entry.getKey(), entry.getValue());
      }
    }
    return new HueLights(colorEnabledBuilder.build(), groupName);
  }

  /** Returns the number of lights there are in the group */
  public int size() {
    return lights.size();
  }

  public void setRGB() {

  }

}