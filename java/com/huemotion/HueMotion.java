package com.huemotion;

import com.huemotion.listener.MainListener;
import com.huey.HueController;
import com.leapmotion.leap.Controller;

final class HueMotion {

  private static final String APP_NAME = "HueMotion";

  HueMotion() {
    HueController hueController = new HueController(APP_NAME);
    hueController.getAllHueLights();

    MainListener listener = new MainListener();

    Controller leapController = new Controller();
    leapController.addListener(listener);
  }
}