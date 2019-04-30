package cs;

import java.util.Map;

import core.agent.communication.CommunicatingAgentInterface;
import core.agent.protocol.AbstractProtocolAgent;
import core.host.protocol.ProtocolHost;
import core.message.AgentCommunicationMessage;
import core.message.LocationUpdateMessage;
import core.message.MessageInterface;
import core.message.MigratingAgentMessageInterface;
import protocol.Protocol;

/**
 * Agent implementation of the CS protocol. When an agent wants to migrate it
 * sends an update location to the central server. When an agent wants to send a
 * message it sends the message to the central server which redirects it.
 */
public class CSAgent extends AbstractProtocolAgent {

	/**
	 * Id of the central server
	 */
	private Integer centralServerId;

	/**
	 * @param communicatingAgent - the CommunicatingAgent that will use this
	 *                           protocol
	 */
	public CSAgent(CommunicatingAgentInterface communicatingAgent) {
		super(communicatingAgent.getId(), communicatingAgent, communicatingAgent.getProtocol());
	}

	@Override
	public void prepareMessageTo(Integer destinationAgentId) {
		CommunicatingAgentInterface sourceAgent = getCommunicatingAgent();
		MessageInterface message = new AgentCommunicationMessage(sourceAgent.getHostId(), centralServerId,
				sourceAgent.getId(), destinationAgentId);
		ProtocolHost protocolHost = getProtocolHost();
		protocolHost.sendMessage(message);
	}

	@Override
	public void init(Map<String, String> protocolArguments, ProtocolHost protocolHost) {
		super.init(protocolArguments, protocolHost);
		centralServerId = Integer.parseInt(protocolArguments.get("serverHost"));
		CommunicatingAgentInterface communicatingAgent = getCommunicatingAgent();
		MessageInterface message = new LocationUpdateMessage(communicatingAgent.getHostId(), centralServerId,
				communicatingAgent.getId(), communicatingAgent.getHostId(), Protocol.CS);
		protocolHost.sendMessage(message);
	}

	@Override
	public void migrate(Integer destinationHostId, MigratingAgentMessageInterface migratingMessage) {
		super.migrate(destinationHostId, migratingMessage);

		CommunicatingAgentInterface communicatingAgent = getCommunicatingAgent();
		MessageInterface message = new LocationUpdateMessage(communicatingAgent.getHostId(), centralServerId,
				communicatingAgent.getId(), destinationHostId, Protocol.CS);
		ProtocolHost protocolHost = getProtocolHost();
		protocolHost.sendMessage(message);
	}

}
