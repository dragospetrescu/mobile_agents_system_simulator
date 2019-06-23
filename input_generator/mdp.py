import networkx as nx
import random
import os
from shutil import copyfile

NODES_LIST = [10, 50, 100, 1000, 10, 50, 100]
AGENTS_LIST = [2, 5,  10,  100, 100, 200, 100]
NO_LEVELS = [2, 2, 3, 3, 2, 2, 3]
NO_LEAFS =  [2, 4, 3, 4, 2, 4, 3]

for i in range(0, len(NODES_LIST)):
    no_nodes = NODES_LIST[i]
    no_agents = AGENTS_LIST[i]
    no_leafs = NO_LEAFS[i]
    no_levels = NO_LEVELS[i]

    OVERHEAD = int((pow(no_leafs, no_levels) - 1) / (no_leafs - 1))
    GRAPH_NAME = "complete_" + str(no_nodes) + "_" + str(no_agents)
    # ------------------- MDP -------------------
    PROTOCOL = "MDP"
    PROTOCOL_FOLDER = PROTOCOL + "/" + GRAPH_NAME
    try:
        os.makedirs(PROTOCOL_FOLDER)
    except OSError:
        print("Creation of the directory %s failed" % PROTOCOL_FOLDER)

    f = open(PROTOCOL_FOLDER + "/" + "agents.json", "w+")
    f.write("[\n")
    for id in range(0, no_agents):
        host_id = random.randint(0, no_nodes - OVERHEAD - 1)
        f.write(
            '{"id": %d,\n"hostId": %d,\n"protocol": "%s"}' % (id, host_id, PROTOCOL))
        if id != no_agents - 1:
            f.write(',\n')
    f.write("]\n")
    f.close()

    f = open(PROTOCOL_FOLDER + "/hosts.json", "w+")

    f.write("[\n")
    for id in range(0, no_nodes - OVERHEAD):
        f.write('{"id": %d,\n"protocol": "%s", "protocolArguments": {"gateway": "%d"}}' % (id, PROTOCOL, id % pow(no_leafs, no_levels - 1) + no_nodes - OVERHEAD))
        if id != no_nodes - OVERHEAD - 1:
            f.write(',\n')
    f.write("]\n")
    f.close()

    f = open(PROTOCOL_FOLDER + "/special_hosts.json", "w+")

    f.write("[\n")
    id = no_nodes - 1
    for level in range(0, no_levels):
        for i in range(0, pow(no_leafs, level)):
            if level != 0:
                f.write('{"id": %d,\n"protocol": "MDP_GATEWAY", "protocolArguments": {"gateway": "%d"}}' % (
                id, id % pow(no_leafs, level - 1) +no_nodes - int((pow(no_leafs, level) - 1) / (no_leafs - 1))))
            else:
                f.write('{"id": %d,\n"protocol": "MDP_GATEWAY", "protocolArguments": {}}' % id)
            id = id -1

            if id != no_nodes - OVERHEAD - 1:
                f.write(',\n')

    f.write("]\n")
    f.close()
    copyfile("CS/" + GRAPH_NAME + "/graph.json", PROTOCOL_FOLDER + "/graph.json")

