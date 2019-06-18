#!/bin/bash

LIST=( 'EXTREMELY_LOW' 'LOW' 'NORMAL' 'HIGH' 'EXTREMELY_HIGH' )
#LIST=( 'EXTREMELY_LOW' 'EXTREMELY_HIGH' )

PROTOCOLS=(  'CS'      'BLACKBOARD'    'HSS'   'Shadow')
USE_SPECIALS=('True'    'False'         'True'  'True')
#PROTOCOLS=(   'CS'   'FP'    'HSS'   'Shadow')
#USE_SPECIALS=('True' 'False' 'True'  'True')

for i in $(seq 0 5); do
    PROTOCOL=${PROTOCOLS[$i]}
    USE_SPECIAL=${USE_SPECIALS[$i]}
    LOG="$PROTOCOL.log"
    dt=$(date '+%d/%m/%Y %H:%M:%S');
    echo "$PROTOCOL $dt"
    for sim in ${PROTOCOL}/*; do
        for MIGRATION in ${LIST[@]}; do
            for MESSAGE in ${LIST[@]}; do
                for CPU in ${LIST[@]}; do
                    AGENT="$sim/agents.json"
                    GRAPH="$sim/graph.json"
                    HOST="$sim/hosts.json"
                    SPECIAL="$sim/special_hosts.json"
                    no_nodes="$(cut -d'_' -f2 <<<$sim)"
                    no_agents="$(cut -d'_' -f3 <<<$sim)"
                    PARAMS="-g $GRAPH -a $AGENT -h $HOST -migration $MIGRATION -message $MESSAGE -cpu $CPU "

                    if [ "$USE_SPECIAL" == "True" ]; then
                        PARAMS="$PARAMS -s $SPECIAL"
                    fi

                    echo -n "$no_nodes $no_agents $MIGRATION $MESSAGE $CPU " >> $LOG
                    java -Xmx6024m -jar Simulator.jar $PARAMS >> $LOG
                done
            done
        done
    done
done