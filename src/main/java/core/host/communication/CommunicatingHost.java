package core.host.communication;

import core.agent.communication.CommunicatingAgentInterface;
import core.agent.protocol.ProtocolAgent;
import core.host.protocol.ProtocolHost;
import core.message.MessageInterface;
import core.message.MessagesManager;
import protocol.Protocol;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents the protocol-independent part of the Host. It's responsibilities
 * are: - communicating with the {@link MessagesManager} - interpreting
 * MigratingAgentMessages - keeping a list of the active agents of this host
 */
public class CommunicatingHost implements CommunicatingHostInterface {

	/**
	 * Unique identifier
	 */
	public Integer id;
	/**
	 * Agents that are currently residing on this host
	 */
	private Map<Integer, CommunicatingAgentInterface> activeAgentsMap;
	/**
	 * Routing table of this host
	 */
	private Map<Integer, Integer> nextHopMap;
	/**
	 * List of messages that will be sent to the {@link MessagesManager}
	 */
	private List<MessageInterface> messagesToBeSent;
	/**
	 * TODO this will become a list of protocols List of protocols this host is
	 * implementing
	 */
	private Protocol protocol;
	/**
	 * TODO this will become a list of protocolsHosts The protocol dependent parts
	 * of the host
	 */
	private ProtocolHost protocolHost;
	/**
	 * Hosts to which agents can migrate
	 */
	private List<Integer> normalHosts;
	
	private Map<String, String> protocolArguments;

	/**
	 * TODO add parameters
	 */
	public CommunicatingHost() {
		activeAgentsMap = new HashMap<Integer, CommunicatingAgentInterface>();
		nextHopMap = new HashMap<Integer, Integer>();
		messagesToBeSent = new ArrayList<MessageInterface>();
	}

	@Override
	public Collection<CommunicatingAgentInterface> getActiveAgents() {
		return activeAgentsMap.values();
	}

	public void addAgent(CommunicatingAgentInterface agent) {
		activeAgentsMap.put(agent.getId(), agent);
		agent.setHost(this);
		agent.setWork();
	}

	public void addRouteNextHop(Integer destinationRouterId, Integer nextHopRouterId) {
		nextHopMap.put(destinationRouterId, nextHopRouterId);
	}

	@Override
	public String toString() {
		return "Host " + id;
	}

	public Integer getId() {
		return id;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		CommunicatingHostInterface host = (CommunicatingHostInterface) o;

		return id == host.getId();
	}

	@Override
	public int hashCode() {
		return id;
	}

	@Override
	public void receiveMessage(MessageInterface message) {
		Integer messageDestinationHostId = message.getHostDestinationId();
		if (messageDestinationHostId == getId()) {
			protocolHost.interpretMessage(message);
		} else {
			routeMessage(message);
			addMessageForSending(message);
		}
	}

	@Override
	public List<MessageInterface> sendMessages() {
		List<MessageInterface> messages = messagesToBeSent;
		messagesToBeSent = new ArrayList<MessageInterface>();
		return messages;
	}

	@Override
	public boolean wantsToSendMessage() {
		return !messagesToBeSent.isEmpty();
	}

	@Override
	public Protocol getProtocol() {
		return protocol;
	}

	@Override
	public void addMessageForSending(MessageInterface message) {
		messagesToBeSent.add(message);
	}

	@Override
	public void initProtocol() {
		this.protocolHost.init(protocolArguments);
	}

	@Override
	public void init(List<Integer> normalHosts) {
		this.protocolHost = this.protocol.getProtocolHost(this);
		this.normalHosts = normalHosts;
	}

	@Override
	public List<Integer> getAllNormalHostsIds() {
		return normalHosts;
	}

	@Override
	public ProtocolHost getProtocolHost(Protocol protocol) {
		return protocolHost;
	}
	
	@Override
	public boolean hasAgentWithId(Integer communicatingAgentId) {
		return activeAgentsMap.containsKey(communicatingAgentId);
	}
	
	@Override
	public ProtocolAgent getProtocolAgentWithId(Integer communicatingAgentId) {
		if(!hasAgentWithId(communicatingAgentId)) {
			throw new RuntimeException("There is no protocol agent with id " + communicatingAgentId + " on " + toString());
		}
		
		return activeAgentsMap.get(communicatingAgentId).getProtocolAgent();
	}

	@Override
	public void routeMessage(MessageInterface message) {
		Integer hostDestinationId = message.getHostDestinationId();
		Integer nextHopHostId = nextHopMap.get(hostDestinationId);
		
		message.route(nextHopHostId);
		
	}

	@Override
	public void reRouteMessage(MessageInterface message, Integer newDestinationHostId) {
		Integer nextHopHostId = nextHopMap.get(newDestinationHostId);
		message.route(nextHopHostId, newDestinationHostId);
	}
}
