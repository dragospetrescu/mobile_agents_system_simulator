package hss;

import agent.communication.CommunicatingAgentInterface;
import host.communication.CommunicatingHostInterface;
import message.Message;
import message.MessageInterface;

public class HSSForwardedMessage extends Message{

	public HSSForwardedMessage(CommunicatingHostInterface sourceHost, CommunicatingHostInterface destinationHost,
			CommunicatingAgentInterface sourceAgent, CommunicatingAgentInterface destinationAgent) {
		super(sourceHost, destinationHost, sourceAgent, destinationAgent);
	}
	
	public HSSForwardedMessage(int id, CommunicatingHostInterface sourceHost, CommunicatingHostInterface destinationHost,
			CommunicatingAgentInterface sourceAgent, CommunicatingAgentInterface destinationAgent) {
		super(id, sourceHost, destinationHost, sourceAgent, destinationAgent);
	}
}
