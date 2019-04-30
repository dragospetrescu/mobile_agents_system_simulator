package broadcast;

import java.util.List;

import agent.communication.CommunicatingAgentInterface;
import agent.protocol.AbstractProtocolAgent;
import message.AgentCommunicationMessage;
import message.AgentCommunicationMessageInterface;
import statistics.StatisticsCreator;

/**
 * Agent implementation of the Broadcast Protocol The agent sends a message to
 * all the hosts hoping that one of them will be inhabited by the
 * destinationAgent
 */
public class BroadcastAgent extends AbstractProtocolAgent {

	/**
	 * @param communicatingAgent - the CommunicatingAgent that will use this
	 *                           protocol
	 */
	public BroadcastAgent(CommunicatingAgentInterface communicatingAgent) {
		super(communicatingAgent.getId(), communicatingAgent, communicatingAgent.getProtocol());
	}

	@Override
	public void prepareMessageTo(Integer destinationAgentId) {
		CommunicatingAgentInterface communicatingAgent = getCommunicatingAgent();
		BroadcastHost protocolHost = (BroadcastHost) getProtocolHost();

		List<Integer> allHostsIds = protocolHost.getAllNormalHosts();

		for (Integer hostId : allHostsIds) {
			AgentCommunicationMessageInterface message = new AgentCommunicationMessage(
					AgentCommunicationMessage.noMessages, communicatingAgent.getHostId(), hostId,
					communicatingAgent.getId(), destinationAgentId);
			protocolHost.sendMessage(message);
			StatisticsCreator.messageFailedDelivered(message);
		}
		AgentCommunicationMessage.noMessages++;
	}
}
