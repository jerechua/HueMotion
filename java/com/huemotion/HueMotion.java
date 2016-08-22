package com.huemotion;

import com.huemotion.listener.MainListener;
import com.huey.HueController;
import com.huey.HueLights;
import com.leapmotion.leap.Controller;

final class HueMotion {

  private static final String APP_NAME = "HueMotion";

  HueMotion() {
    HueController hueController = new HueController(APP_NAME);

    HueLights lights = null;
    try {
      lights = hueController.getHueLightsByGroup("Bedroom");
    } catch (Exception e) {
      // TODO: Remove this!! It's only for debugging.
      throw new RuntimeException(e);
    }
    System.out.println("Found: " + Integer.toString(lights.size()) + " lights in the group");
    lights.setBrightness(99999);

    MainListener listener = new MainListener();

    // Controller leapController = new Controller();
    // leapController.addListener(listener);
  }
}