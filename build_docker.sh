#!/usr/bin/env bash

./gradlew build -x test && docker build . -t leti/monitoring
