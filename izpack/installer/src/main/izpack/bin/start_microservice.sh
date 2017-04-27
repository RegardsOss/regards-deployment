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

# Read configurations
typeset microservices_infos
microservices_infos=$(get_microservice_info "${ROOT_DIR}" "${MICROSERVICE_TYPE}" "${MICROSERVICE_ID}")

# Read wait rules


cd "${ROOT_DIR}"
typeset log_file pid pid_file lib_exec_java
lib_exec_java=$(get_lib_exec_java "${ROOT_DIR}/bootstrap-${MICROSERVICE_TYPE}")

printf "${microservices_infos}\n" | while read line
do
  typeset -A microservices_infos_t
  microservices_infos_t[id]=$(extract_field "${line}" "id")
  microservices_infos_t[host]=$(extract_field "${line}" "host")
  microservices_infos_t[port]=$(extract_field "${line}" "port")

  pid_file="$(get_pid_file_name "${ROOT_DIR}" "${MICROSERVICE_TYPE}" "${microservices_infos_t[id]}")"

  # Check if microservice is already started
  if ! is_microservice_running "${pid_file}" "${lib_exec_java}" "${MICROSERVICE_TYPE}" "${microservices_infos_t[id]}"
  then
    printf >&2 "Starting ${MICROSERVICE_TYPE} type on \"${microservices_infos_t[host]}:${microservices_infos_t[port]}\" ...\n"
    log_file="${ROOT_DIR}"/logs/"${MICROSERVICE_TYPE}-id${microservices_infos_t[id]}".log
    touch ${log_file}
    chmod g+r ${log_file}

    java -jar -Dserver.address="${microservices_infos_t[host]}" -Dserver.port="${microservices_infos_t[port]}" ${lib_exec_java} > "${log_file}" 2>&1 &
    pid=$!

    echo "${pid}" > "${pid_file}"
  fi
done

exit 0
