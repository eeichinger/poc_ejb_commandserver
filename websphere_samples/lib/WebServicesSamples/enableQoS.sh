#!/bin/sh
# This program may be used, executed, copied, modified and distributed
# without royalty for the purpose of developing, using, marketing, or distributing.
# required env variables - WAS_POLICY ZIPFILE

# Set up environment
SAMP_DIR=`dirname $0`
cd "${SAMP_DIR}"
. ./setupSamp.sh $*
if [ $? -eq 1 ]  
then       
  return 1   
fi 

# Test if Both Sei Apps are installed
if [[ ! -d "${CONFIG_ROOT}/cells/${WAS_CELL}/applications/WSSampleClientSei.ear" 
   || ! -d "${CONFIG_ROOT}/cells/${WAS_CELL}/applications/WSSampleServicesSei.ear" ]]
then
  echo ""
  echo ERROR: Both Sei Sample Application EAR files are not installed
  echo to ${USER_INSTALL_ROOT} ${WAS_CELL} ${WAS_NODE}
  echo Please install both WSSampleClientSei.ear and WSSampleServicesSei.ear
  return 1
fi

# Extract the files from the zip
if [ "${ZIPFILE}" != "" ] 
then
  cd "${CONFIG_ROOT}/cells/${WAS_CELL}"
  jar -xf "${OPER_DIR}/lib/${ZIPFILE}"
fi

# Call bindings.py
"${WAS_HOME}/bin/wsadmin.sh" ${PROFOPT} -lang jython -f "${OPER_DIR}/scripts/bindings.py" attach "${WAS_POLICY}" ${WAS_NODE} ${WAS_CELL} ${WAS_SERVER} ${SAVEDPARMS}
if [ $? -ne 0 ]
then
  return 1
fi

return 0
