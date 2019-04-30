package shadow;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import host.communication.CommunicatingHostInterface;
import host.protocol.AbstractProtocolHost;
import message.AgentCommunicationMessageInterface;
import message.LocationUpdateMessageInterface;
import message.MessageInterface;

/**
 * The ShadowHost from the Shadow documentation
 * 
 * It keeps a map agent -> host location
 * 
 * When agent migrates it keeps remembers where that agent left to so any
 * message can follow the exact path of the agent.
 */
public class ShadowHost extends AbstractProtocolHost {
	/**
	 * Map agent to the host to which the agent migrated after it left this host
	 */
	private Map<Integer, Integer> agentForwardingProxy;

	/**
	 * @param communicationHost - the CommunicatingAgent that will use this protocol
	 */
	public ShadowHost(CommunicatingHostInterface communicationHost) {
		super(communicationHost.getId(), communicationHost, communicationHost.getProtocol());
	}

	@Override
	public void init() {
		agentForwardingProxy = new HashMap<Integer, Integer>();
	}

	@Override
	public void interpretMessage(MessageInterface message) {
		if (message instanceof AgentCommunicationMessageInterface) {
			AgentCommunicationMessageInterface agentCommunicationMessage = (AgentCommunicationMessageInterface) message;
			Integer agentDestinationId = agentCommunicationMessage.getAgentDestinationId();
			CommunicatingHostInterface communicationHost = getCommunicationHost();

			if (agentForwardingProxy.containsKey(agentDestinationId)) {
				Integer hostDestination = agentForwardingProxy.get(agentDestinationId);
				if (!hostDestination.equals(communicationHost.getId())) {
					communicationHost.reRouteMessage(agentCommunicationMessage, hostDestination);
					communicationHost.addMessageForSending(agentCommunicationMessage);
					return;
				}
			}
			super.interpretMessage(agentCommunicationMessage);

		} else if (message instanceof LocationUpdateMessageInterface) {
			LocationUpdateMessageInterface locationUpdateMessage = (LocationUpdateMessageInterface) message;
			Integer newInhabitingHostId = locationUpdateMessage.getNewHostId();
			Integer agentSourceId = locationUpdateMessage.getAgentId();
			agentForwardingProxy.put(agentSourceId, newInhabitingHostId);
		} else {
			super.interpretMessage(message);
		}
	}

	/**
	 * @param agent we are currently trying to find
	 * @return the host to which the agent migrated when it left this host
	 */
	public Integer getProxy(Integer agent) {
		return agentForwardingProxy.get(agent);
	}

	/**
	 * Agent is going to migrate to host so it leaves behind some breadcrumbs so the
	 * messages can follow him.
	 * 
	 * @param agent - migrating agent
	 * @param host  - new host of agent
	 */
	public void updateProxy(Integer agent, Integer host) {
		agentForwardingProxy.put(agent, host);
	}

	public List<Integer> getAllNormalHosts() {
		CommunicatingHostInterface communicationHost = getCommunicationHost();
		return communicationHost.getAllNormalHostsIds();
	}
}
