#!/bin/sh

#cron &

cd /anon/Anonymizer ; /anon/jdk1.8.0_191/bin/java -cp deps/json-20200518.jar:RoAnonymization.jar Server 8202 model http://127.0.0.1:5104/api/v1.0/ner true &

cd /anon ; ./start_ner.sh &

/usr/sbin/apachectl -D FOREGROUND
