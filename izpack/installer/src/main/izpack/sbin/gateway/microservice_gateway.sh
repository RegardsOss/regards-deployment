#!/bin/bash -e
# LICENSE_PLACEHOLDER

typeset -r ROOT_DIR="$(readlink -e "$(dirname $0)/..")"

# Restrict PATH
export PATH=/bin:/usr/bin:/sbin/:/usr/sbin

exec "${ROOT_DIR}"/sbin/microservice.sh -t gateway "$@"

exit 0
