package cs;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import agent.communication.CommunicatingAgentInterface;
import host.communication.CommunicatingHostInterface;
import host.protocol.AbstractProtocolHost;
import hss.HSSLocationUpdateMessage;
import message.AgentCommunicationMessage;
import message.AgentCommunicationMessageInterface;
import message.LocationUpdateMessage;
import message.LocationUpdateMessageInterface;
import message.MessageInterface;

public class CSServerHost extends AbstractProtocolHost {

	Map<Integer, Integer> agentToHostDatabase;

	/**
	 * @param communicationHost - the CommunicatingAgent that will use this protocol
	 */
	public CSServerHost(CommunicatingHostInterface communicationHost) {
		super(communicationHost.getId(), communicationHost, communicationHost.getProtocol());
	}

	@Override
	public void init() {
		agentToHostDatabase = new HashMap<Integer, Integer>();
	}

	@Override
	public void interpretMessage(MessageInterface message) {

		if (message instanceof AgentCommunicationMessageInterface) {
			AgentCommunicationMessageInterface agentCommunicationMessage = (AgentCommunicationMessageInterface) message;
			int agentSource = agentCommunicationMessage.getAgentSourceId();
			int agentDestination = agentCommunicationMessage.getAgentDestinationId();
			int hostDestination = agentToHostDatabase.get(agentDestination);
			CommunicatingHostInterface communicationHost = getCommunicationHost();
			AgentCommunicationMessageInterface forwardedMessage = new AgentCommunicationMessage(
					message.getMessageId(), communicationHost.getId(), hostDestination, agentSource, agentDestination);

			communicationHost.addMessageForSending(forwardedMessage);
		} else if (message instanceof LocationUpdateMessageInterface) {
			LocationUpdateMessageInterface csMessage = (LocationUpdateMessageInterface) message;
			int newInhabitingHostId = csMessage.getNewHostId();
			int agentSource = csMessage.getAgentId();
			agentToHostDatabase.put(agentSource, newInhabitingHostId);
		} else {
			super.interpretMessage(message);
		}
	}
}
