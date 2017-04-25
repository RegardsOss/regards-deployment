#!/bin/bash -e
# LICENSE_PLACEHOLDER

# usage ################################################################
function usage
{
  typeset -r USAGE_PGR_NAME="$1"
  printf >&2 "Usage : ${USAGE_PGR_NAME} [-n <microservice id>] -t <microservice type>\n"
  printf >&2 "\t -n <microservice id> (optional): microservice id in the configfile\n"
  printf >&2 "\t -t <microservice type> : type of the microservices\n"

  exit 1
}

# end ##################################################################
function end
{
  cd "${DIR}"
}

# main #################################################################
typeset -r DIR="$(pwd)"

typeset -r PROCESSUS_NAME=$(basename $0)
typeset -r ROOT_DIR="$(readlink -e "$(dirname $0)/..")"

# Restrict PATH
export PATH=/bin:/usr/bin:"${ROOT_DIR}"/bin

while getopts "hn:t:" opt
do
  case ${opt} in
  t)
    typeset -r MICROSERVICE_TYPE="${OPTARG}"
    ;;
  n)
    typeset -ri MICROSERVICE_ID="${OPTARG}"
    start_only_one=true
    ;;
  \?|h)
    usage ${PROCESSUS_NAME}
    ;;
  esac
done

shift $((${OPTIND} - 1))

trap end EXIT

# Check arguments
if [ -z "${MICROSERVICE_TYPE}" ]
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

typeset microservices_infos
microservices_infos=$(get_microservice_info "${ROOT_DIR}" "${MICROSERVICE_TYPE}" "${MICROSERVICE_ID}")

cd "${ROOT_DIR}"
typeset pid pid_file lib_exec_java
lib_exec_java=$(get_lib_exec_java "${ROOT_DIR}/bootstrap-${MICROSERVICE_TYPE}")

printf "${microservices_infos}\n" | \
{
  all_instances_running=0
  while read line
  do
    typeset -A microservices_infos_t
    microservices_infos_t[id]=$(extract_field "${line}" "id")

    pid_file="$(get_pid_file_name "${ROOT_DIR}" "${MICROSERVICE_TYPE}" "${microservices_infos_t[id]}")"

    # Check if microservice is already started
    if ! is_microservice_running "${pid_file}" "${lib_exec_java}" "${MICROSERVICE_TYPE}" "${microservices_infos_t[id]}"
    then
      printf >&2 "Microservice \"${MICROSERVICE_TYPE}\" type id \"${microservices_infos_t[id]}\" is not running.\n"
      let all_instances_running=-1
    fi
  done
  exit ${all_instances_running}
} # encapsulation of the while loop in "{}" allows to perfom exit after while has finished with value of all_instances_running

exit 0
