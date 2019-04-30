package cs;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import core.agent.communication.CommunicatingAgentInterface;
import core.host.communication.CommunicatingHostInterface;
import core.host.protocol.AbstractProtocolHost;
import core.message.AgentCommunicationMessage;
import core.message.AgentCommunicationMessageInterface;
import core.message.LocationUpdateMessage;
import core.message.LocationUpdateMessageInterface;
import core.message.MessageInterface;

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
			Integer agentSource = agentCommunicationMessage.getAgentSourceId();
			Integer agentDestination = agentCommunicationMessage.getAgentDestinationId();
			Integer hostDestination = agentToHostDatabase.get(agentDestination);
			CommunicatingHostInterface communicationHost = getCommunicationHost();
			AgentCommunicationMessageInterface forwardedMessage = new AgentCommunicationMessage(
					message.getMessageId(), communicationHost.getId(), hostDestination, agentSource, agentDestination);
			communicationHost.routeMessage(forwardedMessage);
			communicationHost.addMessageForSending(forwardedMessage);
		} else if (message instanceof LocationUpdateMessageInterface) {
			LocationUpdateMessageInterface csMessage = (LocationUpdateMessageInterface) message;
			Integer newInhabitingHostId = csMessage.getNewHostId();
			Integer agentSource = csMessage.getAgentId();
			agentToHostDatabase.put(agentSource, newInhabitingHostId);
		} else {
			super.interpretMessage(message);
		}
	}
}
