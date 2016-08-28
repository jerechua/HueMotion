cd java
javac \
  -classpath "../lib/LeapJava.jar;../lib/huelocalsdk.jar;../lib/huesdkresources.jar;../lib/guava-19.0.jar;../lib/gson-2.7.jar" \
  -sourcepath . \
  "com/jerechua/huemotion/HueMotionMain.java" && \
java \
  -classpath ".;../lib/LeapJava.jar;../lib/huelocalsdk.jar;../lib/huesdkresources.jar;../lib/guava-19.0.jar;../lib/gson-2.7.jar" \
  -Djava.library.path="../lib/x64" \
  "com.jerechua.huemotion.HueMotionMain"