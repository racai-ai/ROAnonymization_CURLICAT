# ROAnonymization_CURLICAT
Romanian text anonymization (pseudonymization) from the CURLICAT project


# Usage

        Server PORT DBPATH NERURL USEUNK

                PORT=server port
                DBPATH=resource database path
                NERURL=url for NER; NER is disabled if this doesn't start with http
                USEUNK=true to use unknowns


Example:
java -cp deps/json-20200518.jar:RoAnonymization.jar Server 8202 model http://127.0.0.1:5104/api/v1.0/ner true
