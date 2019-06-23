import networkx as nx
import random
import os
from shutil import copyfile

NODES_LIST = [10, 50, 100, 1000, 10, 50, 100]
AGENTS_LIST = [2, 5,  10,  100, 100, 200, 100]

for i in range(0, len(NODES_LIST)):
    no_nodes = NODES_LIST[i]
    no_agents = AGENTS_LIST[i]

    GRAPH_NAME = "complete_" + str(no_nodes) + "_" + str(no_agents)
    # ------------------- MEFS -------------------
    PROTOCOL = "MEFS"
    PROTOCOL_FOLDER = PROTOCOL + "/" + GRAPH_NAME
    try:
        os.makedirs(PROTOCOL_FOLDER)
    except OSError:
        print("Creation of the directory %s failed" % PROTOCOL_FOLDER)

    f = open(PROTOCOL_FOLDER + "/" + "agents.json", "w+")
    f.write("[\n")
    for id in range(0, no_agents):
        host_id = random.randint(0, no_nodes - 2)
        f.write(
            '{"id": %d,\n"hostId": %d,\n"protocol": "%s", "protocolArguments": {"homeServerHost": "%d", "maxSpeed": "10",\
            "ttl": "5", "timeToSync": "10"}}' % (id, host_id, PROTOCOL, no_nodes-1))
        if id != no_agents - 1:
            f.write(',\n')
    f.write("]\n")
    f.close()

    f = open(PROTOCOL_FOLDER + "/hosts.json", "w+")

    f.write("[\n")
    for id in range(0, no_nodes-1):
        f.write('{"id": %d,\n"protocol": "%s", "protocolArguments": {"homeServerHost": "%d"}}' % (id, PROTOCOL, no_nodes-1))
        if id != no_nodes - 2:
            f.write(',\n')
    f.write("]\n")
    f.close()

    f = open(PROTOCOL_FOLDER + "/special_hosts.json", "w+")

    f.write("[\n")
    f.write('{"id": %d,\n"protocol": "MEFS_SERVER"}' % (no_nodes-1))
    f.write("]\n")
    f.close()
    copyfile("CS/" + GRAPH_NAME + "/graph.json", PROTOCOL_FOLDER + "/graph.json")

