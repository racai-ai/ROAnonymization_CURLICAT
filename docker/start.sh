#!/bin/sh

docker run --name "curlicat-anon-ro-running" -d -p 8002:80 \
    curlicat-anon-ro

docker ps

