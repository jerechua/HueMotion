package com.huey;

import com.google.common.collect.ImmutableMap;
import com.philips.lighting.model.PHBridge;
import com.philips.lighting.model.PHGroup;
import com.philips.lighting.model.PHLight;
import com.philips.lighting.model.PHLightState;
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

  /** Returns all Hue lights that have color enabled */
  public HueLights getColorEnabledLights() {
    // TODO: Consider just making this static.

    ImmutableMap.Builder<String, PHLight> colorEnabledBuilder = ImmutableMap.builder();
    for (Map.Entry<String, PHLight> entry : lights.entrySet()) {
      if (entry.getValue().supportsColor()) {
        colorEnabledBuilder.put(entry.getKey(), entry.getValue());
      }
    }
    // NOTE: This new HueLights has no group now, otherwise we would end up updating by
    // group, which will be wrong.
    return new HueLights(bridge, colorEnabledBuilder.build());
  }

  /** Returns the number of lights there are in the group */
  public int size() {
    return lights.size();
  }


  /**
   * Sets the brightness of the hue light.
   *
   * @param brightness is bounded between [0, 254] inclusive.
   */
  public void setBrightness(int brightness) {
    PHLightState lightState = new PHLightState();
    int boundedBrightness = Math.min(Math.max(brightness, 0), 254);
    lightState.setBrightness(boundedBrightness);
    setLightState(lightState);
  }

  /**
   * Returns the brightness of a light.
   */
  public int getBrightness() {
    // This is a pretty bad hack :(
    // Need a way to get brightness of all lights.
    PHLightState lightState = lights.values().asList().get(0).getLastKnownLightState();
    return lightState.getBrightness();
  }

  /** Does the actual logic to set all the light states. */
  private void setLightState(PHLightState lightState) {

    // If we're making it 0, but the light is still on, the light won't actually turn off, so we
    // have to manually turn it off.
    if (lightState.getBrightness() <= 0) {
      lightState.setOn(false);
    } else {
      lightState.setOn(true);
    }

    if (group != null) {
      bridge.setLightStateForGroup(group.getIdentifier(), lightState);
      return;
    }

    lightState.setTransitionTime(0);

    for (PHLight light : lights.values()) {
      bridge.updateLightState(light, lightState);
    }
  }
}