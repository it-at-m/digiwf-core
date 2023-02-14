#!/bin/bash

echo "Bump apps version to $1"
echo "Bump service version to $2"

cd ..

# apps
cd digiwf-apps
npx lerna version "$1" --ignore-changes --no-commit-hooks --no-git-tag-version
npm run init
cd ..

# services
mvn versions:set -DnewVersion="$2"
mvn versions:commit
