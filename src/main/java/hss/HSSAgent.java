package hss;

import java.util.Map;

import core.agent.communication.CommunicatingAgentInterface;
import core.agent.protocol.AbstractProtocolAgent;
import core.host.communication.CommunicatingHostInterface;
import core.host.protocol.ProtocolHost;
import core.message.AgentCommunicationMessage;
import core.message.AgentCommunicationMessageInterface;
import core.message.LocationUpdateMessage;
import core.message.MessageInterface;
import core.message.MigratingAgentMessageInterface;
import protocol.Protocol;

/**
 * The Agent from the HSS documentation
 * 
 * When agent migrates it sends to it's home host message that updates the
 * location database
 * 
 * Agent 0 wants to send message to Agent 1 Agent 0 sends message to HomeServer
 * HomeServer sends message to Agent 1 Home Agent Home Agent sends message
 * location of the Agent
 */
public class HSSAgent extends AbstractProtocolAgent {

	/**
	 * Server that has mapped agent to homeHost
	 */
	private Integer homeServerHostId;
	/**
	 * This agent's home Host
	 */
	private Integer homeAgentHostId;

	/**
	 * @param communicatingAgent - agent that is going to use this protocol
	 *                           implementation
	 */
	public HSSAgent(CommunicatingAgentInterface communicatingAgent) {
		super(communicatingAgent.getId(), communicatingAgent, communicatingAgent.getProtocol());
	}

	@Override
	public void prepareMessageTo(Integer destinationAgentId) {
		CommunicatingAgentInterface communicatingAgent = getCommunicatingAgent();
		ProtocolHost protocolHost = getProtocolHost();

		MessageInterface forwardedMessage = new AgentCommunicationMessage(communicatingAgent.getHostId(),
				homeServerHostId, communicatingAgent.getId(), destinationAgentId);
		protocolHost.sendMessage(forwardedMessage);
	}

	@Override
	public void migrate(Integer newHostId, MigratingAgentMessageInterface migratingMessage) {
		super.migrate(newHostId, migratingMessage);
		CommunicatingAgentInterface communicatingAgent = getCommunicatingAgent();
		ProtocolHost protocolHost = getProtocolHost();

		MessageInterface message = new LocationUpdateMessage(communicatingAgent.getHostId(), homeAgentHostId,
				communicatingAgent.getId(), newHostId, Protocol.HSS);
		protocolHost.sendMessage(message);
	}

	@Override
	public void init(Map<String, String> protocolArguments, ProtocolHost protocolHost) {
		super.init(protocolArguments, protocolHost);

		homeServerHostId = Integer.parseInt(protocolArguments.get("homeServerHost"));
		homeAgentHostId = getCommunicatingAgent().getHostId();
		CommunicatingAgentInterface communicatingAgent = getCommunicatingAgent();
		MessageInterface message = new LocationUpdateMessage(communicatingAgent.getHostId(), homeServerHostId,
				communicatingAgent.getId(), homeAgentHostId, Protocol.HSS);
		protocolHost.sendMessage(message);
	}
}
