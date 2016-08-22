package com.huemotion;

import com.huemotion.listener.MainListener;
import com.leapmotion.leap.Controller;
import com.huey.HueController;
import java.io.IOException;

public final class HueMotionMain {

  private static final String APP_NAME = "HueMotion";

  public static void main(String[] args) {

    HueController hueController = new HueController(APP_NAME);
    hueController.awaitReady();

    // HueLights lights = null;
    // try {
    //   lights = hueController.getHueLightsByGroup("Bedroom");
    // } catch (Exception e) {
    //   // TODO: Remove this!! It's only for debugging.
    //   throw new RuntimeException(e);
    // }
    // System.out.println("Found: " + Integer.toString(lights.size()) + " lights in the group");
    // lights.setBrightness(99999);

    MainListener listener = new MainListener(hueController);

    Controller controller = new Controller();
    controller.addListener(listener);

    System.out.println("Press Enter to quit...");
    try {
      System.in.read();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}