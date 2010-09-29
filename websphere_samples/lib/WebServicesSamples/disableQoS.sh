#!/bin/sh
# This program may be used, executed, copied, modified and distributed
# without royalty for the purpose of developing, using, marketing, or distributing.
# required env variables - WAS_POLICY

# Setup environment
SAMP_DIR=`dirname $0`
cd "${SAMP_DIR}"
. ./setupSamp.sh $*
if [ $? -eq 1 ]  
then       
  return 1   
fi         

# If no policy arg, delete all
if [ "${WAS_POLICY}" = "" ]
then
  export WAS_POLICY=*
fi

# Remove the Policy attachment
if [[ ! -d "${CONFIG_ROOT}/cells/${WAS_CELL}/applications/WSSampleClientSei.ear" 
   || ! -d "${CONFIG_ROOT}/cells/${WAS_CELL}/applications/WSSampleServicesSei.ear" ]]
then
  echo ERROR: Both SEI Sample Application EAR files are not installed
  echo to ${USER_INSTALL_ROOT} ${WAS_CELL} ${WAS_NODE}
  return 1
else
  "${WAS_HOME}/bin/wsadmin.sh" ${PROFOPT} -lang jython -f "${OPER_DIR}/scripts/bindings.py" remove "${WAS_POLICY}" ${WAS_NODE} ${WAS_CELL} ${WAS_SERVER} ${SAVEDPARMS}
  if [ $? -ne 0 ]
  then
    return 1
  fi
fi

return 0

