package shadow;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import agent.communication.CommunicatingAgentInterface;
import host.communication.CommunicatingHostInterface;
import host.protocol.AbstractProtocolHost;
import message.AgentCommunicationMessageInterface;
import message.LocationUpdateMessageInterface;
import message.MessageInterface;

/**
 * The HomeServer from the Shadow documentation
 * 
 * It keeps a map agent -> host of agent
 * 
 * Agent 0 wants to send message to Agent 1
 * Agent 0 sends message to HomeServer
 * HomeServer sends message to Host 0 from Agent 0 migration Path
 * Host 0 from Agent 0 migration Path redirects message to Host 1 from Agent 0 migration Path
 * ...
 * Host ttl from Agent 0 migration Path redirects message to HomeServer
 * 
 * if at any poInteger the agent is present in the host, the agent receives the message
 * 
 */
public class ShadowHomeServerHost extends AbstractProtocolHost {

	/**
	 * Map agent -> first Migration Stop Host Map
	 * See {@link ShadowHomeServerHost}
	 */
	Map<Integer, Integer> agentToFirstMigrationStopHostMap;

	/**
	 * @param communicationHost - the CommunicatingAgent that will use this protocol
	 */
	public ShadowHomeServerHost(CommunicatingHostInterface communicationHost) {
		super(communicationHost.getId(), communicationHost, communicationHost.getProtocol());
	}

	@Override
	public void interpretMessage(MessageInterface message) {
		if (message instanceof AgentCommunicationMessageInterface) {
			AgentCommunicationMessageInterface agentCommunicationMessage = (AgentCommunicationMessageInterface) message;
			Integer agentDestination = agentCommunicationMessage.getAgentDestinationId();
			CommunicatingHostInterface communicationHost = getCommunicationHost();
			Integer hostDestination = agentToFirstMigrationStopHostMap.get(agentDestination);
			communicationHost.reRouteMessage(agentCommunicationMessage, hostDestination);
			communicationHost.addMessageForSending(agentCommunicationMessage);
		} else if (message instanceof LocationUpdateMessageInterface){
			LocationUpdateMessageInterface locationUpdateMessage = (LocationUpdateMessageInterface) message;
			Integer agentId = locationUpdateMessage.getAgentId();
			Integer newHostId = locationUpdateMessage.getNewHostId();
			agentToFirstMigrationStopHostMap.put(agentId, newHostId);
		}

	}

	@Override
	public void init() {
		agentToFirstMigrationStopHostMap = new HashMap<Integer, Integer>();
	}
}
