#!/bin/bash -e
# Copyright 2017-2019 CNES - CENTRE NATIONAL d'ETUDES SPATIALES
#
# This file is part of REGARDS.
#
# REGARDS is free software: you can redistribute it and/or modify
# it under the terms of the GNU General Public License as published by
# the Free Software Foundation, either version 3 of the License, or
# (at your option) any later version.
#
# REGARDS is distributed in the hope that it will be useful,
# but WITHOUT ANY WARRANTY; without even the implied warranty of
# MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
# GNU General Public License for more details.
#
# You should have received a copy of the GNU General Public License
# along with REGARDS. If not, see <http://www.gnu.org/licenses/>.

# usage ################################################################
function usage
{
  typeset -r USAGE_PGR_NAME="$1"
  printf >&2 "Usage : ${USAGE_PGR_NAME} -t <microservice type> <start|stop|status>\n"
  printf >&2 "\t -t <microservice type> : type of the microservices\n"

  exit 1
}

# end ##################################################################
function end
{
  cd "${DIR}"
}

# main #################################################################

if [ $(id -u) -ne 0 ]
then
  printf >&2 "ERROR : Only root can execute this script.\n"
  exit 1
fi

typeset -r DIR="$(pwd)"

typeset -r PROCESSUS_NAME=$(basename $0)
typeset -r ROOT_DIR="$(readlink -e "$(dirname $0)/..")"

# Restrict PATH
export PATH=/bin:/usr/bin:/sbin/:/usr/sbin

while getopts "ht:" opt
do
  case ${opt} in
  t)
    typeset -r MICROSERVICE_TYPE="${OPTARG}"
    ;;
  \?|h)
    usage ${PROCESSUS_NAME}
    ;;
  esac
done

shift $((${OPTIND} - 1))
typeset -r COMMAND="$1"

trap end EXIT

# Check arguments
if [ -z "${MICROSERVICE_TYPE}" -o -z "${COMMAND}" ]
then
  printf >&2 "ERROR : Some mandatory arguments are missing.\n\n"
  usage ${PROCESSUS_NAME}
fi

# Load libraries
typeset FUNCTIONS_FILE="${ROOT_DIR}"/lib/functions.sh
if [ ! -r "${FUNCTIONS_FILE}" ]
then
  printf >&2 "ERROR : Library file \"${FUNCTIONS_FILE}\" must be readable.\n"
  exit 1
fi
. "${FUNCTIONS_FILE}"

typeset -r CONFIGURATION_FILE="${ROOT_DIR}"/config/system.properties
typeset -r EXEC_REGARDS_USER=$(read_config "${CONFIGURATION_FILE}" "regards.microservices.exec.user")

cd "${ROOT_DIR}"
case  "${COMMAND}" in
  start)
    su "${EXEC_REGARDS_USER}" -s /bin/bash -c "\"${ROOT_DIR}\"/bin/start_microservice.sh -t \"${MICROSERVICE_TYPE}\""
    ;;
  status)
    su "${EXEC_REGARDS_USER}" -s /bin/bash -c "\"${ROOT_DIR}\"/bin/status_microservice.sh -t \"${MICROSERVICE_TYPE}\""
    exit $? 
    ;;
  stop)
    su "${EXEC_REGARDS_USER}" -s /bin/bash -c "\"${ROOT_DIR}\"/bin/stop_microservice.sh -t \"${MICROSERVICE_TYPE}\""
    ;;
  *)
    usage ${PROCESSUS_NAME}
    ;;
esac

exit 0
