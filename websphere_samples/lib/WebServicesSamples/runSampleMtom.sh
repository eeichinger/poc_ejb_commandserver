#!/bin/sh
# This program may be used, executed, copied, modified and distributed
# without royalty for the purpose of developing, using, marketing, or distributing.

# Setup
SAMP_DIR=`pwd`
WAS_DIR=`dirname ${0}`/../../../
cd ${WAS_DIR}
REPLACE_WAS_HOME=`pwd`   
cd bin
. ./setupCmdLine.sh
cd "${SAMP_DIR}"                 

#Platform specific args...
PLATFORM=`/bin/uname`
case $PLATFORM in
  OS/390)
    JAVA_LIB_PATH=-Djava.library.path=$WAS_HOME/lib
esac

# Edit the lines below if your machine has products installed to different paths
JAVA_HOME=${WAS_HOME}/java
PATH=${JAVA_HOME}/bin:$PATH
CLASSPATH=${WAS_HOME}/runtimes/com.ibm.jaxws.thinclient_6.1.0.jar:${WAS_HOME}/samples/lib/WebServicesSamples/WSSampleMTOMClient.jar

export WAS_HOME JAVA_HOME CLASSPATH PATH

# Example to run without this file:
#   java -cp ${CLASSPATH} org.apache.axis2.jaxws.sample.mtom.SampleMTOMTests [options]


# 
java $JAVA_LIB_PATH -cp "${CLASSPATH}" org.apache.axis2.jaxws.sample.mtom.SampleMTOMTests "$@"
exit 0
