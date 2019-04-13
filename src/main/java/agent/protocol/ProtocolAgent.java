package agent.protocol;

import message.MessageInterface;
import protocol.Protocol;

import agent.communication.CommunicatingAgentInterface;

/**
 * ProtocolAgent represents the protocol-dependent part of the Agent. It's
 * responsibilities are: 
 * - interpreting messages 
 * - getting the necessary information in order for them to create a message 
 *   to a certain destination
 */
public interface ProtocolAgent {

	/**
	 * Receives and interprets the messages.
	 * If the message's destination is the CommunicatingAgent it redirects it to the CommunicatingAgent
	 * Otherwise, it interprets it.
	 * 
	 * @param message - the message to be redirected / interpreted
	 */
	void receiveMessage(MessageInterface message);

	/**
	 * @return the CommunicatingAgent that is currently using this ProtocolAgent
	 */
	CommunicatingAgentInterface getCommunicatingAgent();

	/**
	 * The CommunicatingAgent asks to prepare a message for the destinationAgent received as a param
	 * 
	 * @param destinationAgent - the destinationAgent for the message that has to be created
	 */
	void prepareMessageTo(CommunicatingAgentInterface destinationAgent);

	/**
	 * @return id - unique identifier
	 */
	int getId();

	/**
	 * @return the protocol of this agent
	 */
	Protocol getProtocol();

}
