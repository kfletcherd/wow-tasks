#!/bin/bash

#
# Compiles the desktop app
#

gitdir="$(git rev-parse --show-toplevel)"
thisdir="$(pwd)"

if [ $gitdir != $thisdir ]; then
	echo "Must be in project root dir"
	exit 1
fi

java.exe -cp bin app.DesktopApplication

