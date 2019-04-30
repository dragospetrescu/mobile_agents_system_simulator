package core.agent.protocol;

import java.util.Map;

import core.agent.communication.CommunicatingAgentInterface;
import core.host.communication.CommunicatingHostInterface;
import core.host.protocol.ProtocolHost;
import core.message.AgentCommunicationMessageInterface;
import core.message.MessageInterface;
import core.message.MigratingAgentMessageInterface;
import protocol.Protocol;

/**
 * ProtocolAgents can extend this class.
 */
public abstract class AbstractProtocolAgent implements ProtocolAgent {

	/**
	 * Unique identifier
	 */
	private Integer id;
	/**
	 * The {@link CommunicatingAgentInterface} that is currently using this ProtocolAgent
	 */
	private CommunicatingAgentInterface communicatingAgent;
	
	private ProtocolHost protocolHost;
	/**
	 * The protocol that it is implementing
	 */
	private Protocol protocol;
	
	/**
	 * @param id - Unique identifier
	 * @param communicatingAgent - The {@link CommunicatingAgentInterface} that will use this ProtocolAgent
	 * @param protocol - The protocol that it is implementing
	 */
	public AbstractProtocolAgent(Integer id, CommunicatingAgentInterface communicatingAgent, Protocol protocol) {
		this.id = id;
		this.communicatingAgent = communicatingAgent;
		this.protocol = protocol;
	}
	
	@Override
	public void migrate(Integer destinationHostId, MigratingAgentMessageInterface migratingMessage) {
		ProtocolHost protocolHost = getProtocolHost();
		protocolHost.sendMessage(migratingMessage);
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
	public ProtocolHost getProtocolHost() {
		return protocolHost;
	}
	
	@Override
	public Integer getId() {
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
	
	@Override
	public void init(Map<String, String> protocolArguments, ProtocolHost protocolHost) {
		this.protocolHost = protocolHost;
	}
	
	@Override
	public void setProtocolHost(ProtocolHost protocolHost) {
		this.protocolHost = protocolHost;
	}
}