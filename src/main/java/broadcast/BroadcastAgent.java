package broadcast;

import java.util.List;
import java.util.Map;

import agent.communication.CommunicatingAgentInterface;
import agent.protocol.AbstractProtocolAgent;
import host.communication.CommunicatingHostInterface;
import message.Message;
import message.MessageInterface;
import message.implementation.NormalCommunicationMessage;
import statistics.StatisticsCreator;

/**
 * Agent implementation of the Broadcast Protocol
 * The agent sends a message to all the hosts hoping that one of them
 * will be inhabited by the destinationAgent
 */
public class BroadcastAgent extends AbstractProtocolAgent {

	/**
	 * @param communicatingAgent - the CommunicatingAgent that will use this protocol
	 */
	public BroadcastAgent(CommunicatingAgentInterface communicatingAgent) {
		super(communicatingAgent.getId(), communicatingAgent, communicatingAgent.getProtocol());
	}

	@Override
	public void receiveMessage(MessageInterface message) {
		super.receiveMessage(message);
	}

	@Override
	public void prepareMessageTo(CommunicatingAgentInterface destinationAgent) {
		CommunicatingAgentInterface communicatingAgent = getCommunicatingAgent();
		List<CommunicatingHostInterface> allHosts = communicatingAgent.getAllNormalHosts();
		for (CommunicatingHostInterface communicatingHostDestination : allHosts) {
			if (!communicatingHostDestination.equals(communicatingAgent.getHost())) {
				MessageInterface message = new NormalCommunicationMessage(Message.noMessages, communicatingAgent.getHost(),
						communicatingHostDestination, communicatingAgent, destinationAgent);
				communicatingAgent.addMessage(message);
				StatisticsCreator.messageFailedDelivered(message);
			}
		}
		Message.noMessages++;
	}

	@Override
	public void init(Map<String, String> protocolArguments) {
	}
}
