package mefs;

import core.message.AgentCommunicationMessage;
@SuppressWarnings("javadoc")
public class MEFSAgentCommunicationMessage extends AgentCommunicationMessage {

	private int ttl;

	public MEFSAgentCommunicationMessage(Integer sourceHost, Integer destinationHost, Integer sourceAgent,
			Integer destinationAgent, int ttl) {
		super(sourceHost, destinationHost, sourceAgent, destinationAgent);
		this.ttl = ttl;
	}

	public int getTtl() {
		return ttl;
	}

	public void decreseTtl() {
		this.ttl--;
	}
	
	

}
