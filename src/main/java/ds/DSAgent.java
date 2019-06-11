package ds;

import java.util.List;

import core.agent.communication.CommunicatingAgentInterface;
import core.agent.protocol.AbstractProtocolAgent;
import core.message.AgentCommunicationMessage;
import core.message.AgentCommunicationMessageInterface;

/**
 * Agent implementation of the Broadcast Protocol The agent sends a message to
 * all the hosts hoping that one of them will be inhabited by the
 * destinationAgent
 */
public class DSAgent extends AbstractProtocolAgent {

	/**
	 * @param communicatingAgent - the CommunicatingAgent that will use this
	 *                           protocol
	 */
	public DSAgent(CommunicatingAgentInterface communicatingAgent) {
		super(communicatingAgent.getId(), communicatingAgent, communicatingAgent.getProtocol());
	}

	@Override
	public void prepareMessageTo(Integer destinationAgentId) {
		CommunicatingAgentInterface communicatingAgent = getCommunicatingAgent();
		DSHost protocolHost = (DSHost) getProtocolHost();

		List<Integer> allHostsIds = protocolHost.getAllHosts();

		for (Integer hostId : allHostsIds) {
			AgentCommunicationMessageInterface message = new AgentCommunicationMessage(
					AgentCommunicationMessage.noMessages, communicatingAgent.getHostId(), hostId,
					communicatingAgent.getId(), destinationAgentId);
			protocolHost.sendMessage(message);
		}
		AgentCommunicationMessage.noMessages++;
	}
}
