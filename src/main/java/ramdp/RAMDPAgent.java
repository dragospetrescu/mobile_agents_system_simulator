package ramdp;

import java.util.Map;

import core.agent.communication.CommunicatingAgentInterface;
import core.agent.protocol.AbstractProtocolAgent;
import core.host.protocol.ProtocolHost;
import core.message.AgentCommunicationMessage;
import core.message.AgentCommunicationMessageInterface;
import core.message.LocationUpdateMessage;
import core.message.LocationUpdateMessageInterface;
import protocol.Protocol;

/**
 * Agent implementation of the Broadcast Protocol The agent sends a message to
 * all the hosts hoping that one of them will be inhabited by the
 * destinationAgent
 */
public class RAMDPAgent extends AbstractProtocolAgent {

	private Integer hn;
	private Integer ls;

	/**
	 * @param communicatingAgent - the CommunicatingAgent that will use this
	 *                           protocol
	 */
	public RAMDPAgent(CommunicatingAgentInterface communicatingAgent) {
		super(communicatingAgent.getId(), communicatingAgent, communicatingAgent.getProtocol());
	}

	@Override
	public void prepareMessageTo(Integer destinationAgentId) {
		CommunicatingAgentInterface communicatingAgent = getCommunicatingAgent();
		ProtocolHost protocolHost = getProtocolHost();
		AgentCommunicationMessageInterface message = new AgentCommunicationMessage(communicatingAgent.getHostId(), ls,
				communicatingAgent.getId(), destinationAgentId);
		protocolHost.sendMessage(message);
	}

	@Override
	public void init(Map<String, String> protocolArguments, ProtocolHost protocolHost) {
		super.init(protocolArguments, protocolHost);
		hn = Integer.parseInt(protocolArguments.get("hn"));
		ls = Integer.parseInt(protocolArguments.get("ls"));

		RAMDPHomeNode ramdpHN = (RAMDPHomeNode) protocolHost;
		ramdpHN.setRS(getId());

		LocationUpdateMessageInterface locMessage = new LocationUpdateMessage(protocolHost.getId(), ls, getId(), hn,
				Protocol.RAMDP);
		ramdpHN.sendMessage(locMessage);
	}

	public Integer getHN() {
		return hn;
	}
}
