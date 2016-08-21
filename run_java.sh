cd java
javac -classpath ../lib/LeapJava.jar -sourcepath . com/leaplight/LeapLight.java && \
java -classpath ".;../lib/LeapJava.jar" -Djava.library.path="../lib/x64" com.leaplight.LeapLight