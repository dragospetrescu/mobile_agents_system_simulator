package message.implementation;

import agent.communication.CommunicatingAgentInterface;
import host.communication.CommunicatingHostInterface;
import message.Message;

/**
 * Protocol independent message
 * All agents will communicate using this kind of messages.
 * These messages are the only one which will be counted for the
 * protocol statistics. 
 */
public class NormalCommunicationMessage extends Message {

	/**
	 * Use this constructor if you don't care about the id
	 * 
	 * @param sourceHost - host from where the message is sent
	 * @param destinationHost - the host where the message has to arrive
	 * @param sourceAgent - the agent that sends the message
	 * @param destinationAgent - the agent that has to receive the message
	 */
    public NormalCommunicationMessage(CommunicatingHostInterface sourceHost, CommunicatingHostInterface destinationHost, 
    		CommunicatingAgentInterface sourceAgent, CommunicatingAgentInterface destinationAgent) {
        super(sourceHost, destinationHost, sourceAgent, destinationAgent);
    }
    
	/**
	 * Warning. You should only use this constructor if the id provided is unique
	 * 
	 * @param id - the unique identifier
	 * @param sourceHost - host from where the message is sent
	 * @param destinationHost - the host where the message has to arrive
	 * @param sourceAgent - the agent that sends the message
	 * @param destinationAgent - the agent that has to receive the message
	 */
    public NormalCommunicationMessage(int id, CommunicatingHostInterface sourceHost, CommunicatingHostInterface destinationHost, 
    		CommunicatingAgentInterface sourceAgent, CommunicatingAgentInterface destinationAgent) {
        super(id, sourceHost, destinationHost, sourceAgent, destinationAgent);
    }
}
