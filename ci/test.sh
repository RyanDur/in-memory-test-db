#!/usr/bin/env bash

set -e -x

pushd pu-git
  ./gradlew clean test
popd