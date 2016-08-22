package com.huemotion;

import java.io.IOException;

public final class HueMotionMain {

  public static void main(String[] args) {

    new HueMotion();

    System.out.println("Press Enter to quit...");
    try {
      System.in.read();
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      System.exit(0);
    }
  }
}