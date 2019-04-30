package shadow;

import java.util.List;
import java.util.Map;

import agent.communication.CommunicatingAgentInterface;
import agent.protocol.AbstractProtocolAgent;
import fp.FPHost;
import host.communication.CommunicatingHostInterface;
import host.protocol.ProtocolHost;
import message.AgentCommunicationMessage;
import message.AgentCommunicationMessageInterface;
import message.LocationUpdateMessage;
import message.MessageInterface;
import message.MigratingAgentMessageInterface;
import protocol.Protocol;

/**
 * The Agent from the Shadow documentation
 * 
 * The agent migrates ttl times and leaving to the current host a proxy to it's
 * new host. After the ttl'nth migration agent sends an update migration start
 * host to the home server
 */
public class ShadowAgent extends AbstractProtocolAgent {

	/**
	 * Server that has mapped agent to start of the migration path
	 */
	private Integer homeServerHostId;
	/**
	 * Time to live - migrations until the home server receives update about the
	 * agent's migration decision
	 */
	private int ttl;
	private int originalTtl;

	/**
	 * @param communicatingAgent that is going to use this protocol
	 */
	public ShadowAgent(CommunicatingAgentInterface communicatingAgent) {
		super(communicatingAgent.getId(), communicatingAgent, communicatingAgent.getProtocol());
	}

	@Override
	public void prepareMessageTo(Integer destinationAgentId) {
		CommunicatingAgentInterface communicatingAgent = getCommunicatingAgent();
		ProtocolHost protocolHost = getProtocolHost();

		MessageInterface message = new AgentCommunicationMessage(communicatingAgent.getHostId(), homeServerHostId,
				communicatingAgent.getId(), destinationAgentId);
		protocolHost.sendMessage(message);
	}

	@Override
	public void migrate(Integer destinationHostId, MigratingAgentMessageInterface migratingMessage) {
		super.migrate(destinationHostId, migratingMessage);
		CommunicatingAgentInterface sourceAgent = getCommunicatingAgent();
		ShadowHost protocolHost = (ShadowHost) getProtocolHost();
		ttl--;
		if (ttl <= 0) {
			MessageInterface locationUpdatMessage = new LocationUpdateMessage(sourceAgent.getHostId(), homeServerHostId,
					sourceAgent.getId(), destinationHostId, Protocol.Shadow);

			protocolHost.sendMessage(locationUpdatMessage);
			ttl = originalTtl;
		}
		protocolHost.updateProxy(sourceAgent.getId(), destinationHostId);

	}

	@Override
	public void init(Map<String, String> protocolArguments, ProtocolHost protocolHost) {
		super.init(protocolArguments, protocolHost);

		ttl = Integer.parseInt(protocolArguments.get("ttl"));
		originalTtl = ttl;
		homeServerHostId = Integer.parseInt(protocolArguments.get("homeServerHost"));
		CommunicatingAgentInterface communicatingAgent = getCommunicatingAgent();
		ShadowHost fpProtocolHost = (ShadowHost) protocolHost;

		MessageInterface message = new LocationUpdateMessage(communicatingAgent.getHostId(), homeServerHostId,
				communicatingAgent.getId(), communicatingAgent.getHostId(), Protocol.FP);
		fpProtocolHost.sendMessage(message);

		List<Integer> allNormalHosts = fpProtocolHost.getAllNormalHosts();
		for (Integer hostId : allNormalHosts) {
			message = new LocationUpdateMessage(communicatingAgent.getHostId(), hostId, getId(),
					communicatingAgent.getHostId(), Protocol.FP);
			fpProtocolHost.sendMessage(message);

		}
	}

}
