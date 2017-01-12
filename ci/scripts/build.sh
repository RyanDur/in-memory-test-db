#!/usr/bin/env bash

set -e -x

export TERM=${TERM:-dumb}

pushd pu-git
  ./gradlew clean build &&
popd