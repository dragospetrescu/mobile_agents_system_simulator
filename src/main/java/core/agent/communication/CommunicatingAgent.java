package core.agent.communication;

import protocol.Protocol;

import java.util.List;
import java.util.Map;

import core.agent.protocol.ProtocolAgent;
import core.helpers.LogTag;
import core.helpers.Logger;
import core.helpers.RandomAssigner;
import core.host.communication.CommunicatingHost;
import core.host.communication.CommunicatingHostInterface;
import core.message.MessageInterface;
import core.message.MigratingAgentMessage;
import core.statistics.StatisticsCreator;

/**
 * Represents the protocol-independent part of the Agent. It's responsibilities
 * are: - migrating - calling the protocol to send messages to a certain agent -
 * giving the messages to the host - create statistics about received messages
 */
public class CommunicatingAgent implements CommunicatingAgentInterface {

	/**
	 * Unique identifier
	 */
	private Integer id;
	/**
	 * Unique host identifier
	 */
	private Integer hostId;
	/**
	 * The host the CommunicatingAgent is currently inhabiting
	 */
	private CommunicatingHostInterface host;
	/**
	 * All agents available in the simulation
	 */
	private List<Integer> allAgentsIds;
	/**
	 * The turns left until the next migration
	 */
	private Integer work;
	/**
	 * The communication protocol this agent is currently using
	 */
	private Protocol protocol;
	/**
	 * The protocol dependent part which handles messages creation and finding the
	 * host destination of an agent destination. Should always be in sync with
	 * protocol
	 */
	private ProtocolAgent protocolAgent;
	/**
	 * Extra arguments for the protocol
	 */
	private Map<String, String> protocolArguments;
	/**
	 * At the simulation end an agent should continue to travel but it should not
	 * send new messages because they might not have enough time to reach
	 * destination and these will ruin statistics
	 */
	private boolean shouldSendMessages;

	/**
	 * TODO add parameters
	 */
	public CommunicatingAgent() {
		shouldSendMessages = true;
	}

	@Override
	public void work() {
		if (work % 10 == 0 && shouldSendMessages) {
			Integer destinationAgentId = RandomAssigner.getRandomElement(allAgentsIds, getId());
			Logger.i(LogTag.NORMAL_MESSAGE, this + " sending message to Agent " + destinationAgentId);
			protocolAgent.prepareMessageTo(destinationAgentId);
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
	public void prepareMigratingMessage() {
		List<Integer> normalHosts = host.getAllNormalHostsIds();
		Integer destinationHostId = RandomAssigner.getRandomElement(normalHosts, hostId);
		Logger.i(LogTag.AGENT_MIGRATING, toString() + " traveling from " + getHost() + " to Host " + destinationHostId);
		MigratingAgentMessage message = new MigratingAgentMessage(getHost().getId(), destinationHostId, this);
		protocolAgent.migrate(destinationHostId, message);

	}

	@Override
	public void receiveMessage(MessageInterface message) {
		StatisticsCreator statistics = StatisticsCreator.getStatistics();
		statistics.messageSuccesfullyDelivered(message);
		Logger.i(LogTag.NORMAL_MESSAGE, this + " received " + message);
	}

	@Override
	public Protocol getProtocol() {
		return protocol;
	}

	@Override
	public Integer getId() {
		return id;
	}

	@Override
	public void initAgent(List<Integer> allAgentsIds, CommunicatingHostInterface host) {
		this.allAgentsIds = allAgentsIds;
		protocolAgent = protocol.getProtocolAgent(this);
		this.host = host;
		this.host.addAgent(this); // TODO REMOVE THIS
	}

	@Override
	public void setHost(CommunicatingHost host) {
		this.host = host;
		this.hostId = host.getId();
	}

	@Override
	public void setWork() {
		work = RandomAssigner.assignWork();
	}

	@Override
	public ProtocolAgent getProtocolAgent() {
		return protocolAgent;
	}

	@Override
	public void initProtocol() {
		protocolAgent.init(protocolArguments, host.getProtocolHost(protocol));
	}

	@Override
	public Integer getHostId() {
		return hostId;
	}

	@Override
	public void stopCreatingNewMessages() {
		shouldSendMessages = false;
	}
}
