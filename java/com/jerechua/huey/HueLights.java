package com.jerechua.huey;

import com.google.common.collect.ImmutableMap;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import com.philips.lighting.model.PHBridge;
import com.philips.lighting.model.PHGroup;
import com.philips.lighting.model.PHLight;
import com.philips.lighting.model.PHLightState;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.Map;

/**
 * Wrapper class for {@link com.philips.lighting.model.PHLight}.
 *
 * TODO: This object may be short-lived if the cache updates? Need to verify.
 */
public final class HueLights {
  // Unless it's all the lights on the bridge, the number of lights required in the group before the
  // Groups API is used.
  private static final int MAX_LIGHTS_BEFORE_USING_GROUPS_API = 15;

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

  /**
  * Does the actual logic to set all the light states.
  *
  * <p><ul>From http://www.developers.meethue.com/things-you-need-know:
  * <li>Updating light attributes is more efficient via light api than it is with group API for
  * ~dozen lights.</li>
  * <li>Don’t always send ‘ON’</li>
  */
  private void setLightState(final PHLightState lightState) {

    if (group != null && lights.size() > MAX_LIGHTS_BEFORE_USING_GROUPS_API) {
      // If we're making it 0, but the light is still on, the light won't actually turn off, so we
      // have to manually turn it on/off.
      lightState.setOn(lightState.getBrightness() > 0);
      bridge.setLightStateForGroup(group.getIdentifier(), lightState);
      return;
    }

    // TODO: Should this be static final?
    ListeningExecutorService executor = MoreExecutors.listeningDecorator(
        Executors.newFixedThreadPool(MAX_LIGHTS_BEFORE_USING_GROUPS_API));

    for (final PHLight light : lights.values()) {
      executor.submit(new Callable<Void>() {
        @Override public Void call() {
          // Only update light state if it's not already that light state.
          if (light.getLastKnownLightState().isOn() ^ (lightState.getBrightness() > 0)) {
            lightState.setOn(lightState.getBrightness() > 0);
          }
          bridge.updateLightState(light, lightState);
          return null;
        }
      });
    }

    executor.shutdown();
  }
}