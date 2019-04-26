package hss;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import agent.communication.CommunicatingAgentInterface;
import host.communication.CommunicatingHostInterface;
import host.protocol.AbstractProtocolHost;
import message.AgentCommunicationMessageInterface;

/**
 * The HomeServer from the HSS documentation
 * 
 * It keeps a map agent -> home agent
 * 
 * Agent 0 wants to send message to Agent 1
 * Agent 0 sends message to HomeServer
 * HomeServer sends message to Agent 1 Home Agent
 * Home Agent sends message location of the Agent
 * 
 */
public class HSSHomeServerHost extends AbstractProtocolHost {

	/**
	 * Map agent -> home agent
	 * See {@link HSSHomeAgentHost}
	 */
	Map<CommunicatingAgentInterface, CommunicatingHostInterface> agentToHomeDatabase;

	/**
	 * @param communicationHost - the CommunicatingAgent that will use this protocol
	 */
	public HSSHomeServerHost(CommunicatingHostInterface communicationHost) {
		super(communicationHost.getId(), communicationHost, communicationHost.getProtocol());
	}

	@Override
	public void interpretMessage(AgentCommunicationMessageInterface message) {

		if (message instanceof HSSForwardedMessage) {
			HSSForwardedMessage hssMessage = (HSSForwardedMessage) message;
			CommunicatingAgentInterface agentSource = message.getAgentSourceId();
			CommunicatingAgentInterface agentDestination = message.getAgentDestinationId();
			CommunicatingHostInterface communicationHost = getCommunicationHost();
			CommunicatingHostInterface hostDestination = agentToHomeDatabase.get(agentDestination);
			HSSForwardedMessage forwardedMessage = new HSSForwardedMessage(hssMessage.getMessageId(), communicationHost,
					hostDestination, agentSource, agentDestination);

			communicationHost.addMessageForSending(forwardedMessage);
		} else {
			super.interpretMessage(message);
		}
	}

	@Override
	public void init() {
		agentToHomeDatabase = new HashMap<CommunicatingAgentInterface, CommunicatingHostInterface>();
		List<CommunicatingHostInterface> allHosts = getCommunicationHost().getAllNormalHosts();
		for (CommunicatingHostInterface communicatingHostInterface : allHosts) {
			List<CommunicatingAgentInterface> activeAgents = communicatingHostInterface.getActiveAgents();
			for (CommunicatingAgentInterface activeAgent : activeAgents) {
				agentToHomeDatabase.put(activeAgent, communicatingHostInterface);
			}
		}
	}
}
