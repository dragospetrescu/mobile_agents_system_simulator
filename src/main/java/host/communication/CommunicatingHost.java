package host.communication;

import agent.communication.CommunicatingAgentInterface;
import helpers.LogTag;
import helpers.Logger;
import host.protocol.ProtocolHostInterface;
import message.MessageInterface;
import message.implementation.MigratingAgentMessage;
import protocol.Protocol;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommunicatingHost implements CommunicatingHostInterface {

	public int id;
	private List<CommunicatingAgentInterface> activeAgents;
	private Map<CommunicatingHostInterface, CommunicatingHostInterface> nextHopMap;
	private List<MessageInterface> messagesToBeSent;
	private Protocol protocol;
	private ProtocolHostInterface protocolHost;
	
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

	public void printRouting() {
		System.out.println(toString());

		for (CommunicatingHostInterface destHost : nextHopMap.keySet()) {
			System.out.println("To " + destHost + " by " + nextHopMap.get(destHost));
		}
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
	public void setProtocolHost() {
		this.protocolHost = this.protocol.getProtocolHost(this);
	}
}
