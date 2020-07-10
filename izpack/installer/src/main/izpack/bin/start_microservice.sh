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
echo "$@"
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

cd "${ROOT_DIR}"
typeset log_file pid pid_file lib_exec_java logback_file plugins_dir
lib_exec_java=$(get_lib_exec_java "${ROOT_DIR}/bootstrap-${MICROSERVICE_TYPE}")

printf "${microservices_infos}\n" | while read line
do
  typeset -A microservices_infos_t
  microservices_infos_t[id]=$(extract_field "${line}" "id")
  microservices_infos_t[host]=$(extract_field "${line}" "host")
  microservices_infos_t[port]=$(extract_field "${line}" "port")
  microservices_infos_t[xmx]=$(extract_field "${line}" "xmx")

  pid_file="$(get_pid_file_name "${ROOT_DIR}" "${MICROSERVICE_TYPE}" "${microservices_infos_t[id]}")"

  # Check if microservice is already started
  if ! is_microservice_running "${pid_file}" "${lib_exec_java}" "${MICROSERVICE_TYPE}" "${microservices_infos_t[id]}"
  then
	printf >&2 "\n"
    printf >&2 "Starting ${MICROSERVICE_TYPE} type on \"${microservices_infos_t[host]}:${microservices_infos_t[port]}\" ...\n"
    # Wait for other components
    typeset wait_rule_list
    typeset -A wait_rule_list_t
    wait_rule_list=$(read_component_wait_rule_list "${ROOT_DIR}" "${MICROSERVICE_TYPE}")
    printf "${wait_rule_list}\n" | while read line
    do
      if [ ! -z "${line}" ]
      then
        wait_rule_list_t[host]=$(extract_field "${line}" "host")
        wait_rule_list_t[port]=$(extract_field "${line}" "port")
        wait_rule_list_t[timeout]=$(extract_field "${line}" "timeout")
        "${ROOT_DIR}"/bin/wait-for-it.sh ${wait_rule_list_t[host]}:${wait_rule_list_t[port]} -t ${wait_rule_list_t[timeout]}
      fi
    done
    
    log_file="${ROOT_DIR}"/logs/"rs-${MICROSERVICE_TYPE}".log
    touch ${log_file}
    chmod g+r ${log_file}

    logback_file="${ROOT_DIR}"/config/logback/${MICROSERVICE_TYPE}/logback.xml
    plugins_dir="${ROOT_DIR}"/plugins/${MICROSERVICE_TYPE}
    microservice_conf_dir="${ROOT_DIR}"/config/regards/${MICROSERVICE_TYPE}
    
    java_memory_properties="-XX:+UseG1GC -Xms256m -Xmx${microservices_infos_t[xmx]} -XX:MinHeapFreeRatio=10 -XX:MaxHeapFreeRatio=70 -XX:CompressedClassSpaceSize=64m -XX:ReservedCodeCacheSize=64m -XX:MaxMetaspaceSize=256m"

    umask 0027
    if [ ${MICROSERVICE_TYPE} == "frontend" ]
    then
        java $java_memory_properties -Dserver.address="0.0.0.0" -Dserver.port="${microservices_infos_t[port]}" -jar ${lib_exec_java} --regards.frontend.www.path=./www >> "${log_file}" 2>&1 &
    else
        java $java_memory_properties -Dserver.address="0.0.0.0" -Dserver.port="${microservices_infos_t[port]}" -Dloader.path=${plugins_dir} -cp ${microservice_conf_dir} -Dlogging.config=${logback_file} -jar ${lib_exec_java}  >> "${log_file}" 2>&1 &
    fi
    pid=$!
    umask 0077
    echo "${pid}" > "${pid_file}"
  fi
done

exit 0
