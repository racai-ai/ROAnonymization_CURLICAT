The anonymization docker can be built with build.sh.

If started with start.sh, it will listen on port 8002.

Example API call (also in test.sh):
```
curl -X 'POST' \
  'http://127.0.0.1:8002/anonymize/index.php' \
  -H 'accept: application/json' \
  -H 'Content-Type: application/json' \
  -d '{
  "text": "Maria a fost în excursie la Constanța.",
  "format": "text"
    }'
```

Example successful API reply:
```
{"orignal_text":"Maria a fost \u00een excursie la Constan\u021ba.","anonymized_text":"Andree\u0219anu a fost \u00een excursie la Ple\u0219oi.","format":"text"}
```

Example error API reply:
```
{"status":"ERROR","message":"Wrong input format; missing text field"}
```

