package com.jerechua.huemotion;

import com.jerechua.huemotion.listener.MainListener;
import com.jerechua.huey.HueController;
import com.jerechua.huey.HueLights;
import com.leapmotion.leap.Controller;

final class HueMotion {

  private static final String APP_NAME = "HueMotion";
  // private final HueController hueController;
  private final Controller leapController;
  private final MainListener listener;

  HueMotion() {
    // hueController = new HueController(APP_NAME);
    // hueController.awaitReady();


    // HueLights lights = null;
    // try {
    //   lights = hueController.getHueLightsByGroup("Bedroom");
    // } catch (Exception e) {
    //   // TODO: Remove this!! It's only for debugging.
    //   throw new RuntimeException(e);
    // }
    // System.out.println("Found: " + Integer.toString(lights.size()) + " lights in the group");
    // lights.setBrightness(99999);

    System.out.println("Starting leap motion device");
    listener = new MainListener();
    leapController = new Controller();
    leapController.addListener(new MainListener());

  }
}