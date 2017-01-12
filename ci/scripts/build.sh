#!/usr/bin/env bash

set -e -x

export TERM=${TERM:-dumb}

pushd pu-git/
  ./gradlew clean build &&
popd

mv pu-git/p-and-u-api/build/libs/p-and-u-api-*.jar pu-jars/
