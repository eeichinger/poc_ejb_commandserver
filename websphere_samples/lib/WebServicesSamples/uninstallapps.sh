#!/bin/sh
# This program may be used, executed, copied, modified and distributed
# without royalty for the purpose of developing, using, marketing, or distributing.

SAMP_DIR=`dirname $0`
cd "${SAMP_DIR}"
. ./setupSamp.sh $*
if [ $? -eq 1 ]  
then       
  exit 1   
else         
  "${WAS_HOME}/bin/wsadmin.sh" ${PROFOPT} -lang jython -f "${OPER_DIR}/scripts/installapps.py" uninstall ${WAS_NODE} ${WAS_CELL} ${WAS_SERVER} ${SAVEDPARMS}
fi
exit 0
