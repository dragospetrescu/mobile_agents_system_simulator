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

    public static int noMessages = 0;


    public Message(int id, CommunicatingHostInterface sourceHost, CommunicatingHostInterface destinationHost, CommunicatingAgentInterface sourceAgent, CommunicatingAgentInterface destinationAgent) {
        this.sourceHost = sourceHost;
        this.destinationHost = destinationHost;
        this.sourceAgent = sourceAgent;
        this.destinationAgent = destinationAgent;
        previousHopHost = destinationHost;
        nextHopHost = sourceHost.getNextHop(destinationHost);
        this.id = id;
    }
    
    public Message(CommunicatingHostInterface sourceHost, CommunicatingHostInterface destinationHost, CommunicatingAgentInterface sourceAgent, CommunicatingAgentInterface destinationAgent) {
    	this(noMessages, sourceHost, destinationHost, sourceAgent, destinationAgent);
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Message other = (Message) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
	
}
