cd java
javac -classpath ../lib/LeapJava.jar com/leaplight/**/*.java
java -classpath ".;../lib/LeapJava.jar" -Djava.library.path="../lib/x64" com.leaplight.LeapLight