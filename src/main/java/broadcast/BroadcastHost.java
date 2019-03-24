package broadcast;

import java.util.List;

import agent.communication.CommunicatingAgentInterface;
import host.communication.CommunicatingHostInterface;
import host.protocol.ProtocolHost;
import message.MessageInterface;

public class BroadcastHost extends ProtocolHost {

	public BroadcastHost(CommunicatingHostInterface communicationHost) {
		super(communicationHost.getId(), communicationHost, communicationHost.getProtocol());
	}
	
	@Override
	public void interpretMessage(MessageInterface message) {
		CommunicatingAgentInterface communicatingAgent = message.getAgentDestination();
		List<CommunicatingAgentInterface> communicatingAgents = getCommunicationHost().getActiveAgents();
		if(communicatingAgents.contains(communicatingAgent)) {
			communicatingAgent.receiveMessage(message);
		}
	}

}
