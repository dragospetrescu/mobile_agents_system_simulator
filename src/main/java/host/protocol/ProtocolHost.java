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

public class ProtocolHost implements ProtocolHostInterface {

	private int id;
	private CommunicatingHostInterface communicationHost;
	private Protocol protocol;
	
	public ProtocolHost(int id, CommunicatingHostInterface communicationHost, Protocol protocol) {
		super();
		this.id = id;
		this.communicationHost = communicationHost;
		this.protocol = protocol;
	}

	@Override
	public void interpretMessage(MessageInterface message) {
		CommunicatingAgentInterface communicatingAgent = message.getAgentDestination();
		List<CommunicatingAgentInterface> communicatingAgents = communicationHost.getActiveAgents();
		if(communicatingAgents.contains(communicatingAgent)) {
			ProtocolAgent protocolAgent = communicatingAgent.getProtocolAgent();
			protocolAgent.receiveMessage(message);
		} else {
			Logger.w(LogTag.NORMAL_MESSAGE, message + " did non find destination " + communicatingAgent + " at " + this);
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
