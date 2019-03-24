package agent.protocol;

import agent.communication.CommunicatingAgentInterface;
import protocol.Protocol;

public abstract class AbsoluteProtocolAgent implements ProtocolAgent {

	private int id;
	private CommunicatingAgentInterface communicatingAgent;
	private Protocol protocol;
	
	public AbsoluteProtocolAgent(int id, CommunicatingAgentInterface communicatingAgent, Protocol protocol) {
		this.id = id;
		this.communicatingAgent = communicatingAgent;
		this.protocol = protocol;
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
