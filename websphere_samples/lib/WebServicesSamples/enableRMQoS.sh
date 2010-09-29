#!/bin/sh
# This program may be used, executed, copied, modified and distributed
# without royalty for the purpose of developing, using, marketing, or distributing.

SAMP_DIR=`dirname $0`
cd "${SAMP_DIR}"
whatos=`uname -s`
# Detremine if operating system implementation is z/os
if [ "${whatos}" = "OS/390" ] 
then
export WAS_POLICY="WSReliableMessaging persistent"
else 
export WAS_POLICY="WSReliableMessaging default"
fi
export ZIPFILE=
. ./enableQoS.sh ${SAVEDPARMS}
exit 0