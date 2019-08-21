#!/bin/bash
git add gradle.properties
git commit -m "$1"
# git push -> won't work because of authentication
git config credential.helper store
git push
git rev-parse HEAD