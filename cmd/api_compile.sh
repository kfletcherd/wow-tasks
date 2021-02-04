#!/bin/bash

#
# Compiles the API
#

gitdir="$(git rev-parse --show-toplevel)"
thisdir="$(pwd)"

if [ $gitdir != $thisdir ]; then
	echo "Must be in project root dir"
	exit 1
fi

cd src

javac -d ../bin api/API.java

