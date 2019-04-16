package agent.communication;

import helpers.LogTag;
import helpers.Logger;
import helpers.RandomAssigner;
import host.communication.CommunicatingHost;
import host.communication.CommunicatingHostInterface;
import message.MessageInterface;
import message.implementation.MigratingAgentMessage;
import protocol.Protocol;
import statistics.StatisticsCreator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import agent.protocol.ProtocolAgent;

/**
 * Represents the protocol-independent part of the Agent. It's responsibilities
 * are: - migrating - calling the protocol to send messages to a certain agent -
 * giving the messages to the host - create statistics about received messages
 */
public class CommunicatingAgent implements CommunicatingAgentInterface {

	/**
	 * Unique identifier
	 */
	private int id;
	/**
	 * Unique identifier of the host the CommunicatingAgent is currently inhabiting
	 */
	private int hostId;
	/**
	 * The host the CommunicatingAgent is currently inhabiting
	 */
	private CommunicatingHostInterface host;
	/**
	 * All hosts available in the simulation
	 */
	private List<CommunicatingHostInterface> allHosts;
	/**
	 * All agents available in the simulation
	 */
	private List<CommunicatingAgentInterface> allAgents;
	/**
	 * The turns left until the next migration
	 */
	private int work;
	/**
	 * The communication protocol this agent is currently using
	 */
	private Protocol protocol;
	/**
	 * The protocol dependent part which handles messages creation and finding the
	 * host destination of an agent destination. Should always be in sync with
	 * protocol
	 */
	private ProtocolAgent agentProtocol;
	/**
	 * List of messages to be delivered to be sent
	 */
	private List<MessageInterface> messages;
	private Map<String, String> protocolArguments;

	/**
	 * TODO add parameters
	 */
	public CommunicatingAgent() {
		messages = new ArrayList<MessageInterface>();
	}

	@Override
	public void work() {
		if (work % 10 == 0) {
			agentProtocol
					.prepareMessageTo(RandomAssigner.getRandomElement(allAgents, ((CommunicatingAgentInterface) this)));
		}
		work--;
	}

	@Override
	public boolean wantsToMigrate() {
		return work <= 0;
	}

	@Override
	public CommunicatingHostInterface getHost() {
		return host;
	}

	@Override
	public String toString() {
		return "Agent " + id;
	}

	@Override
	public MessageInterface prepareMigratingMessage() {
		CommunicatingHostInterface destinationHost = RandomAssigner.getRandomElement(allHosts, host);
		Logger.i(LogTag.AGENT_MIGRATING, toString() + " traveling from " + getHost() + " to " + destinationHost);
		agentProtocol.migrate(destinationHost);
		
		return new MigratingAgentMessage(getHost(), destinationHost, this);
	}

	@Override
	public boolean wantsToSendMessage() {
		return !messages.isEmpty();
	}

	@Override
	public List<MessageInterface> sendMessages() {
		List<MessageInterface> messagesToBeSent = messages;
		messages = new ArrayList<MessageInterface>();
		return messagesToBeSent;
	}

	@Override
	public void receiveMessage(MessageInterface message) {
		StatisticsCreator.messageSuccesfullyDelivered(message);
		Logger.i(LogTag.NORMAL_MESSAGE, this + " received " + message);
	}

	@Override
	public void addMessage(MessageInterface message) {
		messages.add(message);
	}

	@Override
	public Protocol getProtocol() {
		return protocol;
	}

	@Override
	public int getId() {
		return id;
	}

	@Override
	public void initAgent(List<CommunicatingAgentInterface> allAgents, List<CommunicatingHostInterface> allHosts) {
		this.allAgents = allAgents;
		this.allHosts = allHosts;
		agentProtocol = protocol.getProtocolAgent(this);

		for (CommunicatingHostInterface host : allHosts) {
			if (host.getId() == hostId) {
				this.host = host;
				break;
			}
		}
		if (this.host == null)
			throw new RuntimeException("Can't find host with id " + hostId);
		this.host.addAgent(this);

	}

	@Override
	public void setHost(CommunicatingHost host) {
		this.host = host;
	}

	@Override
	public List<CommunicatingHostInterface> getAllHosts() {
		return allHosts;
	}

	@Override
	public void setWork() {
		work = RandomAssigner.assignWork();
	}

	@Override
	public ProtocolAgent getProtocolAgent() {
		return agentProtocol;
	}

	@Override
	public void initProtocol() {
		agentProtocol.init(protocolArguments);
	}
}
