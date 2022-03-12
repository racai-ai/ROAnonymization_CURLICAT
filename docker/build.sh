#!/bin/sh

docker build --tag curlicat-anon-ro .


docker rmi -f $(docker images -q --filter label=stage=intermediate)
