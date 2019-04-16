package message;

import agent.communication.CommunicatingAgentInterface;
import host.communication.CommunicatingHostInterface;

/**
 * Generic message, implements the next hop logic Can be extended to create
 * custom, protocol specific messages
 */
public class Message implements MessageInterface {

	/**
	 * Unique id
	 */
	private int id;
	/**
	 * The host from which the message was sent
	 */
	private CommunicatingHostInterface sourceHost;
	/**
	 * The final host destination
	 */
	private CommunicatingHostInterface destinationHost;
	/**
	 * The last host that redirected the message
	 */
	private CommunicatingHostInterface previousHopHost;
	/**
	 * The next host to the final destination
	 */
	private CommunicatingHostInterface nextHopHost;
	/**
	 * The agent that sent the message. Can be null if the message was sent by a
	 * host.
	 */
	private CommunicatingAgentInterface sourceAgent;
	/**
	 * The destination agent. Can be null if the destination is a host.
	 */
	private CommunicatingAgentInterface destinationAgent;

	/**
	 * Used to make sure each message receives a unique id
	 */
	public static int noMessages = 0;

	/**
	 * Warning. You should only use this constructor if the id provided is unique
	 * 
	 * @param id - the unique identifier
	 * @param sourceHost - host from where the message is sent
	 * @param destinationHost - the host where the message has to arrive
	 * @param sourceAgent - the agent that sends the message
	 * @param destinationAgent - the agent that has to receive the message
	 */
	public Message(int id, CommunicatingHostInterface sourceHost, CommunicatingHostInterface destinationHost,
			CommunicatingAgentInterface sourceAgent, CommunicatingAgentInterface destinationAgent) {
		this.sourceHost = sourceHost;
		this.destinationHost = destinationHost;
		this.sourceAgent = sourceAgent;
		this.destinationAgent = destinationAgent;
		previousHopHost = sourceHost;
		nextHopHost = sourceHost.getNextHop(destinationHost);
		this.id = id;
	}

	/**
	 * Use this constructor if you don't care about the id
	 * 
	 * @param sourceHost - host from where the message is sent
	 * @param destinationHost - the host where the message has to arrive
	 * @param sourceAgent - the agent that sends the message
	 * @param destinationAgent - the agent that has to receive the message
	 */
	public Message(CommunicatingHostInterface sourceHost, CommunicatingHostInterface destinationHost,
			CommunicatingAgentInterface sourceAgent, CommunicatingAgentInterface destinationAgent) {
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
	public void setHostDestination(CommunicatingHostInterface destinationHost) {
		this.destinationHost = destinationHost;
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
