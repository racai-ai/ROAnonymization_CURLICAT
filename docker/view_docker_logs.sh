#!/bin/sh

docker logs curlicat-anon-ro-running

docker exec -it curlicat-anon-ro-running cat /var/log/apache2/error.log

