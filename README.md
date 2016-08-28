HueMotion
=========

HueMotion is a weekend hack project that allows you to open and close your hand and control your
[Philips Hue](http://www2.meethue.com/en-us/) lights using a
[Leap Motion](https://www.leapmotion.com/) device.

For now, opening and closing your hand is the only gesture supported. More to come, hopefully.

![](images/sample.gif)

Dependencies
------------
These are all the dependencies the project needs, and must be placed in the `lib`, minus Java.

- Java JDK8
- [LeapMotion v2 SDK](https://developer.leapmotion.com/v2)
- [Philips Hue SDK (1.11.2)](http://www.developers.meethue.com/documentation/java-multi-platform-and-android-sdk)
  (NOTE: This is currently harded coded in `HueController.java`)
- [Google Guava core libraries (release 19)](https://github.com/google/guava/wiki/Release19)
- [gson 2.7](http://repo1.maven.org/maven2/com/google/code/gson/gson/2.7/gson-2.7.jar)

To run
------

> NOTE: This was developed on windows :( Script might need to be changed to
> support other OS, in particular `run_java.sh` might need `s/:/;`.

`sh run_java.sh`

Future work
-----------

- Maven
- Make it run on raspi!
- Tests.. dunno how to do this yet.
- "Logging" (`System.out.println` :) is ugly
- Daemonize the Leap Motion process so it still works from the background.
- Moar gestures/better gesture mechanism.
- Move Huey to its own repo
- Multiple bridge support
- TensorFlow to learn gestures? Is this even possible?
- Otherthingsicantthinkofrightnow=
