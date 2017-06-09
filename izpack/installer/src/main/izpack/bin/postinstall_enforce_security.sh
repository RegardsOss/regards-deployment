#!/bin/bash -e
# LICENSE_PLACEHOLDER

# usage ################################################################
function usage
{
  typeset -r USAGE_PGR_NAME="$1"
  printf >&2 "Usage : ${USAGE_PGR_NAME}\n"

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
typeset -r ROOT_DIR_INSTALL="$(readlink -e "${ROOT_DIR}"/..)"

# Restrict PATH
export PATH=/bin:/usr/bin:"${ROOT_DIR}"/bin

while getopts "h" opt
do
  case ${opt} in
  \?|h)
    usage ${PROCESSUS_NAME}
    ;;
  esac
done

shift $((${OPTIND} - 1))

trap end EXIT

if [ -z "${ROOT_DIR_INSTALL}" -o -z "${ROOT_DIR}" ]
then
  printf >&2 "ERROR : Venv ROOT_DIR_INSTALL or ROOT_DIR can't be empty.\n"
  exit 1
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
typeset -r GLOBAL_REGARDS_GROUP=$(read_config "${CONFIGURATION_FILE}" "regards.microservices.regards.group")
typeset -r EXEC_REGARDS_GROUP=$(read_config "${CONFIGURATION_FILE}" "regards.microservices.exec.group")
typeset -r ADMIN_REGARDS_GROUP=$(read_config "${CONFIGURATION_FILE}" "regards.microservices.admin.group")
typeset -r RUNTIME_REGARDS_GROUP=$(read_config "${CONFIGURATION_FILE}" "regards.microservices.runtime.group")

# Two types of users must exist
# * Admin owned GLOBAL_REGARDS_GROUP, ADMIN_REGARDS_GROUP, RUNTIME_REGARDS_GROUP use to administer REGARDS
# * Exec owned GLOBAL_REGARDS_GROUP, EXEC_REGARDS_GROUP, RUNTIME_REGARDS_GROUP use to run REGARDS services
#
# GLOBAL_REGARDS_GROUP group is used to filter the first directory and ensure that user can access to the regards subtree.
# RUNTIME_REGARDS_GROUP is used to share access between admin and exec users.


# Root install dir
chown :${GLOBAL_REGARDS_GROUP} "${ROOT_DIR_INSTALL}"
chmod 0750 "${ROOT_DIR_INSTALL}"

# Root install files
find "${ROOT_DIR_INSTALL}"/* -prune -type f -exec chown :${GLOBAL_REGARDS_GROUP} {} \;
find "${ROOT_DIR_INSTALL}"/* -prune -type f -exec chmod 0640 {} \;
chown :${ADMIN_REGARDS_GROUP} "${ROOT_DIR_INSTALL}"/.installationinformation
chmod 0640 "${ROOT_DIR_INSTALL}"/.installationinformation

# Root dir acceded by admin and exec user
chown :${RUNTIME_REGARDS_GROUP} "${ROOT_DIR}"
chmod 0750 "${ROOT_DIR}"

# Services files read for execution by exec user
if ls "${ROOT_DIR}/"*.war > /dev/null 2>&1
then
  chown :${EXEC_REGARDS_GROUP} "${ROOT_DIR}/"*.war
  chmod 0640 "${ROOT_DIR}/"*.war
fi
if ls "${ROOT_DIR}/"*.jar > /dev/null 2>&1
then
  chown :${EXEC_REGARDS_GROUP} "${ROOT_DIR}/"*.jar
  chmod 0640 "${ROOT_DIR}/"*.jar
fi

# Dirs shared by admin (rw) and exec (ro) users
chown -R :${ADMIN_REGARDS_GROUP} "${ROOT_DIR}"/config
find "${ROOT_DIR}"/config -type d -exec chmod 2775 {} \;

# Files shared by admin (rw) and exec (ro) users
find "${ROOT_DIR}"/config -type f -exec chmod 0664 {} \;

# Dirs accessed through rx by exec users
chown -R :${RUNTIME_REGARDS_GROUP} "${ROOT_DIR}"/bin
chmod 0750 "${ROOT_DIR}"/bin

# Files accessed through rx by exec users
if ls "${ROOT_DIR}"/bin/* > /dev/null 2>&1
then
  chmod 0750 "${ROOT_DIR}"/bin/*
fi

# Dirs accessed through rx by root users
chmod 0700 "${ROOT_DIR}"/sbin

# Files accessed through rx by exec users
if ls "${ROOT_DIR}"/sbin/* > /dev/null 2>&1
then
  chmod 0700 "${ROOT_DIR}"/sbin/*
fi

# Dirs accessed through ro by exec users
chown -R :${RUNTIME_REGARDS_GROUP} "${ROOT_DIR}"/lib
chmod 0750 "${ROOT_DIR}"/lib

# Files accessed through ro by exec users
if ls "${ROOT_DIR}"/lib/* > /dev/null 2>&1
then
  chmod 0640 "${ROOT_DIR}"/lib/*
fi

# www directory and subdirs
if ls "${ROOT_DIR}"/www > /dev/null 2>&1
then
  chown -R :${ADMIN_REGARDS_GROUP} "${ROOT_DIR}"/www
  find "${ROOT_DIR}"/www -type d -exec chmod 2775 {} \;
  find "${ROOT_DIR}"/www -type f -exec chmod 0664 {} \;
fi

# Dirs shared through rw by admin and exec users
chown -R :${RUNTIME_REGARDS_GROUP} "${ROOT_DIR}"/{run,logs}
chmod 2770 "${ROOT_DIR}"/{run,logs}

# Files shared through rw by admin and exec users
find "${ROOT_DIR}"/{run,logs} -type f -exec chmod 0660 {} \;

exit 0
