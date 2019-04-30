package hss;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import core.agent.communication.CommunicatingAgentInterface;
import core.host.communication.CommunicatingHostInterface;
import core.host.protocol.AbstractProtocolHost;
import core.message.AgentCommunicationMessageInterface;
import core.message.LocationUpdateMessage;
import core.message.LocationUpdateMessageInterface;
import core.message.MessageInterface;

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
	Map<Integer, Integer> agentToHomeDatabase;

	/**
	 * @param communicationHost - the CommunicatingAgent that will use this protocol
	 */
	public HSSHomeServerHost(CommunicatingHostInterface communicationHost) {
		super(communicationHost.getId(), communicationHost, communicationHost.getProtocol());
	}

	@Override
	public void interpretMessage(MessageInterface message) {

		if (message instanceof AgentCommunicationMessageInterface) {
			AgentCommunicationMessageInterface agentCommunicationMessage = (AgentCommunicationMessageInterface) message;
			Integer agentDestination = agentCommunicationMessage.getAgentDestinationId();
			CommunicatingHostInterface communicationHost = getCommunicationHost();
			Integer hostDestination = agentToHomeDatabase.get(agentDestination);
			communicationHost.reRouteMessage(agentCommunicationMessage, hostDestination);
			communicationHost.addMessageForSending(agentCommunicationMessage);
		} else if (message instanceof LocationUpdateMessageInterface){
			LocationUpdateMessageInterface locationUpdateMessage = (LocationUpdateMessageInterface) message;
			Integer agentId = locationUpdateMessage.getAgentId();
			Integer newHostId = locationUpdateMessage.getNewHostId();
			agentToHomeDatabase.put(agentId, newHostId);
		}
	}

	@Override
	public void init() {
		agentToHomeDatabase = new HashMap<Integer, Integer>();
	}
}
