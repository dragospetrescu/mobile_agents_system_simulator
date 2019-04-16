package host.communication;

import agent.communication.CommunicatingAgentInterface;
import helpers.LogTag;
import helpers.Logger;
import host.protocol.ProtocolHost;
import message.MessageInterface;
import message.MessagesManager;
import message.implementation.MigratingAgentMessage;
import protocol.Protocol;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents the protocol-independent part of the Host. 
 * It's responsibilities are: 
 * - communicating with the {@link MessagesManager}
 * - interpreting MigratingAgentMessages
 * - keeping a list of the active agents of this host
 */
public class CommunicatingHost implements CommunicatingHostInterface {

	/**
	 * Unique identifier
	 */
	public int id;
	/**
	 * Agents that are currently residing on this host
	 */
	private List<CommunicatingAgentInterface> activeAgents;
	/**
	 * Routing table of this host
	 */
	private Map<CommunicatingHostInterface, CommunicatingHostInterface> nextHopMap;
	/**
	 * List of messages that will be sent to the {@link MessagesManager} 
	 */
	private List<MessageInterface> messagesToBeSent;
	/**
	 * TODO this will become a list of protocols
	 * List of protocols this host is implementing
	 */
	private Protocol protocol;
	/**
	 * TODO this will become a list of protocolsHosts
	 * The protocol dependent parts of the host
	 */
	private ProtocolHost protocolHost;
	private List<CommunicatingHostInterface> allHosts;
	
	/**
	 * TODO add parameters
	 */
	public CommunicatingHost() {
		activeAgents = new ArrayList<CommunicatingAgentInterface>();
		nextHopMap = new HashMap<CommunicatingHostInterface, CommunicatingHostInterface>();
		messagesToBeSent = new ArrayList<MessageInterface>();
	}

	public List<CommunicatingAgentInterface> getActiveAgents() {
		return activeAgents;
	}

	public void addAgent(CommunicatingAgentInterface agent) {
		activeAgents.add(agent);
		agent.setHost(this);
		agent.setWork();
	}

	public void addRouteNextHop(CommunicatingHostInterface destinationRouter,
			CommunicatingHostInterface nextHopRouter) {
		nextHopMap.put(destinationRouter, nextHopRouter);
	}

	@Override
	public String toString() {
		return "Host " + id;
	}

	public int getId() {
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
		CommunicatingHostInterface messageDestination = message.getHostDestination();
		if (messageDestination.equals(this)) {
			if (message instanceof MigratingAgentMessage) {
				MigratingAgentMessage migratingAgentMessage = (MigratingAgentMessage) message;
				CommunicatingAgentInterface migratingAgent = migratingAgentMessage.getMigratingAgent();
				addAgent(migratingAgent);
				Logger.i(LogTag.AGENT_MIGRATING, toString() + " received " + migratingAgent);
			} else {
				protocolHost.interpretMessage(message);
			}
		} else {
			CommunicatingHostInterface communicatingHostInterface = nextHopMap.get(messageDestination);
			message.setNextHopHost(communicatingHostInterface);
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
	public CommunicatingHostInterface getNextHop(CommunicatingHostInterface destinationHost) {
		return nextHopMap.get(destinationHost);
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
		this.protocolHost.init();
	}
	
	@Override
	public void init(List<CommunicatingHostInterface> allHosts) {
		this.protocolHost = this.protocol.getProtocolHost(this);
		this.allHosts = allHosts;
	}
	
	@Override
	public List<CommunicatingHostInterface> getAllHosts() {
		return allHosts;
	}
	
	@Override
	public CommunicatingHostInterface getHostById(int homeServerHostId) {
		for (CommunicatingHostInterface host : allHosts) {
			if(homeServerHostId == host.getId())
				return host;
		}
		throw new RuntimeException("Host with id " + homeServerHostId + " not found!");
	}
}
