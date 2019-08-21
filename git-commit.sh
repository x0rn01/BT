#!/bin/bash
git add gradle.properties
git commit -m "$1"
# git push -> won't work because of authentication
git rev-parse HEAD