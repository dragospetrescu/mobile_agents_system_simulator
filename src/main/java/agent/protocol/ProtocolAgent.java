package agent.protocol;

import message.MessageInterface;
import protocol.Protocol;

import java.util.Map;

import agent.communication.CommunicatingAgentInterface;
import host.communication.CommunicatingHostInterface;
import host.protocol.ProtocolHost;

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
	 * @return the CommunicatingAgent that is currently using this ProtocolAgent
	 */
	ProtocolHost getProtocolHost();

	/**
	 * The CommunicatingAgent asks to prepare a message for the destinationAgent received as a param
	 * 
	 * @param destinationAgent - the destinationAgent for the message that has to be created
	 */
	void prepareMessageTo(int destinationAgentId);

	/**
	 * @return id - unique identifier
	 */
	int getId();

	/**
	 * @return the protocol of this agent
	 */
	Protocol getProtocol();

	/**
	 * Protocol dependent migration process
	 * 
	 * @param destinationHost - migrating to this host
	 */
	void migrate(int destinationHostId);

	/**
	 * @param protocolArguments extra arguments for the protocol
	 * @param protocolHost 
	 */
	void init(Map<String, String> protocolArguments, ProtocolHost protocolHost);

	void setProtocolHost(ProtocolHost protocolHost);
}
