package hss;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import agent.communication.CommunicatingAgentInterface;
import host.communication.CommunicatingHostInterface;
import host.protocol.AbstractProtocolHost;
import message.MessageInterface;
import message.implementation.NormalCommunicationMessage;

/**
 * The HomeServer from the HSS documentation
 * 
 * It keeps a map agent -> host location
 * 
 * When agent migrates it sends to it's home host message
 * that updates the location database
 */
public class HSSHomeAgentHost extends AbstractProtocolHost {

	/**
	 * Map agent to current location
	 * It is updated every time agent migrates
	 */
	Map<CommunicatingAgentInterface, CommunicatingHostInterface> agentToAddressDatabase;

	/**
	 * @param communicationHost - the CommunicatingAgent that will use this protocol
	 */
	public HSSHomeAgentHost(CommunicatingHostInterface communicationHost) {
		super(communicationHost.getId(), communicationHost, communicationHost.getProtocol());
	}

	@Override
	public void interpretMessage(MessageInterface message) {
		if (message instanceof HSSForwardedMessage) {
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
		} else if (message instanceof HSSLocationUpdateMessage) {
			HSSLocationUpdateMessage hssMessage = (HSSLocationUpdateMessage) message;
			CommunicatingHostInterface newInhabitingHost = hssMessage.getNewHostLocation();
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