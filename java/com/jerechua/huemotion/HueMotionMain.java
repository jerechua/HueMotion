package com.jerechua.huemotion;

import com.leapmotion.leap.Controller;
import com.jerechua.huey.HueController;
import java.io.IOException;

public final class HueMotionMain {

  private static final String APP_NAME = "HueMotion";

  public static void main(String[] args) {

    HueController hueController = new HueController(APP_NAME);
    hueController.awaitReady();

    LeapMotionListener listener = new LeapMotionListener(hueController);

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