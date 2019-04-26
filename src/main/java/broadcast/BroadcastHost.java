package broadcast;

import java.util.List;

import agent.communication.CommunicatingAgentInterface;
import agent.protocol.ProtocolAgent;
import host.communication.CommunicatingHostInterface;
import host.protocol.AbstractProtocolHost;
import message.AgentCommunicationMessageInterface;

/**
 * Host implementation of the Broadcast Protocol 
 * If this host does not contain the destination 
 * agent of the message it discards the message
 */
public class BroadcastHost extends AbstractProtocolHost {

	/**
	 * @param communicationHost - the CommunicatingAgent that will use this protocol
	 */
	public BroadcastHost(CommunicatingHostInterface communicationHost) {
		super(communicationHost.getId(), communicationHost, communicationHost.getProtocol());
	}

	@Override
	public void interpretMessage(AgentCommunicationMessageInterface message) {
		CommunicatingAgentInterface communicatingAgent = message.getAgentDestinationId();
		List<CommunicatingAgentInterface> communicatingAgents = getCommunicationHost().getActiveAgents();
		if (communicatingAgents.contains(communicatingAgent)) {
			ProtocolAgent protocolAgent = communicatingAgent.getProtocolAgent();
			protocolAgent.receiveMessage(message);
		}
	}
	
	@Override
	public void init() {
	}
}
