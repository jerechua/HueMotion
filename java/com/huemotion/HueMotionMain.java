package com.huemotion;

import com.huey.HueController;
import com.huemotion.listener.MainListener;
import com.leapmotion.leap.Controller;
import java.io.IOException;

public final class HueMotionMain {

  private static final String APP_NAME = "HueMotion";

  public static void main(String[] args) {
    System.out.println("Press Enter to quit...");

    HueController hueController = new HueController(APP_NAME);
    hueController.getAllLights();

    MainListener listener = new MainListener();

    Controller leapController = new Controller();
    leapController.addListener(listener);

    try {
      System.in.read();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}