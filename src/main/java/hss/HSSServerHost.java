package hss;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import agent.communication.CommunicatingAgentInterface;
import host.communication.CommunicatingHostInterface;
import host.protocol.AbstractProtocolHost;
import message.MessageInterface;

public class HSSServerHost extends AbstractProtocolHost {

	Map<CommunicatingAgentInterface, CommunicatingHostInterface> agentToHomeDatabase;

	public HSSServerHost(CommunicatingHostInterface communicationHost) {
		super(communicationHost.getId(), communicationHost, communicationHost.getProtocol());
	}

	@Override
	public void interpretMessage(MessageInterface message) {

		if (message instanceof HSSForwardedMessage) {
			HSSForwardedMessage hssMessage = (HSSForwardedMessage) message;
			CommunicatingAgentInterface agentSource = message.getAgentSource();
			CommunicatingAgentInterface agentDestination = message.getAgentDestination();
			CommunicatingHostInterface communicationHost = getCommunicationHost();
			CommunicatingHostInterface hostDestination = agentToHomeDatabase.get(agentDestination);
			HSSForwardedMessage forwardedMessage = new HSSForwardedMessage(hssMessage.getId(), communicationHost,
					hostDestination, agentSource, agentDestination);

			communicationHost.addMessageForSending(forwardedMessage);
		} else {
			super.interpretMessage(message);
		}
	}

	@Override
	public void init() {
		agentToHomeDatabase = new HashMap<CommunicatingAgentInterface, CommunicatingHostInterface>();
		List<CommunicatingHostInterface> allHosts = getCommunicationHost().getAllHosts();
		for (CommunicatingHostInterface communicatingHostInterface : allHosts) {
			List<CommunicatingAgentInterface> activeAgents = communicatingHostInterface.getActiveAgents();
			for (CommunicatingAgentInterface activeAgent : activeAgents) {
				agentToHomeDatabase.put(activeAgent, communicatingHostInterface);
			}
		}
	}
}
