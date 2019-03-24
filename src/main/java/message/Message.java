package message;

import agent.communication.CommunicatingAgentInterface;
import host.communication.CommunicatingHostInterface;

public class Message implements MessageInterface {

    private int id;
    private CommunicatingHostInterface sourceHost;
    private CommunicatingHostInterface destinationHost;
    private CommunicatingHostInterface previousHopHost;
    private CommunicatingHostInterface nextHopHost;
    private CommunicatingAgentInterface sourceAgent;
    private CommunicatingAgentInterface destinationAgent;

    private static int noMessages = 0;


    public Message(CommunicatingHostInterface sourceHost, CommunicatingHostInterface destinationHost, CommunicatingAgentInterface sourceAgent, CommunicatingAgentInterface destinationAgent) {
        this.sourceHost = sourceHost;
        this.destinationHost = destinationHost;
        this.sourceAgent = sourceAgent;
        this.destinationAgent = destinationAgent;
        previousHopHost = destinationHost;
        nextHopHost = sourceHost.getNextHop(destinationHost);
        id = noMessages;
        noMessages++;
    }

    @Override
    public CommunicatingHostInterface getHostDestination() {
        return destinationHost;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Message " + id;
    }

    @Override
    public CommunicatingHostInterface getNextHop() {
        return nextHopHost;
    }

    @Override
    public CommunicatingHostInterface getPreviousHop() {
        return previousHopHost;
    }

    @Override
    public CommunicatingHostInterface getHostSource() {
        return sourceHost;
    }

    @Override
    public CommunicatingAgentInterface getAgentSource() {
        return sourceAgent;
    }

    @Override
    public CommunicatingAgentInterface getAgentDestination() {
        return destinationAgent;
    }

	@Override
	public void setNextHopHost(CommunicatingHostInterface nextHop) {
		this.previousHopHost = this.nextHopHost;
		this.nextHopHost = nextHop;
	}
}
