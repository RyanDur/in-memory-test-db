#!/usr/bin/env bash

set -e -x

export TERM=${TERM:-dumb}

pushd post-build-pu-codebase/
  ./gradlew clean build &&
popd

mv post-build-pu-codebase/p-and-u-api/build/libs/p-and-u-api-*.jar pu-jars/
