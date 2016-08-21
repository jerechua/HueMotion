cd java
javac \
  -classpath "../lib/LeapJava.jar;../lib/huelocalsdk.jar;../lib/huesdkresources.jar" \
  -sourcepath . \
  "com/huemotion/HueMotionMain.java" && \
java \
  -classpath ".;../lib/LeapJava.jar;../lib/huelocalsdk.jar;../lib/huesdkresources.jar" \
  -Djava.library.path="../lib/x64" \
  "com.huemotion.HueMotionMain"