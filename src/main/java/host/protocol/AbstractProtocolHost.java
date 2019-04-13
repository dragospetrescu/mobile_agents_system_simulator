package host.protocol;

import java.util.List;

import agent.communication.CommunicatingAgentInterface;
import agent.protocol.ProtocolAgent;
import helpers.LogTag;
import helpers.Logger;
import host.communication.CommunicatingHostInterface;
import message.MessageInterface;
import protocol.Protocol;
import statistics.StatisticsCreator;

/**
 * ProtocolHosts can extend this class.
 */
public abstract class AbstractProtocolHost implements ProtocolHost {

	/**
	 * Unique identifier
	 */
	private int id;
	/**
	 * The {@link CommunicatingHostInterface} that is currently using this
	 * ProtocolHost
	 */
	private CommunicatingHostInterface communicationHost;
	/**
	 * The protocol that it is implementing
	 */
	private Protocol protocol;

	/**
	 * @param id - Unique identifier
	 * @param communicationHost - The {@link CommunicatingHostInterface} that will use this ProtocolHost
	 * @param protocol - The protocol that it is implementing
	 */
	public AbstractProtocolHost(int id, CommunicatingHostInterface communicationHost, Protocol protocol) {
		super();
		this.id = id;
		this.communicationHost = communicationHost;
		this.protocol = protocol;
	}

	@Override
	public void interpretMessage(MessageInterface message) {
		CommunicatingAgentInterface communicatingAgent = message.getAgentDestination();
		List<CommunicatingAgentInterface> communicatingAgents = communicationHost.getActiveAgents();
		if (communicatingAgents.contains(communicatingAgent)) {
			ProtocolAgent protocolAgent = communicatingAgent.getProtocolAgent();
			protocolAgent.receiveMessage(message);
		} else {
			Logger.w(LogTag.NORMAL_MESSAGE,
					message + " did non find destination " + communicatingAgent + " at " + this);
			StatisticsCreator.messageFailedDelivered(message);
		}
	}

	@Override
	public CommunicatingHostInterface getCommunicationHost() {
		return communicationHost;
	}

	@Override
	public int getId() {
		return id;
	}

	@Override
	public Protocol getProtocol() {
		return protocol;
	}

	@Override
	public String toString() {
		return protocol + " HOST " + id;
	}
}
