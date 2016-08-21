package com.leaplight;

import com.leaplight.hue.HueController;
import com.leaplight.listener.MainListener;
import com.leapmotion.leap.Controller;
import java.io.IOException;

public final class LeapLight {

  private static final String APP_NAME = "LeapLight";

  public static void main(String[] args) {
    System.out.println("Press Enter to quit...");

    HueController hueController = new HueController(APP_NAME);

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