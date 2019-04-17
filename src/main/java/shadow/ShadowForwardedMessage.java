package shadow;

import agent.communication.CommunicatingAgentInterface;
import host.communication.CommunicatingHostInterface;
import message.Message;

/**
 * This message is sent to the HomeServer and then to the Home Agent
 * 
 * Agent 0 wants to send message to Agent 1
 * Agent 0 sends message to HomeServer
 * HomeServer sends message to Agent 1 Home Agent
 * Home Agent sends message location of the Agent
 */
public class ShadowForwardedMessage extends Message{

	/**
	 * Use this constructor if you don't care about the id
	 * 
	 * @param sourceHost - host from where the message is sent
	 * @param destinationHost - the host where the message has to arrive
	 * @param sourceAgent - the agent that sends the message
	 * @param destinationAgent - the agent that has to receive the message
	 */
	public ShadowForwardedMessage(CommunicatingHostInterface sourceHost, CommunicatingHostInterface destinationHost,
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
	public ShadowForwardedMessage(int id, CommunicatingHostInterface sourceHost, CommunicatingHostInterface destinationHost,
			CommunicatingAgentInterface sourceAgent, CommunicatingAgentInterface destinationAgent) {
		super(id, sourceHost, destinationHost, sourceAgent, destinationAgent);
	}
}
