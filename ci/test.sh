#!/usr/bin/env bash

set -e -x

pushd in-memory-test-db
  ./gradlew clean test
popd