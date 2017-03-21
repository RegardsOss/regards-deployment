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

typeset -r GLOBAL_REGARDS_GROUP="regards"
typeset -r ADMIN_REGARDS_GROUP="rsadmin"
typeset -r EXEC_REGARDS_GROUP="rsexec"
typeset -r RUNTIME_REGARDS_GROUP="rsrun"

# Two type of users must existed
# * Admin owned GLOBAL_REGARDS_GROUP, ADMIN_REGARDS_GROUP, RUNTIME_REGARDS_GROUP use to administer REGARDS
# * Exec owned GLOBAL_REGARDS_GROUP, EXEC_REGARDS_GROUP, RUNTIME_REGARDS_GROUP use to run REGARDS services
#
# GLOBAL_REGARDS_GROUP group is used to filter the first directory and ensure that user can access to the regards subtree.
# RUNTIME_REGARDS_GROUP is used to shared access between admin and exec users.

if [ -z "${ROOT_DIR_INSTALL}" -o -z "${ROOT_DIR}" ]
then
  printf >&2 "ERROR : Venv ROOT_DIR_INSTALL or ROOT_DIR can't be empty.\n"
  exit 1
fi

# Root install dir
chown :${GLOBAL_REGARDS_GROUP} "${ROOT_DIR_INSTALL}"
chmod 0750 "${ROOT_DIR_INSTALL}"

# Root install files
find "${ROOT_DIR_INSTALL}"/* -prune -type f -exec chown :${GLOBAL_REGARDS_GROUP} {} \;
find "${ROOT_DIR_INSTALL}"/* -prune -type f -exec chmod 0640 {} \;
chown :${ADMIN_REGARDS_GROUP} "${ROOT_DIR_INSTALL}"/.installationinformation
chmod 0640 "${ROOT_DIR_INSTALL}"/.installationinformation

# Root uninstall dir only acceded by admin
chown -R :${ADMIN_REGARDS_GROUP} "${ROOT_DIR_INSTALL}"/Uninstaller
chmod 0750 "${ROOT_DIR_INSTALL}"/Uninstaller

# Root uninstall file only acceded by admin
chmod 0640 "${ROOT_DIR_INSTALL}"/Uninstaller/*

# Root dir acceded by admin and exec user
chown :${RUNTIME_REGARDS_GROUP} "${ROOT_DIR}"
chmod 0750 "${ROOT_DIR}"

# Services files read for execution by exec user
chown :${EXEC_REGARDS_GROUP} "${ROOT_DIR}/"{*.war,*.jar}
chmod 0640 "${ROOT_DIR}/"{*.war,*.jar}

# Dirs shared throw rw by admin and exec users
chown -R :${RUNTIME_REGARDS_GROUP} "${ROOT_DIR}"/{run,logs}
chmod 2770 "${ROOT_DIR}"/{run,logs}

# Files shared throw rw by admin and exec users
find "${ROOT_DIR}"/{run,logs} -type f -exec chmod 0660 {} \;

# Dirs shared by admin (rw) and exec (ro) users
chown -R :${ADMIN_REGARDS_GROUP} "${ROOT_DIR}"/config
chmod 0775 "${ROOT_DIR}"/config

# Files shared by admin (rw) and exec (ro) users
chmod 0775 "${ROOT_DIR}"/config/*

# Dirs acceded throw rx by exec users
chown -R :${RUNTIME_REGARDS_GROUP} "${ROOT_DIR}"/bin
chmod 0750 "${ROOT_DIR}"/bin

# Files acceded throw rx by exec users
chmod 0750 "${ROOT_DIR}"/bin/*

# Dirs acceded throw ro by exec users
chown -R :${RUNTIME_REGARDS_GROUP} "${ROOT_DIR}"/lib
chmod 0750 "${ROOT_DIR}"/lib

# Files acceded throw ro by exec users
chmod 0640 "${ROOT_DIR}"/lib/*

exit 0
