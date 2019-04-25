package cs;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import agent.communication.CommunicatingAgentInterface;
import host.communication.CommunicatingHostInterface;
import host.protocol.AbstractProtocolHost;
import hss.HSSLocationUpdateMessage;
import message.MessageInterface;
import message.implementation.NormalCommunicationMessage;

public class CSServerHost extends AbstractProtocolHost {

	Map<CommunicatingAgentInterface, CommunicatingHostInterface> agentToHostDatabase;
	
	/**
	 * @param communicationHost - the CommunicatingAgent that will use this protocol
	 */
	public CSServerHost(CommunicatingHostInterface communicationHost) {
		super(communicationHost.getId(), communicationHost, communicationHost.getProtocol());
	}

	@Override
	public void init() {
		agentToHostDatabase = new HashMap<CommunicatingAgentInterface, CommunicatingHostInterface>();
		List<CommunicatingHostInterface> allHosts = getCommunicationHost().getAllNormalHosts();
		for (CommunicatingHostInterface communicatingHostInterface : allHosts) {
			List<CommunicatingAgentInterface> activeAgents = communicatingHostInterface.getActiveAgents();
			for (CommunicatingAgentInterface activeAgent : activeAgents) {
				agentToHostDatabase.put(activeAgent, communicatingHostInterface);
			}
		}
	}
	
	@Override
	public void interpretMessage(MessageInterface message) {

		if (message instanceof NormalCommunicationMessage) {
			CommunicatingAgentInterface agentSource = message.getAgentSource();
			CommunicatingAgentInterface agentDestination = message.getAgentDestination();
			CommunicatingHostInterface hostSource = getCommunicationHost();
			CommunicatingHostInterface hostDestination = agentToHostDatabase.get(agentDestination);
			NormalCommunicationMessage forwardedMessage = new NormalCommunicationMessage(message.getId(), hostSource,
					hostDestination, agentSource, agentDestination);

			hostSource.addMessageForSending(forwardedMessage);
		} else if (message instanceof CSLocationUpdateMessage) {
			CSLocationUpdateMessage hssMessage = (CSLocationUpdateMessage) message;
			CommunicatingHostInterface newInhabitingHost = hssMessage.getNewHostLocation();
			CommunicatingAgentInterface agentSource = hssMessage.getAgentSource();
			agentToHostDatabase.put(agentSource, newInhabitingHost);
		} else {
			super.interpretMessage(message);
		}
	}
}
