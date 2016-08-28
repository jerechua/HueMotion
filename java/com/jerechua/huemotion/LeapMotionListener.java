package com.jerechua.huemotion;

import com.google.common.util.concurrent.RateLimiter;
import com.jerechua.huey.HueController;
import com.jerechua.huey.HueLights;
import com.leapmotion.leap.Bone;
import com.leapmotion.leap.Controller;
import com.leapmotion.leap.Frame;
import com.leapmotion.leap.Hand;
import com.leapmotion.leap.HandList;
import com.leapmotion.leap.Listener;

public final class LeapMotionListener extends Listener {

  private static final int FRAME_DELTA = 10;
  private static final int HUE_UPDATES_PER_SECOND = 2;
  private static final RateLimiter HUE_RATE_LIMIT = RateLimiter.create(HUE_UPDATES_PER_SECOND);

  private final HueController hueController;

  private Frame lastFrame;

  public LeapMotionListener(HueController hueController) {
    this.hueController = hueController;
  }

  @Override public void onConnect(Controller controller) {
    System.out.println("Connected to controller");
    lastFrame = controller.frame();
  }

  @Override public void onFrame(Controller controller) {
    if (!HUE_RATE_LIMIT.tryAcquire()) {
      // Skip over frame call if we can't acquire a permit, so we don't overwhelm the Hue bridge.
      return;
    }

    Frame current = controller.frame();

    HandList currentHands = current.hands();
    HandList previousHands = lastFrame.hands();

    if (previousHands.isEmpty()) {
      lastFrame = current;
    }

    if (currentHands.isEmpty()) {
      // This or previous frame doesn't have any hands.
      return;
    }

    Hand hand = currentHands.frontmost();
    double delta = hand.sphereRadius() - previousHands.frontmost().sphereRadius();
    float maxSphereSize = 100;
    double brightness = (hand.sphereRadius() - hand.palmWidth()/1.75)/maxSphereSize*255;
    System.out.println(hand.sphereRadius());

    updateHueLights((int)brightness);

    // Update the last frame to be this frame.
    lastFrame = current;
  }

  private void updateHueLights(int delta) {
    HueLights lights = null;
    try {
      lights = hueController.getHueLightsByGroup("Bedroom");
    } catch (Exception e) {
      // TODO: Remove this!! It's only for debugging.
      throw new RuntimeException(e);
    }
    System.out.println("Setting brightness to: " + Integer.toString(delta));
    lights.setBrightness(delta);
  }

  @Override public void onDeviceChange(Controller arg0) {
    System.out.println("onDeviceChange");
  }

  @Override public void onDisconnect(Controller arg0) {
    System.out.println("onDisconnect");
  }

  @Override public void onExit(Controller arg0) {
    System.out.println("onExit");
  }

  @Override public void onFocusGained(Controller arg0) {
    System.out.println("onFocusGained");
  }

  @Override public void onFocusLost(Controller arg0) {
    System.out.println("onFocusLost");
  }

  @Override public void onImages(Controller arg0) {
    System.out.println("onimages");
  }

  @Override public void onInit(Controller arg0) {
    System.out.println("onInit");
  }

  @Override public void onServiceConnect(Controller arg0) {
    System.out.println("onServiceConnect");
  }

  @Override public void onServiceDisconnect(Controller arg0) {
    System.out.println("onServiceDisconnect");
  }

}