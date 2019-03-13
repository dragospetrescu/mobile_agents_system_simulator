package message;

import agent.Agent;
import helpers.RandomAssigner;
import host.Host;

public abstract class Message implements MessageInterface {

    private int id;
    private Host sourceHost;
    private Host destinationHost;
    private Host previousHopHost;
    private Host nextHopHost;
    private Agent sourceAgent;
    private Agent destinationAgent;

    private static int noMessages = 0;


    public Message(Host sourceHost, Host destinationHost, Agent sourceAgent, Agent destinationAgent) {
        this.sourceHost = sourceHost;
        this.destinationHost = destinationHost;
        this.sourceAgent = sourceAgent;
        this.destinationAgent = destinationAgent;
        previousHopHost = destinationHost;
        nextHopHost = sourceHost.getNextHop(destinationHost);
    }

    public Host getHostDestination() {
        return destinationHost;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Message " + id;
    }


    public void setNextHopHost(Host nextHopHost) {
        this.nextHopHost = nextHopHost;
    }

    public void setPreviousHopHost(Host previousHopHost) {
        this.previousHopHost = previousHopHost;
    }

    @Override
    public Host getNextHop() {
        return nextHopHost;
    }

    @Override
    public Host getPreviousHop() {
        return previousHopHost;
    }

    @Override
    public Host getHostSource() {
        return sourceHost;
    }

    @Override
    public Agent getAgentSource() {
        return sourceAgent;
    }

    @Override
    public Agent getAgentDestination() {
        return destinationAgent;
    }
}
