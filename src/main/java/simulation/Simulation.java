package simulation;

import agent.Agent;
import host.Host;
import host.router.Graph;
import message.Message;
import message.MessagesManager;

import java.util.*;

public abstract class Simulation {

    private List<Host> hosts;
    private List<Agent> agents;
    private static Graph graph;
    private MessagesManager messagesManager;

    public Simulation() {
        agents = new ArrayList<>();
    }
    
    public void init() {
        initHosts();
        messagesManager = new MessagesManager(graph);
        initAgents();
        for (Agent agent : agents) {
            agent.setAllAgents(agents);
        }
    }

    public abstract void initAgents();

    public void initHosts() {

        Map<Integer, Host> hostMap = new HashMap<Integer, Host>();
        for (Host host: hosts) {
            hostMap.put(host.getId(), host);
        }

        graph = new Graph("graph.ini");
        graph.addRoutingToHosts(hostMap);
    }


    public void addNewAgent(Agent agent, Host host) {
        host.addAgent(agent);
        agents.add(agent);
    }

    public void addNewHost(Host host) {
        hosts.add(host);
    }

    public static long  step;

    public void run() {

        for (step = 0; step < Constants.NO_STEPS; step++) {

            messagesManager.travelMessages();
            List<Message> arrivedMessages = messagesManager.getArrivedMessages();
            for (Message message : arrivedMessages) {
                Host nextHopHost = message.getNextHop();
                nextHopHost.receiveMessage(message);
            }

            for (Host host : hosts) {

                if (host.wantsToSendMessage()) {
                    List<Message> messages = host.getMessagesToBeSent();
                    messagesManager.addAllMessages(messages);
                }

                List<Agent> hostActiveAgents = host.getActiveAgents();
                for (Iterator<Agent> agentsIterator = hostActiveAgents.iterator(); agentsIterator.hasNext(); ) {
                    Agent agent = agentsIterator.next();

                    if (agent.wantsToSendMessage()) {
                        List<Message> messages = agent.sendMessages();
                        messagesManager.addAllMessages(messages);
                    }

                    if (agent.wantsToMigrate()) {
                        Message message = agent.prepareMigratingMessage();
                        messagesManager.addMessage(message);
                        agentsIterator.remove();
                    } else {
                        agent.work();
                    }
                }
            }
        }
    }

    public void setHosts(List<Host> hosts) {
        this.hosts = hosts;
    }

    public List<Host> getHosts() {
        return hosts;
    }
}
