package agent.protocol;

import agent.communication.CommunicatingAgentInterface;
import host.communication.CommunicatingHostInterface;
import message.MessageInterface;
import protocol.Protocol;

/**
 * ProtocolAgents can extend this class.
 */
public abstract class AbstractProtocolAgent implements ProtocolAgent {

	/**
	 * Unique identifier
	 */
	private int id;
	/**
	 * The {@link CommunicatingAgentInterface} that is currently using this ProtocolAgent
	 */
	private CommunicatingAgentInterface communicatingAgent;
	/**
	 * The protocol that it is implementing
	 */
	private Protocol protocol;
	
	/**
	 * @param id - Unique identifier
	 * @param communicatingAgent - The {@link CommunicatingAgentInterface} that will use this ProtocolAgent
	 * @param protocol - The protocol that it is implementing
	 */
	public AbstractProtocolAgent(int id, CommunicatingAgentInterface communicatingAgent, Protocol protocol) {
		this.id = id;
		this.communicatingAgent = communicatingAgent;
		this.protocol = protocol;
	}
	
	@Override
	public void migrate(CommunicatingHostInterface destinationHost) {
	}
	
	@Override
	public void receiveMessage(MessageInterface message) {
		communicatingAgent.receiveMessage(message);
	}

	@Override
	public CommunicatingAgentInterface getCommunicatingAgent() {
		return communicatingAgent;
	}

	@Override
	public int getId() {
		return id;
	}

	@Override
	public Protocol getProtocol() {
		return protocol;
	}
	
	@Override
	public String toString() {
		return protocol + " Agent " + id;
	}
}
