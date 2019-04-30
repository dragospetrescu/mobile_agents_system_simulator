package core.agent.protocol;

import protocol.Protocol;

import java.util.Map;

import core.agent.communication.CommunicatingAgentInterface;
import core.host.protocol.ProtocolHost;
import core.message.MessageInterface;
import core.message.MigratingAgentMessageInterface;

/**
 * ProtocolAgent represents the protocol-dependent part of the Agent. It's
 * responsibilities are: - interpreting messages - getting the necessary
 * information in order for them to create a message to a certain destination
 */
public interface ProtocolAgent {

	/**
	 * Receives and interprets the messages. If the message's destination is the
	 * CommunicatingAgent it redirects it to the CommunicatingAgent Otherwise, it
	 * interprets it.
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
	 * The CommunicatingAgent asks to prepare a message for the destinationAgent
	 * received as a param
	 * 
	 * @param destinationAgentId - the destinationAgent id for the message that has
	 *                           to be created
	 */
	void prepareMessageTo(Integer destinationAgentId);

	/**
	 * @return id - unique identifier
	 */
	Integer getId();

	/**
	 * @return the protocol of this agent
	 */
	Protocol getProtocol();

	/**
	 * Protocol dependent migration process HAVE TO CALL SUPER IF YOU OVERWRITE
	 * 
	 * @param migratingMessage  - message that will transport the agent
	 * 
	 * @param destinationHostId - migrating to this host
	 */
	void migrate(Integer destinationHostId, MigratingAgentMessageInterface migratingMessage);

	/**
	 * HAVE TO CALL SUPER IF YOU OVERWRITE
	 * 
	 * @param protocolArguments extra arguments for the protocol
	 * @param protocolHost
	 */
	void init(Map<String, String> protocolArguments, ProtocolHost protocolHost);

	/**
	 * @param protocolHost - the new protocol host of the agent. Used when the agent
	 *                     is migrating to a new host
	 */
	void setProtocolHost(ProtocolHost protocolHost);
}
