package broadcast;

import java.util.List;

import agent.communication.CommunicatingAgentInterface;
import agent.protocol.ProtocolAgent;
import helpers.LogTag;
import helpers.Logger;
import host.communication.CommunicatingHostInterface;
import host.protocol.AbstractProtocolHost;
import message.AgentCommunicationMessage;
import message.AgentCommunicationMessageInterface;
import message.MessageInterface;

/**
 * Host implementation of the Broadcast Protocol If this host does not contain
 * the destination agent of the message it discards the message
 */
public class BroadcastHost extends AbstractProtocolHost {

	/**
	 * @param communicationHost - the CommunicatingAgent that will use this protocol
	 */
	public BroadcastHost(CommunicatingHostInterface communicationHost) {
		super(communicationHost.getId(), communicationHost, communicationHost.getProtocol());
	}

	@Override
	public void interpretMessage(MessageInterface message) {
		if (message instanceof AgentCommunicationMessageInterface) {
			AgentCommunicationMessageInterface agentCommunicationMessage = (AgentCommunicationMessageInterface) message;
			int communicatingAgentId = agentCommunicationMessage.getAgentDestinationId();
			CommunicatingHostInterface communicationHost = getCommunicationHost();
			if (communicationHost.hasAgentWithId(communicatingAgentId)) {
				ProtocolAgent protocolAgent = communicationHost.getProtocolAgentWithId(communicatingAgentId);
				protocolAgent.receiveMessage(message);
			} else {
				Logger.w(LogTag.AGENT_MIGRATING, message + " arrived at " + this + " but did not find Agent " + agentCommunicationMessage.getAgentDestinationId());
			}
		}
	}

	@Override
	public void init() {
	}

	public List<Integer> getAllNormalHosts() {
		CommunicatingHostInterface communicationHost = getCommunicationHost();
		return communicationHost.getAllNormalHostsIds();
	}
}
