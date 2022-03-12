FROM ubuntu:18.04 as intermediate
LABEL stage=intermediate
ARG DEBIAN_FRONTEND=noninteractive
RUN apt-get update && \
    apt-get install -y git wget curl make automake autoconf gcc g++ flex bison zip unzip gzip

RUN mkdir anon

# Anonymizer
RUN wget http://relate.racai.ro/resources/ner/jdk1.8.0_191.tar.bz2 -O /anon/jdk1.8.0_191.tar.bz2 && tar -C /anon -jxf /anon/jdk1.8.0_191.tar.bz2 && rm -f /anon/jdk1.8.0_191.tar.bz2
RUN git clone https://github.com/racai-ai/ROAnonymization_CURLICAT.git /anon/Anonymizer

# NER
RUN git clone https://github.com/racai-ai/LegalNER.git /anon/ner
RUN wget https://relate.racai.ro/resources/legalnero/legalnero_per_loc_org_time_gazetteer.zip -O /anon/legalnero_per_loc_org_time_gazetteer.zip && cd /anon && unzip legalnero_per_loc_org_time_gazetteer.zip && rm -f legalnero_per_loc_org_time_gazetteer.zip
RUN mkdir anon/data && mkdir anon/data/gazetteers && mkdir anon/data/affixes
RUN wget https://relate.racai.ro/resources/legalnero/legalnero_all.gaz.gz -O /anon/data/gazetteers/legalnero_all.gaz.gz && gunzip /anon/data/gazetteers/legalnero_all.gaz.gz
RUN wget https://relate.racai.ro/resources/legalnero/legalnero_affixes.tsv.gz -O /anon/data/affixes/legalnero_affixes.tsv.gz && gunzip /anon/data/affixes/legalnero_affixes.tsv.gz
RUN mkdir -p /anon/ner/ner/data_per_loc_org_time/deploy && touch /anon/ner/ner/data_per_loc_org_time/deploy/test.txt
RUN mkdir -p /anon/ner/ner/wordembeddings
RUN wget https://relate.racai.ro/resources/legalnero/combined.vec.gz -O /anon/ner/ner/wordembeddings/combined.vec.gz && gunzip /anon/ner/ner/wordembeddings/combined.vec.gz


# Final image
FROM ubuntu:18.04
ARG DEBIAN_FRONTEND=noninteractive
RUN apt-get update && apt-get install -y cron zip unzip apache2 php7.2 libapache2-mod-php7.2 curl php7.2-mbstring php7.2-curl locales
RUN locale-gen en_US.UTF-8
RUN mkdir -p /var/www/site && a2enmod php7.2 && a2enmod rewrite
COPY --from=intermediate /anon/ /anon/
COPY anonymize/ /var/www/site/anonymize/
COPY docker/start_ner.sh /anon/
COPY docker/requirements.txt /anon/

# Python
RUN apt-get update && apt-get install -y libdb5.3 libdb5.3++ python3.7 python3-pip
RUN pip3 install --upgrade pip
RUN pip3 install Cython
RUN pip3 install numpy
RUN pip3 install matplotlib
RUN pip3 install scipy
RUN pip3 install -r /anon/requirements.txt
RUN  export LC_ALL=C.UTF-8 ; export LANG=C.UTF-8 ; python3 -m spacy download ro_core_news_sm
RUN  export LC_ALL=C.UTF-8 ; export LANG=C.UTF-8 ; python3 -m spacy download ro_core_news_lg

RUN sed -i "s/error_reporting = .*$/error_reporting = E_ERROR | E_WARNING | E_PARSE/" /etc/php/7.2/apache2/php.ini

ENV APACHE_RUN_USER www-data
ENV APACHE_RUN_GROUP www-data
ENV APACHE_LOG_DIR /var/log/apache2
ENV APACHE_LOCK_DIR /var/lock/apache2
ENV APACHE_PID_FILE /var/run/apache2.pid

ENV LC_ALL=en_US.UTF-8
ENV LANG=en_US.UTF-8
ENV LANGUAGE=en_US:en

ADD docker/apache-config.conf /etc/apache2/sites-enabled/000-default.conf
COPY docker/entrypoint.sh /
RUN chmod a+rx /entrypoint.sh
#COPY docker/tasks.cron /etc/cron.d/
#RUN chmod 0644 /etc/cron.d/tasks.cron
#RUN crontab /etc/cron.d/tasks.cron
#RUN touch /var/log/cron.log

CMD ["/entrypoint.sh"]
