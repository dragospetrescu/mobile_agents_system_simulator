package broadcast;

import java.util.List;

import agent.communication.CommunicatingAgentInterface;
import agent.protocol.AbstractProtocolAgent;
import helpers.LogTag;
import helpers.Logger;
import host.communication.CommunicatingHostInterface;
import message.Message;
import message.MessageInterface;
import message.implementation.DummyMessage;
import statistics.StatisticsCreator;

public class BroadcastAgent extends AbstractProtocolAgent {

	public BroadcastAgent(CommunicatingAgentInterface communicatingAgent) {
		super(communicatingAgent.getId(), communicatingAgent, communicatingAgent.getProtocol());
	}

	@Override
	public void receiveMessage(MessageInterface message) {
		super.receiveMessage(message);
		StatisticsCreator.messageSuccesfullyDelivered(message);
	}

	@Override
	public void prepareMessageTo(CommunicatingAgentInterface destinationAgent) {
		CommunicatingAgentInterface communicatingAgent = getCommunicatingAgent();
		List<CommunicatingHostInterface> allHosts = communicatingAgent.getAllHosts();
		for (CommunicatingHostInterface communicatingHostDestination : allHosts) {
			if (!communicatingHostDestination.equals(communicatingAgent.getHost())) {
				MessageInterface message = new DummyMessage(Message.noMessages, communicatingAgent.getHost(),
						communicatingHostDestination, communicatingAgent, destinationAgent);
				communicatingAgent.addMessage(message);
				StatisticsCreator.messageFailedDelivered(message);
			}
		}
		Message.noMessages++;
	}
}
