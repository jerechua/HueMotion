package com.leaplight.listeners;

import com.leapmotion.leap.Listener;
import com.leapmotion.leap.Controller;

public final class MainListener extends Listener {

  @Override public void onConnect(Controller controller) {
    System.out.println("Connected to controller");
  }

  @Override public void onFrame(Controller controller) {
    System.out.println("Frame available");
  }
}