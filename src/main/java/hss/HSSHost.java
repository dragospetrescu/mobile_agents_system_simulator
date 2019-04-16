package hss;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import agent.communication.CommunicatingAgentInterface;
import host.communication.CommunicatingHostInterface;
import host.protocol.AbstractProtocolHost;
import message.MessageInterface;
import message.implementation.NormalCommunicationMessage;

public class HSSHost extends AbstractProtocolHost {

	Map<CommunicatingAgentInterface, CommunicatingHostInterface> agentToAddressDatabase;

	public HSSHost(CommunicatingHostInterface communicationHost) {
		super(communicationHost.getId(), communicationHost, communicationHost.getProtocol());
	}

	@Override
	public void interpretMessage(MessageInterface message) {
		if (message instanceof HSSForwardedMessage) {
			HSSForwardedMessage hssMessage = (HSSForwardedMessage) message;
			CommunicatingAgentInterface agentDestination = message.getAgentDestination();
			CommunicatingHostInterface communicationHost = getCommunicationHost();
			CommunicatingHostInterface hostDestination = agentToAddressDatabase.get(agentDestination);
			MessageInterface forwardedNormalMessage = new NormalCommunicationMessage(message.getId(), communicationHost, hostDestination,
					message.getAgentSource(), message.getAgentDestination());
			if(hostDestination.equals(communicationHost)) {
				interpretMessage(forwardedNormalMessage);
			} else {
				communicationHost.addMessageForSending(forwardedNormalMessage);
			}
		} else if (message instanceof HSSMigratingMessage) {
			HSSMigratingMessage hssMessage = (HSSMigratingMessage) message;
			CommunicatingHostInterface newInhabitingHost = hssMessage.getNewInhabitingHost();
			CommunicatingAgentInterface agentSource = hssMessage.getAgentSource();
			agentToAddressDatabase.put(agentSource, newInhabitingHost);
		} else {
			super.interpretMessage(message);
		}
	}

	@Override
	public void init() {
		agentToAddressDatabase = new HashMap<CommunicatingAgentInterface, CommunicatingHostInterface>();
		CommunicatingHostInterface communicationHost = getCommunicationHost();
		List<CommunicatingAgentInterface> activeAgents = communicationHost.getActiveAgents();
		for (CommunicatingAgentInterface communicatingAgentInterface : activeAgents) {
			agentToAddressDatabase.put(communicatingAgentInterface, communicationHost);
		}
	}

}
