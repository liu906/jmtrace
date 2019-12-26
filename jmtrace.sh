#!/bin/bash
#author: Liu Xutong

echo "$0"; #jmtrace
echo "$1"; #-jar
echo "$2"; #something.jar
echo "$3"; #"hello world"

echo "${2%.*}"; #something


#java -javaagent:agent.jar -classpath /home/lau/jmtrace/target/classes:/home/lau/jmtrace/javassist-3.12.1.GA.jar application.TestInstrumentation
