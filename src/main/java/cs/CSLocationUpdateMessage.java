package cs;

import agent.communication.CommunicatingAgentInterface;
import host.communication.CommunicatingHostInterface;
import message.AgentCommunicationMessage;

/**
 * The Update message sent when agent migrates
 * 
 * When agent migrates it sends to it's home host message
 * that updates the location database
 */
public class CSLocationUpdateMessage extends AgentCommunicationMessage {

	/**
	 * The host to which the agent is migrating
	 */
	private CommunicatingHostInterface newHostLocation;
	
	/**
	 * @param sourceHost - host from where the message is sent
	 * @param destinationHost - the host where the message has to arrive
	 * @param sourceAgent - the agent that sends the message
	 * @param destinationAgent - the agent that has to receive the message
	 * @param newHostLocation - the host to which the agent is migrating
	 */
	public CSLocationUpdateMessage(int sourceHost, int destinationHost,
			int sourceAgent, CommunicatingHostInterface newHostLocation) {
		super(sourceHost, destinationHost, sourceAgent, -1);
		this.newHostLocation = newHostLocation;
	}

	/**
	 * @return the host to which the agent is migrating
	 */
	public CommunicatingHostInterface getNewHostLocation() {
		return newHostLocation;
	}
}
