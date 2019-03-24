package host.protocol;

import java.util.List;

import agent.communication.CommunicatingAgentInterface;
import helpers.Logger;
import host.communication.CommunicatingHostInterface;
import message.MessageInterface;
import protocol.Protocol;

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
			communicatingAgent.receiveMessage(message);
		} else {
			Logger.w(message + " did non find destination " + communicatingAgent + " at " + this);
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
}
