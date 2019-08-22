#!/bin/bash
revisioncount=`git rev-list --all --count`
projectversion=`git describe --tags --long`
cleanversion=${projectversion%%-*}

echo "$cleanversion.$revisioncount"