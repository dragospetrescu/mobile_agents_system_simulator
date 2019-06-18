import networkx as nx
import random
import os
from shutil import copyfile

NODES_LIST = [10, 50, 100, 1000]
AGENTS_LIST = [2, 5, 10, 100]

for no_nodes in NODES_LIST:

    G = nx.complete_graph(no_nodes)
    for (u, v, w) in G.edges(data=True):
        w['weight'] = random.randint(1, 10)

    A = nx.adjacency_matrix(G)
    matrix = A.todense().tolist()
    f = open("graph.json", "w+")

    f.write("[\n")
    for i in range(0, no_nodes):
        line = matrix[i]
        f.write(str(line))
        if i != no_nodes - 1:
            f.write(',\n')
    f.write("]\n")
    f.close()

    for no_agents in AGENTS_LIST:
        GRAPH_NAME = "complete_" + str(no_nodes) + "_" + str(no_agents)
        # ------------------- CS -------------------
        PROTOCOL = "CS"
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
                '{"id": %d,\n"hostId": %d,\n"protocol": "%s", "protocolArguments": {"serverHost": "%d"}}' % (id, host_id, PROTOCOL, no_nodes-1))
            if id != no_agents - 1:
                f.write(',\n')
        f.write("]\n")
        f.close()

        f = open(PROTOCOL_FOLDER + "/hosts.json", "w+")

        f.write("[\n")
        for id in range(0, no_nodes-1):
            f.write('{"id": %d,\n"protocol": "%s"}' % (id, PROTOCOL))
            if id != no_nodes - 2:
                f.write(',\n')
        f.write("]\n")
        f.close()

        f = open(PROTOCOL_FOLDER + "/special_hosts.json", "w+")

        f.write("[\n")
        f.write('{"id": %d,\n"protocol": "CSServer"}' % (no_nodes-1))
        f.write("]\n")
        f.close()
        copyfile("graph.json", PROTOCOL_FOLDER + "/graph.json")

        # ------------------- HSS -------------------
        PROTOCOL = "HSS"
        PROTOCOL_FOLDER = PROTOCOL + "/" + GRAPH_NAME
        try:
            os.makedirs(PROTOCOL_FOLDER)
        except OSError:
            print("Creation of the directory %s failed" % PROTOCOL_FOLDER)
        else:
            print("Creation of the directory %s success" % PROTOCOL_FOLDER)

        f = open(PROTOCOL_FOLDER + "/" + "agents.json", "w+")
        f.write("[\n")
        for id in range(0, no_agents):
            host_id = random.randint(0, no_nodes - 2)
            f.write(
                '{"id": %d,\n"hostId": %d,\n"protocol": "%s", "protocolArguments": {"homeServerHost": "%d"}}' % (id, host_id, PROTOCOL, no_nodes-1))
            if id != no_agents - 1:
                f.write(',\n')
        f.write("]\n")
        f.close()

        f = open(PROTOCOL_FOLDER + "/hosts.json", "w+")

        f.write("[\n")
        for id in range(0, no_nodes-1):
            f.write('{"id": %d,\n"protocol": "%s"}' % (id, PROTOCOL))
            if id != no_nodes - 2:
                f.write(',\n')
        f.write("]\n")
        f.close()

        f = open(PROTOCOL_FOLDER + "/special_hosts.json", "w+")

        f.write("[\n")
        f.write('{"id": %d,\n"protocol": "HSSServer"}' % (no_nodes-1))
        f.write("]\n")
        f.close()
        copyfile("graph.json", PROTOCOL_FOLDER + "/graph.json")

        # ------------------- Shadow -------------------
        PROTOCOL = "Shadow"
        PROTOCOL_FOLDER = PROTOCOL + "/" + GRAPH_NAME
        TTL=5
        try:
            os.makedirs(PROTOCOL_FOLDER)
        except OSError:
            print("Creation of the directory %s failed" % PROTOCOL_FOLDER)
        else:
            print("Creation of the directory %s success" % PROTOCOL_FOLDER)

        f = open(PROTOCOL_FOLDER + "/" + "agents.json", "w+")
        f.write("[\n")
        for id in range(0, no_agents):
            host_id = random.randint(0, no_nodes - 2)
            f.write(
                '{"id": %d,\n"hostId": %d,\n"protocol": "%s", "protocolArguments": {"homeServerHost": "%d", "ttl": "%d"}}' % (id, host_id, PROTOCOL, no_nodes - 1, TTL))
            if id != no_agents - 1:
                f.write(',\n')
        f.write("]\n")
        f.close()

        f = open(PROTOCOL_FOLDER + "/hosts.json", "w+")

        f.write("[\n")
        for id in range(0, no_nodes-1):
            f.write('{"id": %d,\n"protocol": "%s"}' % (id, PROTOCOL))
            if id != no_nodes - 2:
                f.write(',\n')
        f.write("]\n")
        f.close()

        f = open(PROTOCOL_FOLDER + "/special_hosts.json", "w+")

        f.write("[\n")
        f.write('{"id": %d,\n"protocol": "ShadowServer"}' % (no_nodes-1))
        f.write("]\n")
        f.close()
        copyfile("graph.json", PROTOCOL_FOLDER + "/graph.json")

        # ------------------- Broadcast -------------------
        PROTOCOL = "BROADCAST"
        PROTOCOL_FOLDER = PROTOCOL + "/" + GRAPH_NAME
        try:
            os.makedirs(PROTOCOL_FOLDER)
        except OSError:
            print("Creation of the directory %s failed" % PROTOCOL_FOLDER)
        else:
            print("Creation of the directory %s success" % PROTOCOL_FOLDER)

        f = open(PROTOCOL_FOLDER + "/" + "agents.json", "w+")
        f.write("[\n")
        for id in range(0, no_agents):
            host_id = random.randint(0, no_nodes - 1)
            f.write(
                '{"id": %d,\n"hostId": %d,\n"protocol": "%s"}' % (id, host_id, PROTOCOL))
            if id != no_agents - 1:
                f.write(',\n')
        f.write("]\n")
        f.close()

        f = open(PROTOCOL_FOLDER + "/hosts.json", "w+")

        f.write("[\n")
        for id in range(0, no_nodes):
            f.write('{"id": %d,\n"protocol": "%s"}' % (id, PROTOCOL))
            if id != no_nodes - 1:
                f.write(',\n')
        f.write("]\n")
        f.close()

        copyfile("graph.json", PROTOCOL_FOLDER + "/graph.json")

        # ------------------- FP -------------------
        PROTOCOL = "FP"
        PROTOCOL_FOLDER = PROTOCOL + "/" + GRAPH_NAME
        try:
            os.makedirs(PROTOCOL_FOLDER)
        except OSError:
            print("Creation of the directory %s failed" % PROTOCOL_FOLDER)
        else:
            print("Creation of the directory %s success" % PROTOCOL_FOLDER)

        f = open(PROTOCOL_FOLDER + "/" + "agents.json", "w+")
        f.write("[\n")
        for id in range(0, no_agents):
            host_id = random.randint(0, no_nodes - 1)
            f.write(
                '{"id": %d,\n"hostId": %d,\n"protocol": "%s"}' % (id, host_id, PROTOCOL))
            if id != no_agents - 1:
                f.write(',\n')
        f.write("]\n")
        f.close()

        f = open(PROTOCOL_FOLDER + "/hosts.json", "w+")

        f.write("[\n")
        for id in range(0, no_nodes):
            f.write('{"id": %d,\n"protocol": "%s"}' % (id, PROTOCOL))
            if id != no_nodes - 1:
                f.write(',\n')
        f.write("]\n")
        f.close()

        copyfile("graph.json", PROTOCOL_FOLDER + "/graph.json")

        # ------------------- BLACKBOARD -------------------
        PROTOCOL = "BLACKBOARD"
        PROTOCOL_FOLDER = PROTOCOL + "/" + GRAPH_NAME
        try:
            os.makedirs(PROTOCOL_FOLDER)
        except OSError:
            print("Creation of the directory %s failed" % PROTOCOL_FOLDER)
        else:
            print("Creation of the directory %s success" % PROTOCOL_FOLDER)

        f = open(PROTOCOL_FOLDER + "/" + "agents.json", "w+")
        f.write("[\n")
        for id in range(0, no_agents):
            host_id = random.randint(0, no_nodes - 1)
            f.write(
                '{"id": %d,\n"hostId": %d,\n"protocol": "%s"}' % (id, host_id, PROTOCOL))
            if id != no_agents - 1:
                f.write(',\n')
        f.write("]\n")
        f.close()

        f = open(PROTOCOL_FOLDER + "/hosts.json", "w+")

        f.write("[\n")
        for id in range(0, no_nodes):
            f.write('{"id": %d,\n"protocol": "%s"}' % (id, PROTOCOL))
            if id != no_nodes - 1:
                f.write(',\n')
        f.write("]\n")
        f.close()

        copyfile("graph.json", PROTOCOL_FOLDER + "/graph.json")
