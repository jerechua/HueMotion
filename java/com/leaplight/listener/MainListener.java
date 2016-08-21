package com.leaplight.listener;

import com.leapmotion.leap.Bone;
import com.leapmotion.leap.Controller;
import com.leapmotion.leap.Frame;
import com.leapmotion.leap.Hand;
import com.leapmotion.leap.HandList;
import com.leapmotion.leap.Listener;

public final class MainListener extends Listener {

  private static final int FRAME_DELTA = 10;

  @Override public void onConnect(Controller controller) {
    System.out.println("Connected to controller");
    for(Bone.Type boneType : Bone.Type.values()) {
        System.out.println(boneType);
      }
  }

  @Override public void onFrame(Controller controller) {
    Frame previous = controller.frame(FRAME_DELTA);
    if (!previous.isValid()) {
      System.out.println("invalid previous frame");
      return;
    }
    Frame current = controller.frame();

    HandList currentHands = current.hands();
    HandList previousHands = previous.hands();

    if (currentHands.isEmpty() || previousHands.isEmpty()) {
      // This or previous frame doesn't have any hands.
      return;
    }

    float delta = previousHands.frontmost().sphereRadius() - currentHands.frontmost().sphereRadius();
    System.out.println(delta);
  }
}