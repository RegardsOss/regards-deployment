#!/bin/bash -e
# Copyright 2017-2020 CNES - CENTRE NATIONAL d'ETUDES SPATIALES
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
if [ -z "${COMMAND}" ]
then
  printf >&2 "ERROR : Some mandatory arguments are missing.\n\n"
  usage ${PROCESSUS_NAME}
fi

typeset service_type_list
case "${COMMAND}" in
  start|status)
service_type_list="config registry gateway admin-instance admin authentication storage ingest dam catalog order dataprovider access-instance access-project frontend"
    ;;
  stop)
service_type_list="frontend access-project access-instance dataprovider ingest storage order catalog dam admin-instance admin authentication gateway registry config"
    ;;
  *)
    usage ${PROCESSUS_NAME}
    ;;
esac

# If the microservice type was passed as argument
if [ ! -z "${MICROSERVICE_TYPE}" ]
then
  if [ -e "${ROOT_DIR}/sbin/microservice_${MICROSERVICE_TYPE}.sh" ]
  then
    "${ROOT_DIR}"/sbin/microservice.sh -t "${MICROSERVICE_TYPE}" "$@"
    exit $?
  fi
# If no type was passed, perform the command on all components
else
  typeset service_type
  for service_type in ${service_type_list}
  do
    if [ -e "${ROOT_DIR}/sbin/microservice_${service_type}.sh" ]
    then
      "${ROOT_DIR}"/sbin/microservice.sh -t "${service_type}" "$@"
      # Wait service starting
      case "${COMMAND}" in
        start)
          sleep 5
          ;;
        *)
          ;;
      esac
    fi
  done
fi

exit 0
