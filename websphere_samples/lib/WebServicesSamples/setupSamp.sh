#!/bin/sh
# This program may be used, executed, copied, modified and distributed
# without royalty for the purpose of developing, using, marketing, or distributing.

ERROR=
PROFOFT=
PROGNAME=$0

# default server name if it is not passed as a command parameter
export WAS_SERVER=server1

printopts()
{
echo ""
echo "Usage:"
echo "  ${PROGNAME} [profile] [cell] [node] [server] [-user user -password password]"
echo "optional parameters:"
echo "  profile is your WebSphere Application Server profile"
echo "  cell is the cell name for the profile"
echo "  node is the node name for the server node"
echo "  server is the server name"
echo "Example:"
echo "  ${PROGNAME} AppSrv01 LINK-T42Node01Cell LINK-T42Node01 server1"
}

# Get Parms And Save Them
if [ "$1" != "" ]; then
  if [ "$1" != "-user" ]; then 
    WAS_PROFILE=$1
    PROFOPT="-profileName $1"
    shift
  fi
fi

if [ "$1" != "" ]; then
  if [ "$1" != "-user" ]; then
    SAVED_CELL=$1
    export WAS_CELL=$1
    shift
  fi
fi

if [ "$1" != "" ]; then
  if [ "$1" != "-user" ]; then
    SAVED_NODE=$1
    export WAS_NODE=$1
    shift
  fi
fi

if [ "$1" != "" ]; then
  if [ "$1" != "-user" ]; then
    SAVED_SERVER=$1
    export WAS_SERVER=$1
    shift
  fi
fi
SAVEDPARMS=$*

# Call WAS Command-Line Setup
SAMP_DIR=`pwd`
cd ../../..
REPLACE_WAS_HOME=`pwd`   
cd bin
. ./setupCmdLine.sh ${PROFOPT} $*
cd "${SAMP_DIR}"                 

# Restore Saved Parms If Any
if [ "$SAVED_CELL" != "" ]; then
  export WAS_CELL=$SAVED_CELL
fi
if [ "$SAVED_NODE" != "" ]; then
  export WAS_NODE=$SAVED_NODE
fi
if [ "$SAVED_SERVER" != "" ]; then
  export WAS_SERVER=$SAVED_SERVER
fi

# Edit the lines below if your machine has products installed to different paths
JAVA_HOME=${WAS_HOME}/java
PATH=${JAVA_HOME}/bin:$PATH
OPER_DIR=${WAS_HOME}/samples/lib/WebServicesSamples

# Test Profile name
if [[ ! -d "${USER_INSTALL_ROOT}" ]] 
then
  echo ERROR: Profile ${WAS_PROFILE} does not exist
  echo Valid profiles:
  cd ../../../bin                  
  ./manageprofiles.sh -listProfiles
  cd "${SAMP_DIR}"                 
  printopts
  return 1
fi

# Test Cell name
if [[ ! -d "${CONFIG_ROOT}/cells/${WAS_CELL}" ]] 
then
  echo ERROR: Cell ${WAS_CELL} does not exist
  echo Valid cells on server ${WAS_PROFILE}:
  ls "${CONFIG_ROOT}/cells"
  printopts
  return 1
fi

# Test Node name
if [[ ! -d "${CONFIG_ROOT}/cells/${WAS_CELL}/nodes/${WAS_NODE}" ]] 
then
  echo ERROR: Node ${WAS_NODE} does not exist
  echo Valid nodes on cell ${WAS_CELL}:
  ls "${CONFIG_ROOT}/cells/${WAS_CELL}/nodes"
  printopts
  return 1
fi


