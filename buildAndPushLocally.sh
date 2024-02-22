#!/bin/bash

# build
mvn clean verify -DskipTests -T 16

# build and push digiwf-engine-community image
docker build -t lmoesle/digiwf-engine-community:latest digiwf-engine/digiwf-engine-service
docker push lmoesle/digiwf-engine-community:latest
