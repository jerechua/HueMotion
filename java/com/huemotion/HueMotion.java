package com.huemotion;

import com.huey.HueController;
import com.huemotion.listener.MainListener;
import com.leapmotion.leap.Controller;
import com.philips.lighting.model.PHLight;

final class HueMotion {

  private static final String APP_NAME = "HueMotion";

  HueMotion() {
    HueController hueController = new HueController(APP_NAME);
    hueController.getAllLights();

    MainListener listener = new MainListener();

    Controller leapController = new Controller();
    leapController.addListener(listener);
  }
}