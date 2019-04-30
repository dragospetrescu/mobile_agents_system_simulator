package cs;

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

public class CSAgent extends AbstractProtocolAgent {

	private Integer serverHostId;

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
		MessageInterface message = new AgentCommunicationMessage(sourceAgent.getHostId(), serverHostId,
				sourceAgent.getId(), destinationAgentId);
		ProtocolHost protocolHost = getProtocolHost();
		protocolHost.sendMessage(message);
	}

	@Override
	public void init(Map<String, String> protocolArguments, ProtocolHost protocolHost) {
		super.init(protocolArguments, protocolHost);
		serverHostId = Integer.parseInt(protocolArguments.get("serverHost"));
		CommunicatingAgentInterface communicatingAgent = getCommunicatingAgent();
		MessageInterface message = new LocationUpdateMessage(communicatingAgent.getHostId(),
				serverHostId, communicatingAgent.getId(), communicatingAgent.getHostId(),
				Protocol.CS);
		protocolHost.sendMessage(message);
	}

	@Override
	public void migrate(Integer destinationHostId, MigratingAgentMessageInterface migratingMessage) {
		super.migrate(destinationHostId, migratingMessage);
		
		CommunicatingAgentInterface communicatingAgent = getCommunicatingAgent();
		MessageInterface message = new LocationUpdateMessage(communicatingAgent.getHostId(), serverHostId,
				communicatingAgent.getId(), destinationHostId, Protocol.CS);
		ProtocolHost protocolHost = getProtocolHost();
		protocolHost.sendMessage(message);
	}

}
