#!/bin/bash -e
# Copyright 2017-2019 CNES - CENTRE NATIONAL d'ETUDES SPATIALES
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
# ROOT_DIR reflects the directory in which the application is installed
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
# These three are only usable in case:
# rs-config is being installed, if it is not, value is empty so make sure to test when using it
typeset -r WORKSPACE=$(read_config "${CONFIGURATION_FILE}" "regards.microservices.workspace")
# rs-storage is being installed
typeset -r STORAGE_CACHE=$(read_config "${CONFIGURATION_FILE}" "regards.storage.cache")
# rs-dam is being installed
typeset -r DAM_LOCAL_STORAGE=$(read_config "${CONFIGURATION_FILE}" "regards.dam.local.storage")

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

# plugins dir read for execution by exec user
chown -R :${EXEC_REGARDS_GROUP} "${ROOT_DIR}/"plugins
find "${ROOT_DIR}"/plugins -type d -exec chmod 0750 {} \;
find "${ROOT_DIR}"/plugins -type f -exec chmod 0640 {} \;

# Dirs shared by admin (rw) and exec (ro) users(files are owned by rsins)
chown -R :${ADMIN_REGARDS_GROUP} "${ROOT_DIR}"/config
find "${ROOT_DIR}"/config -type d -exec chmod 2775 {} \;

# Files shared by admin (rw) and exec (ro) users(files are owned by rsins)
find "${ROOT_DIR}"/config -type f -exec chmod 0664 {} \;

# Dirs accessed through rx by exec users
chown :${RUNTIME_REGARDS_GROUP} "${ROOT_DIR}"/bin
chmod -R 0750 "${ROOT_DIR}"/bin

# Files accessed through rx by exec users
chown :${EXEC_REGARDS_GROUP} "${ROOT_DIR}"/bin/start_microservice.sh
chmod 0650 "${ROOT_DIR}"/bin/start_microservice.sh
chown :${EXEC_REGARDS_GROUP} "${ROOT_DIR}"/bin/wait-for-it.sh
chmod 0650 "${ROOT_DIR}"/bin/wait-for-it.sh
chown :${EXEC_REGARDS_GROUP} "${ROOT_DIR}"/bin/get_microservice_info.groovy
chmod 0650 "${ROOT_DIR}"/bin/get_microservice_info.groovy
chown :${EXEC_REGARDS_GROUP} "${ROOT_DIR}"/bin/read_component_wait_rule_list.groovy
chmod 0650 "${ROOT_DIR}"/bin/read_component_wait_rule_list.groovy
chown :${EXEC_REGARDS_GROUP} "${ROOT_DIR}"/bin/stop_microservice.sh
chmod 0650 "${ROOT_DIR}"/bin/stop_microservice.sh

# Files accessed through rx by admin users
chown :${RUNTIME_REGARDS_GROUP} "${ROOT_DIR}"/bin/status_microservice.sh
chmod 0750 "${ROOT_DIR}"/bin/status_microservice.sh

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
# Lets handle dynamic locations
if [ -n "${WORKSPACE}" ]
then
  chown -R :${RUNTIME_REGARDS_GROUP} ${WORKSPACE}
  chmod 2770 ${WORKSPACE}
fi
if [ -n "${STORAGE_CACHE}" ]
then
  chown -R :${RUNTIME_REGARDS_GROUP} ${STORAGE_CACHE}
  chmod 2770 ${STORAGE_CACHE}
fi
if [ -n "${DAM_LOCAL_STORAGE}" ]
then
  chown -R :${RUNTIME_REGARDS_GROUP} ${DAM_LOCAL_STORAGE}
  chmod 2770 ${DAM_LOCAL_STORAGE}
fi

# Files shared through rw by admin and exec users
find "${ROOT_DIR}"/{run,logs} -type f -exec chmod 0660 {} \;

exit 0
