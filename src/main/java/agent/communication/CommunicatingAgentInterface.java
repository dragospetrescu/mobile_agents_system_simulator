package agent.communication;

import message.AgentCommunicationMessageInterface;
import message.MessageInterface;
import protocol.Protocol;

import java.util.List;

import agent.protocol.ProtocolAgent;
import host.communication.CommunicatingHost;
import host.communication.CommunicatingHostInterface;

/**
 * CommunicatingAgentInterface represents the protocol-independent part of the Agent.
 * It's responsibilities are:
 * - migrating
 * - calling the protocol to send messages to a certain agent
 * - giving the messages to the host
 * - create statistics about received messages
 */
public interface CommunicatingAgentInterface {

	/**
	 * When the agent decides to migrate from one host to another it has to prepare
	 * a MigratingMessage The removing from the current host will be done by the
	 * simulation
	 * 
	 * @return MigratingMessage that contains the migrating agent
	 */
	MessageInterface prepareMigratingMessage();

	/**
	 * @return the host that the agent is currently on
	 */
	CommunicatingHostInterface getHost();

	/**
	 * Counts the time between consecutive migrations
	 */
	void work();

	/**
	 * Resets the time between consecutive migrations
	 */
	void setWork();

	/**
	 * @return true if the work is done and false if not
	 */
	boolean wantsToMigrate();

	/**
	 * Receives message and creates statistics
	 * 
	 * @param message
	 */
	void receiveMessage(MessageInterface message);

	/**
	 * @return the communication protocol this agent is using
	 */
	Protocol getProtocol();

	/**
	 * @return the Protocol Agent that the CommunicatingAgent is using
	 */
	ProtocolAgent getProtocolAgent();

	/**
	 * Sets for each agent the allAgents of the simulations and allHosts of the simulation
	 * 
	 * @param allAgents - all CommunicatingAgents of the simulation
	 * @param hosts - all CommunicatingHosts of the simulation
	 */
	void initAgent(List<Integer> allAgentsIds, CommunicatingHostInterface host);

	/**
	 * @return - the unique id of each CommunicatingAgentInterface
	 */
	int getId();

	/**
	 * Sets the host this CommunicatingAgent is currently inhabiting
	 * 
	 * @param communicatingHost
	 */
	void setHost(CommunicatingHost communicatingHost);
	/**
	 * Calls protocol.init()
	 */
	void initProtocol();

	int getHostId();

}
