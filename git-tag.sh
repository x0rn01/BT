#!/bin/bash
git add gradle.properties --quiet
git commit -m "$1" --quiet
# git push -> won't work because of authentication
git rev-parse HEAD