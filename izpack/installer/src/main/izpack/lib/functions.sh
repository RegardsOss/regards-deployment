#!bin/false
# LICENSE_PLACEHOLDER

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
      printf >&2 "ERROR : Pattern \"${FCT_FIELD_ID}\" not founded in \"${FCT_FORMATTED_STRING}\".\n"
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

  export CLASSPATH="${FCT_ROOT_DIR}"/lib/regards-izpack-utils.jar
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
  typeset -r FCT_PROGRAM_NAME="$2"
  typeset -r FCT_MICROSERVICE_TYPE="$3"
  typeset -r FCT_MICROSERVICE_ID="$4"
  
  typeset fct_pid

  if [ -e "${FCT_PID_FILE}" ]
  then
    fct_pid=$(cat "${FCT_PID_FILE}")
    if ps --no-headers -p ${fct_pid} -f | grep "${FCT_PROGRAM_NAME}" > /dev/null
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
