package agent.protocol;

import agent.communication.CommunicatingAgentInterface;
import message.MessageInterface;
import protocol.Protocol;

public abstract class AbstractProtocolAgent implements ProtocolAgent {

	private int id;
	private CommunicatingAgentInterface communicatingAgent;
	private Protocol protocol;
	
	public AbstractProtocolAgent(int id, CommunicatingAgentInterface communicatingAgent, Protocol protocol) {
		this.id = id;
		this.communicatingAgent = communicatingAgent;
		this.protocol = protocol;
	}
	
	@Override
	public void receiveMessage(MessageInterface message) {
		communicatingAgent.receiveMessage(message);
	}

	@Override
	public CommunicatingAgentInterface getCommunicatingAgent() {
		return communicatingAgent;
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
		return protocol + " Agent " + id;
	}
}
