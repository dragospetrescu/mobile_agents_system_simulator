package shadow;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import agent.communication.CommunicatingAgentInterface;
import host.communication.CommunicatingHostInterface;
import host.protocol.AbstractProtocolHost;
import message.MessageInterface;
import message.implementation.NormalCommunicationMessage;

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
 * if at any point the agent is present in the host, the agent receives the message
 * 
 */
public class ShadowHomeServerHost extends AbstractProtocolHost {

	/**
	 * Map agent -> first Migration Stop Host Map
	 * See {@link ShadowHomeServerHost}
	 */
	Map<CommunicatingAgentInterface, CommunicatingHostInterface> agentToFirstMigrationStopHostMap;

	/**
	 * @param communicationHost - the CommunicatingAgent that will use this protocol
	 */
	public ShadowHomeServerHost(CommunicatingHostInterface communicationHost) {
		super(communicationHost.getId(), communicationHost, communicationHost.getProtocol());
	}

	@Override
	public void interpretMessage(MessageInterface message) {
		if(message instanceof ShadowLocationUpdateMessage) {
			ShadowLocationUpdateMessage shadowMessage = (ShadowLocationUpdateMessage) message;
			CommunicatingHostInterface migratingToHost = shadowMessage.getMigratingToHost();
			CommunicatingAgentInterface agentSource = shadowMessage.getAgentSource();
			agentToFirstMigrationStopHostMap.put(agentSource, migratingToHost);
		}
		if(message instanceof ShadowForwardedMessage) {
			CommunicatingAgentInterface destinationAgent = message.getAgentDestination();
			CommunicatingHostInterface newHostDestination = agentToFirstMigrationStopHostMap.get(destinationAgent);
			CommunicatingHostInterface sourceHost = getCommunicationHost();
			CommunicatingAgentInterface sourceAgent = message.getAgentSource();
			MessageInterface forwardedMessage = new NormalCommunicationMessage(message.getId(), sourceHost,
					newHostDestination, sourceAgent, destinationAgent);
			sourceHost.addMessageForSending(forwardedMessage);
		}

	}

	@Override
	public void init() {
		agentToFirstMigrationStopHostMap = new HashMap<CommunicatingAgentInterface, CommunicatingHostInterface>();
		List<CommunicatingHostInterface> allHosts = getCommunicationHost().getAllNormalHosts();
		for (CommunicatingHostInterface communicatingHostInterface : allHosts) {
			List<CommunicatingAgentInterface> activeAgents = communicatingHostInterface.getActiveAgents();
			for (CommunicatingAgentInterface activeAgent : activeAgents) {
				agentToFirstMigrationStopHostMap.put(activeAgent, communicatingHostInterface);
			}
		}
	}
}
