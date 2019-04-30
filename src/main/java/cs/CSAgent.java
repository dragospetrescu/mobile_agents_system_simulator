package cs;

import java.util.Map;

import agent.communication.CommunicatingAgentInterface;
import agent.protocol.AbstractProtocolAgent;
import host.communication.CommunicatingHostInterface;
import host.protocol.ProtocolHost;
import message.AgentCommunicationMessage;
import message.AgentCommunicationMessageInterface;
import message.LocationUpdateMessage;
import message.MessageInterface;
import message.MigratingAgentMessageInterface;
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
