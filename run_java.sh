cd java
javac \
  -classpath "../lib/LeapJava.jar;../lib/huelocalsdk.jar;../lib/huesdkresources.jar" \
  -sourcepath . \
  "com/leaplight/LeapLight.java" && \
java \
  -classpath ".;../lib/LeapJava.jar;../lib/huelocalsdk.jar;../lib/huesdkresources.jar" \
  -Djava.library.path="../lib/x64" \
  "com.leaplight.LeapLight"