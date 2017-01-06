#!/usr/bin/env bash

set -e -x

pushd pu-git
  gradle wrapper
  ./gradlew clean test
popd