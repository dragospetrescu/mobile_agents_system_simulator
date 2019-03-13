package host;

import agent.Agent;
import helpers.Logger;
import message.Message;
import message.implementation.MigratingAgentMessage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class Host implements HostInterface {

    public int id;
    private List<Agent> activeAgents;
    private Map<Host, Host> nextHopMap;
    private List<Message> messagesToBeSent;

    public Host() {
        this.activeAgents = new ArrayList<Agent>();
        nextHopMap = new HashMap<Host, Host>();
        messagesToBeSent = new ArrayList<Message>();
    }

    public List<Agent> getActiveAgents() {
        return activeAgents;
    }

    public void addAgent(Agent agent) {
        activeAgents.add(agent);
        agent.setHost(this);
    }

    public void addRouteNextHop(Host destinationRouter, Host nextHopRouter) {
        nextHopMap.put(destinationRouter, nextHopRouter);
    }

    @Override
    public String toString() {
        return "Host " + id;
    }

    public int getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Host host = (Host) o;

        return id == host.id;
    }

    @Override
    public int hashCode() {
        return id;
    }

    public void printRouting() {
        System.out.println(toString());

        for (Host destHost : nextHopMap.keySet()) {
            System.out.println("To " + destHost + " by " + nextHopMap.get(destHost));
        }
    }

    public void receiveMessage(Message message) {
        Logger.i("Message arrived at " + toString());
        Host messageDestination = message.getHostDestination();
        if (messageDestination.equals(this)) {
            if (message instanceof MigratingAgentMessage) {
                MigratingAgentMessage migratingAgentMessage = (MigratingAgentMessage) message;

                Agent migratingAgent = migratingAgentMessage.getMigratingAgent();
                addAgent(migratingAgent);
                Logger.i(toString() + " received " + migratingAgent);
                return;

            } else {
                interpretMessage(message);
                return;
            }
        }
        Host nextHop = nextHopMap.get(messageDestination);
        message.setNextHopHost(nextHop);
        message.setPreviousHopHost(this);
        addMessageForSending(message);
    }

    public void addMessageForSending(Message message) {
        messagesToBeSent.add(message);
    }

    public List<Message> getMessagesToBeSent() {
        List<Message> messages = messagesToBeSent;
        messagesToBeSent = new ArrayList<Message>();
        return messages;
    }

    public boolean wantsToSendMessage() {
        return !messagesToBeSent.isEmpty();
    }

    public Host getNextHop(Host destinationHost) {
        return nextHopMap.get(destinationHost);
    }
}
