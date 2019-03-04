package simulation;

import agent.Agent;
import host.Host;
import message.Message;

import java.util.*;

public abstract class Simulation {

    private List<Host> hosts;
    private List<Message> travelingMessages;

    public void init() {

        hosts = new ArrayList<>();
        travelingMessages = new ArrayList<>();

        initHosts();
        initAgents();
    }

    public abstract void initAgents();

    public abstract void initHosts();

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
