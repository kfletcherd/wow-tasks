#!/bin/bash

#
# Starts the API
#

gitdir="$(git rev-parse --show-toplevel)"
thisdir="$(pwd)"

if [ $gitdir != $thisdir ]; then
	echo "Must be in project root dir"
	exit 1
fi

java -cp "lib/postgresql.jar:bin" api.API 9999

