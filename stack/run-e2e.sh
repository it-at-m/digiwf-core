#!/bin/bash

cd ..

echo "Building digiwf-apps"
cd digiwf-apps && npm run init && npm run build
cd ..

echo "Building services"
./mvnw clean verify -DskipTests -T8

echo "Build tasklist docker image"
docker build -t digiwf-tasklist-service:latest digiwf-task/digiwf-tasklist-service
echo "Build gateway docker image"
docker build -t digiwf-gateway-service:latest digiwf-gateway
echo "Build engine docker image"
docker build -t digiwf-engine:latest digiwf-engine/digiwf-engine-service
echo "Build connector docker image"
docker build -t digiwf-connector:latest digiwf-connector/digiwf-camunda-connector-service
echo "Build tasklist docker image"
docker build -t digiwf-tasklist:latest digiwf-apps/packages/apps/digiwf-tasklist
echo "Build cosys docker image"
docker build -t digiwf-cosys:latest digiwf-integrations/digiwf-cosys-integration/digiwf-cosys-integration-service
echo "Build s3 docker image"
docker build -t digiwf-s3:latest digiwf-integrations/digiwf-s3-integration/digiwf-s3-integration-service

echo "Starting services"
docker compose -f stack/docker-compose-e2e.yml up -d
