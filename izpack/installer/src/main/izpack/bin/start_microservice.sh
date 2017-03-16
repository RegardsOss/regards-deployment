#!/bin/bash -e
# LICENSE_PLACEHOLDER

# usage ################################################################
function usage
{
  typeset -r USAGE_PGR_NAME="$1"
  printf >&2 "Usage : ${USAGE_PGR_NAME} -n <microservice id> -t <microservice type>\n"
  printf >&2 "\t -n <microservice id> : microservice id in the configfile\n"
  printf >&2 "\t -t <microservice type> : used to dertermine configfile\n"

  exit 1
}

# extract_file #########################################################
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

# main #################################################################

# end ##################################################################
function end
{
  cd "${DIR}"
}

# main #################################################################
typeset -r DIR="$(pwd)"

typeset -r PROCESSUS_NAME=$(basename $0)
typeset -r DIR_RACINE="$(readlink -e "$(dirname $0)/..")"

# Restrict PATH
export PATH=/bin:/usr/bin:"${DIR_RACINE}"/bin

while getopts "hn:t:" opt
do
  case ${opt} in
  t)
    typeset -r MICROSERVICE_TYPE="${OPTARG}"
    ;;
  n)
    typeset -ri MICROSERVICE_ID="${OPTARG}"
    ;;
  \?|h)
    usage ${PROCESSUS_NAME}
    ;;
  esac
done

shift $((${OPTIND} - 1))

trap end EXIT

# Check arguments
if [ -z "${MICROSERVICE_TYPE}" -o -z "${MICROSERVICE_ID}" ]
then
  printf >&2 "ERROR : Some mandatory arguments are missing.\n\n"
  usage ${PROCESSUS_NAME}
fi

export CLASSPATH="${DIR_RACINE}"/lib/regards-izpack-utils.jar
typeset microservices_infos
microservices_infos=$(get_microservice_info.groovy -n "${MICROSERVICE_ID}" -t "${MICROSERVICE_TYPE}" -i "${DIR_RACINE}")
if [ "${microservices_infos}" = "null" ]
then
  printf >&2  "ERROR : Id \"${MICROSERVICE_ID}\" doesn't exist for this microservice type \"${MICROSERVICE_TYPE}\"\n"
  exit 1
fi

typeset -A microservices_infos_t
microservices_infos_t[id]=$(extract_field "${microservices_infos}" "id")
microservices_infos_t[host]=$(extract_field "${microservices_infos}" "host")
microservices_infos_t[port]=$(extract_field "${microservices_infos}" "port")

cd "${DIR_RACINE}"
exec java -jar -Dserver.address="${microservices_infos_t[host]}" -Dserver.port="${microservices_infos_t[port]}" bootstrap-${MICROSERVICE_TYPE}.jar

exit 0

