#!/bin/bash
#export JAVA_HOME="$CATALINA_BASE/jdk/Contents/Home"  - For MACOSX
#export JAVA_HOME="$CATALINA_BASE/jdk"                - For Linux

export CATALINA_OPTS="$CATALINA_OPTS -Xms1024m"
export CATALINA_OPTS="$CATALINA_OPTS -Xmx2048m"

#export JAVA_OPTS="-Xdebug -Xrunjdwp:transport=dt_socket,address=7001,server=y,suspend=n"