package hss;

import agent.communication.CommunicatingAgentInterface;
import host.communication.CommunicatingHostInterface;
import message.Message;

public class HSSMigratingMessage extends Message {

	private CommunicatingHostInterface newInhabitingHost;
	
	public HSSMigratingMessage(CommunicatingHostInterface sourceHost, CommunicatingHostInterface destinationHost,
			CommunicatingAgentInterface sourceAgent, CommunicatingAgentInterface destinationAgent, CommunicatingHostInterface newInhabitingHost) {
		super(sourceHost, destinationHost, sourceAgent, destinationAgent);
		this.newInhabitingHost = newInhabitingHost;
	}

	public CommunicatingHostInterface getNewInhabitingHost() {
		return newInhabitingHost;
	}
}
