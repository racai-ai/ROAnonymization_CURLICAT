#!/bin/sh

cd /anon/ner/ner/PharmaCoNER-Tagger/src

python3 ner_server_nolegal.py  \
    --parameters_filepath=/anon/no_legal_CoRoLa+MARCELL_1_1/parameters.ini \
    --pretrained_model_folder=/anon/no_legal_CoRoLa+MARCELL_1_1/ \
    --use_pretrained_model=True \
    --train_model=False

