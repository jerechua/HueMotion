package com.leaplight;

import com.leaplight.listeners.MainListener;
import com.leapmotion.leap.Controller;
import java.io.IOException;

public final class LeapLight {

  public static void main(String[] args) {
    System.out.println("Press Enter to quit...");

    MainListener listener = new MainListener();

    Controller controller = new Controller();
    controller.addListener(listener);

    try {
      System.in.read();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}