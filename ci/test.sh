#!/usr/bin/env bash

set -e -x

pushd pu-git
  gradle clean test
popd