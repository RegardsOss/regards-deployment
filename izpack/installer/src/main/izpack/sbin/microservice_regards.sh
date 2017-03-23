#!/bin/bash -e
# LICENSE_PLACEHOLDER

# usage ################################################################
function usage
{
  typeset -r USAGE_PGR_NAME="$1"
  printf >&2 "Usage : ${USAGE_PGR_NAME} <start|stop|status>\n"

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

while getopts "h" opt
do
  case ${opt} in
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
    service_type_list="conf registry admin gateway dam catalog access frontend"
    ;;
  stop)
    service_type_list="frontend access catalog dam gateway admin registry conf"
    ;;
  *)
    usage ${PROCESSUS_NAME}
    ;;
esac

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

exit 0
