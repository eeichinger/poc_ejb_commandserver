#!/bin/sh
# This program may be used, executed, copied, modified and distributed
# without royalty for the purpose of developing, using, marketing, or distributing.

SAMP_DIR=`dirname $0`
cd "${SAMP_DIR}"
export WAS_POLICY=SecureConversation 
export ZIPFILE=SC_applications.zip
. ./enableQoS.sh ${SAVEDPARMS}
exit 0
