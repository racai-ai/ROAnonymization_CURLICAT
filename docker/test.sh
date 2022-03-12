#!/bin/sh


curl -X 'POST' \
  'http://127.0.0.1:8002/anonymize/index.php' \
  -H 'accept: application/json' \
  -H 'Content-Type: application/json' \
  -d '{
  "text": "Maria a fost în excursie la Constanța.",
  "format": "text"
    }'
