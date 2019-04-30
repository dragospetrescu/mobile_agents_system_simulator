package fp;

import java.util.List;
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

/**
 * The Agent from the FP documentation
 * 
 * Uses protocolHost forwarding proxy to find out agent location When agent
 * migrates it updates future location to its current host
 */
public class FPAgent extends AbstractProtocolAgent {

	/**
	 * @param communicatingAgent - agents that will use this protocol implementation
	 */
	public FPAgent(CommunicatingAgentInterface communicatingAgent) {
		super(communicatingAgent.getId(), communicatingAgent, communicatingAgent.getProtocol());
	}

	@Override
	public void prepareMessageTo(Integer destinationAgentId) {
		CommunicatingAgentInterface sourceAgent = getCommunicatingAgent();
		FPHost protocolHost = (FPHost) getProtocolHost();

		Integer destinationHostId = protocolHost.getProxy(destinationAgentId);

		MessageInterface message = new AgentCommunicationMessage(sourceAgent.getHostId(), destinationHostId,
				sourceAgent.getId(), destinationAgentId);
		protocolHost.sendMessage(message);
	}

	@Override
	public void migrate(Integer destinationHostId, MigratingAgentMessageInterface migratingMessage) {
		super.migrate(destinationHostId, migratingMessage);

		CommunicatingAgentInterface sourceAgent = getCommunicatingAgent();
		FPHost protocolHost = (FPHost) getProtocolHost();

		protocolHost.updateProxy(sourceAgent.getId(), destinationHostId);
	}

	@Override
	public void init(Map<String, String> protocolArguments, ProtocolHost protocolHost) {
		super.init(protocolArguments, protocolHost);
		CommunicatingAgentInterface communicatingAgent = getCommunicatingAgent();
		FPHost fpProtocolHost = (FPHost) protocolHost;
		List<Integer> allNormalHosts = fpProtocolHost.getAllNormalHosts();
		for (Integer hostId : allNormalHosts) {
			LocationUpdateMessage message = new LocationUpdateMessage(communicatingAgent.getHostId(), hostId, getId(),
					communicatingAgent.getHostId(), Protocol.FP);
			fpProtocolHost.sendMessage(message);

		}

	}
}
