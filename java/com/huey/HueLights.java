package com.huey;

import com.philips.lighting.model.PHLight;
import java.util.Map;

/**
 * Wrapper class for {@link com.philips.lighting.model.PHLight}.
 *
 * TODO: This object may be short-lived if the cache updates? Need to verify.
 */
public final class HueLights {

  // Map of light identifiers to the light object.
  private final Map<String, PHLight> lights;
  private final String groupName;

  // Don't allow other people to create this class.
  HueLights(Map<String, PHLight> lights) {
    this(lights, null);
  }

  // Don't allow other people to create this class.
  HueLights(Map<String, PHLight> lights, String groupName) {
    this.lights = lights;
    this.groupName = groupName;
  }

  /** Returns whether or not this group of lights is "all" the lights */
  public boolean isAllLights() {
    return groupName == null;
  }

}