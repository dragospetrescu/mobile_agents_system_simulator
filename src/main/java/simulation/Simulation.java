package simulation;

import agent.communication.CommunicatingAgentInterface;
import host.communication.CommunicatingHostInterface;
import host.router.Graph;
import message.MessageInterface;
import message.MessagesManager;

import java.util.*;

public class Simulation {

    private List<CommunicatingHostInterface> hosts;
    private List<CommunicatingAgentInterface> agents;
    private static Graph graph;
    private MessagesManager messagesManager;

    public Simulation() {
        agents = new ArrayList<>();
    }
    
//    public void init() {
//        initHosts();
//        messagesManager = new MessagesManager(graph);
//        initAgents();
//        for (CommunicatingAgent agent : agents) {
//            agent.setAllAgents(agents);
//        }
//    }
//
//    public void initAgents() {}
//
//    public void initHosts() {
//
//        Map<Integer, Host> hostMap = new HashMap<Integer, Host>();
//        for (Host host: hosts) {
//            hostMap.put(host.getId(), host);
//        }
//
//        graph = new Graph("graph.ini");
//        graph.addRoutingToHosts(hostMap);
//    }

    public void run() {

        for (int step = 0; step < Constants.NO_STEPS; step++) {

            messagesManager.travelMessages();
            List<MessageInterface> arrivedMessages = messagesManager.getArrivedMessages();
            for (MessageInterface message : arrivedMessages) {
            	CommunicatingHostInterface nextHopHost = message.getNextHop();
                nextHopHost.receiveMessage(message);
            }

            for (CommunicatingHostInterface host : hosts) {

                if (host.wantsToSendMessage()) {
                    List<MessageInterface> messages = host.sendMessages();
                    messagesManager.addAllMessages(messages);
                }

                List<CommunicatingAgentInterface> hostActiveAgents = host.getActiveAgents();
                for (Iterator<CommunicatingAgentInterface> agentsIterator = hostActiveAgents.iterator(); agentsIterator.hasNext(); ) {
                    CommunicatingAgentInterface agent = agentsIterator.next();

                    if (agent.wantsToSendMessage()) {
                        List<MessageInterface> messages = agent.sendMessages();
                        messagesManager.addAllMessages(messages);
                    }

                    if (agent.wantsToMigrate()) {
                        MessageInterface message = agent.prepareMigratingMessage();
                        messagesManager.addMessage(message);
                        agentsIterator.remove();
                    } else {
                        agent.work();
                    }
                }
            }
        }
    }
}
