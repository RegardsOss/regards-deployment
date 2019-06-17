#!bin/false
# Copyright 2017-2018 CNES - CENTRE NATIONAL d'ETUDES SPATIALES
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

# extract_field ########################################################
function extract_field
{
  typeset -r FCT_FORMATTED_STRING="$1"
  typeset -r FCT_FIELD_ID="$2"

  typeset fct_result
  typeset fct_transformed_string

  # Change [] by {} to avoid regex problems
  fct_transformed_string=$(printf "${FCT_FORMATTED_STRING}" | tr '[' '{' | tr ']' '}')

  if ! fct_result=$(expr "${fct_transformed_string}" : ".*[[:blank:]|{]${FCT_FIELD_ID}=\([^,|}]*\)")
  then
    # Warning, if expr result="0" the return code is 0 even if they have no error
    if [ "${fct_result}" != "0" ]
    then
      printf >&2 "ERROR : Pattern \"${FCT_FIELD_ID}\" not found in \"${FCT_FORMATTED_STRING}\".\n"
      exit 1
    fi
  fi

  printf "${fct_result}"
}

# get_pid_file_name ####################################################
function get_pid_file_name
{
  typeset -r FCT_ROOT_DIR="$1"
  typeset -r FCT_MICROSERVICE_TYPE="$2"
  typeset -r FCT_MICROSERVICE_ID="$3"

  printf "${FCT_ROOT_DIR}/run/${FCT_MICROSERVICE_TYPE}-id${FCT_MICROSERVICE_ID}.pid"
}

# get_microservice_info ################################################
function get_microservice_info
{
  typeset -r FCT_ROOT_DIR="$1"
  typeset -r FCT_MICROSERVICE_TYPE="$2"
  typeset -r FCT_MICROSERVICE_ID="$3"

  typeset fct_microservices_infos

export CLASSPATH="${FCT_ROOT_DIR}"/lib/custom-components.jar
  if [ -n "${FCT_MICROSERVICE_ID}" ]
  then
    fct_microservices_infos="$(get_microservice_info.groovy -n "${FCT_MICROSERVICE_ID}" -t "${FCT_MICROSERVICE_TYPE}" -i "${FCT_ROOT_DIR}")"
    if [ "${fct_microservices_infos}" = "null" ]
    then
      printf >&2  "ERROR : Id \"${FCT_MICROSERVICE_ID}\" doesn't exist for this microservice type \"${FCT_MICROSERVICE_TYPE}\"\n"
      exit 1
    fi
  else
    fct_microservices_infos="$(get_microservice_info.groovy -t "${FCT_MICROSERVICE_TYPE}" -i "${FCT_ROOT_DIR}")"
  fi

  printf "${fct_microservices_infos}"
}

# is_microservice_running ##############################################
function is_microservice_running
{
  typeset -r FCT_PID_FILE="$1"
  typeset -r FCT_LIB_EXEC_JAVA="$2"
  typeset -r FCT_MICROSERVICE_TYPE="$3"
  typeset -r FCT_MICROSERVICE_ID="$4"

  typeset fct_pid

  if [ -e "${FCT_PID_FILE}" ]
  then
    fct_pid=$(cat "${FCT_PID_FILE}")
    if ps --no-headers -p ${fct_pid} -f | grep "${FCT_LIB_EXEC_JAVA}" > /dev/null
    then
      printf >&2 "Microservice \"${FCT_MICROSERVICE_TYPE}\" type id \"${FCT_MICROSERVICE_ID}\" is running with pid ${fct_pid}.\n"
      return 0
    else
      # Destroy bad pid file
      rm "${FCT_PID_FILE}"
    fi
  fi
  return 1
}

# get_lib_exec_java ####################################################
function get_lib_exec_java
{
  typeset -r FCT_LIB_EXEC_JAVA_PATH="$1"

  typeset fct_lib_exec_java
  if ! fct_lib_exec_java=$(ls "${FCT_LIB_EXEC_JAVA_PATH}"*.[wj]ar)
  then
    printf >&2  "ERROR : File starting by \"${FCT_LIB_EXEC_JAVA_PATH}\" must exist.\n"
    exit 1
  fi
  basename "${fct_lib_exec_java}"
}

# read_config ##########################################################
# in case the value has not been replaced, return empty string
function read_config
{
  typeset -r FCT_CONFIGURATION_FILE="$1"
  typeset -r FCT_PARAMETER="$2"

  # Variable
  typeset search_value search_line

  if [ ! -r "${FCT_CONFIGURATION_FILE}" ]
  then
   printf >&2  "ERROR : File \"${FCT_CONFIGURATION_FILE}\" doesn't exist or not readable.\n"
   exit 1
  fi

  # Extract parameter value from configuration file
  search_line=$(egrep "^[[:blank:]]*${FCT_PARAMETER}[[:blank:]]*=" "${FCT_CONFIGURATION_FILE}")
  if [ -z "${search_line}" ]
  then
    printf >&2  "ERROR : Mandatory property \"${FCT_PARAMETER}\" isn't defined in properties file \"${FCT_CONFIGURATION_FILE}\".\n"
    exit 1
  fi

  if ! search_value=$(expr "${search_line}" : ".*\=[[:blank:]]*\(.*\)")
  then
    printf >&2  "ERROR : Property line definition \"${search_line}\" is malformed.\n"
    exit 1
  fi
  if [ $(expr "${search_value}" : '${.*}') -ne 0 ]
  then
    search_value=''
  fi
  eval eval echo "${search_value}"
}

# read_component_wait_rule_list ################################################
function read_component_wait_rule_list
{
  typeset -r FCT_ROOT_DIR="$1"
  typeset -r FCT_MICROSERVICE_TYPE="$2"

  typeset fct_read_component_wait_rule_list

export CLASSPATH="${FCT_ROOT_DIR}"/lib/custom-components.jar
  fct_read_component_wait_rule_list="$(read_component_wait_rule_list.groovy -i "${FCT_ROOT_DIR}" -t "${FCT_MICROSERVICE_TYPE}")"

  printf "${fct_read_component_wait_rule_list}"
}

function wait_for_it
{
  typeset -r FCT_ROOT_DIR="$1"
  typeset -r PORT="$2"
  typeset -r TIMEOUT="$3"

  typeset fct_wait_for_it
  #fct_wait_for_it="$("${FCT_ROOT_DIR}"/lib/wait-for-it.sh "${HOST}":"${PORT}" -t "${TIMEOUT}")"
  #printf "${fct_wait_for_it}"
  #
  printf "/bin/sh ${FCT_ROOT_DIR}/lib/wait-for-it.sh ${HOST}:${PORT} -t ${TIMEOUT}"
}

#function
