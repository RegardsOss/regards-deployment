#!/bin/bash -e
# LICENSE_PLACEHOLDER

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
    ;;
  stop)
    su "${EXEC_REGARDS_USER}" -s /bin/bash -c "\"${ROOT_DIR}\"/bin/stop_microservice.sh -t \"${MICROSERVICE_TYPE}\""
    ;;
  *)
    usage ${PROCESSUS_NAME}
    ;;
  esac

exit 0
