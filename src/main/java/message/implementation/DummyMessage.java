package message.implementation;

import agent.communication.CommunicatingAgentInterface;
import host.communication.CommunicatingHostInterface;
import message.Message;

public class DummyMessage extends Message {

    public DummyMessage(CommunicatingHostInterface sourceHost, CommunicatingHostInterface destinationHost, 
    		CommunicatingAgentInterface sourceAgent, CommunicatingAgentInterface destinationAgent) {
        super(sourceHost, destinationHost, sourceAgent, destinationAgent);
    }
}
