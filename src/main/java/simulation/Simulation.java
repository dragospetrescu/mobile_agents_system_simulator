package simulation;

import agent.Agent;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import host.Host;
import host.implementation.DummyHost;
import host.router.Graph;
import message.Message;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.*;

public class Simulation {

    private List<Host> hosts;
    private List<Message> travelingMessages;

    public void init() {
        travelingMessages = new ArrayList<Message>();

        initHosts();
        initAgents();
    }

    public void initAgents() {
//        TODO: READ FROM INPUT FILE AND INITIALIZE
    }

    public void initHosts() {

        Gson gson = new Gson();
        JsonReader reader;
        try {
            reader = new JsonReader(new FileReader("hosts.json"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        }
        Type listType = new TypeToken<ArrayList<DummyHost>>(){}.getType();
        hosts = gson.fromJson(reader, listType);
        Map<Integer, Host> hostMap = new HashMap<Integer, Host>();
        for (Host host :
                hosts) {
            hostMap.put(host.getId(), host);
        }

        Graph graph = new Graph("graph.ini");
        graph.addRoutingToHosts(hostMap);

        for (Host host :
                hosts) {
            host.printRouting();
        }

    }


    public void addNewAgent(Agent agent, Host host) {
        host.addAgent(agent);
    }

    public void addNewHost(Host host) {
        hosts.add(host);
    }

    public void run() {

        for (long step = 0; step < Constants.NO_STEPS; step++) {

            for (Iterator<Message> messageIterator = travelingMessages.iterator(); messageIterator.hasNext(); ) {
                Message message = messageIterator.next();
                if (message.isArrived()) {
                    Host hostDestination = message.getHostDestination();
                    hostDestination.receiveMessage(message);
                    messageIterator.remove();
                } else {
                    message.travel();
                }
            }

            for (Host host : hosts) {

                if (host.wantsToSendMessage()) {
                    Message message = host.prepareMessage();
                    travelingMessages.add(message);
                }

                List<Agent> hostActiveAgents = host.getActiveAgents();
                for (Iterator<Agent> agentsIterator = hostActiveAgents.iterator(); agentsIterator.hasNext(); ) {
                    Agent agent = agentsIterator.next();

                    if (agent.wantsToSendMessage()) {
                        Message message = agent.prepareMessage();
                        travelingMessages.add(message);
                    }

                    if (agent.wantsToMigrate()) {
                        Message message = agent.prepareMigratingMessage();
                        travelingMessages.add(message);
                        agentsIterator.remove();
                    } else {
                        agent.work();
                    }
                }
            }
        }
    }
}
