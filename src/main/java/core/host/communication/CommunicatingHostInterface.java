package core.host.communication;

import core.agent.communication.CommunicatingAgentInterface;
import core.agent.protocol.ProtocolAgent;
import core.host.protocol.AbstractProtocolHost;
import core.host.protocol.ProtocolHost;
import core.message.AgentCommunicationMessageInterface;
import core.message.MessageInterface;
import core.message.MessagesManager;
import core.message.MigratingAgentMessage;
import protocol.Protocol;

import java.util.Collection;
import java.util.List;

/**
 * CommunicatingHostInterface represents the protocol-independent part of the
 * Agent. It's responsibilities are: - routing messages that did not get to the
 * final destination yet - sending messages to the {@link MessagesManager}
 */
public interface CommunicatingHostInterface {

	/**
	 * 
	 * If this host is not the message's destination the host redirects it to the
	 * next hop. If this host is the message's destination and the message is a
	 * {@link MigratingAgentMessage} then the agent is added to the active agents of
	 * the host. If this host is the message's destination and the message is NOT a
	 * {@link MigratingAgentMessage} then the message is interpreted by the
	 * {@link AbstractProtocolHost}
	 * 
	 * @param message - the received message
	 */
	void receiveMessage(MessageInterface message);

	/**
	 * @return true if the host has messages that need to be sent and false
	 *         otherwise
	 */
	boolean wantsToSendMessage();

	/**
	 * @param message - add this message to the list of messages to be be sent
	 */
	void addMessageForSending(MessageInterface message);

	/**
	 * @return the protocol used by this host. #TODO change this to a list
	 */
	Protocol getProtocol();

	/**
	 * @return the messages to be sent. They will be taken over by the
	 *         {@link MessagesManager}
	 */
	List<MessageInterface> sendMessages();

	/**
	 * 
	 * TODO REMOVE THIS????
	 * @return list of agents that are currently residing on this host
	 */
	Collection<CommunicatingAgentInterface> getActiveAgents();

	/**
	 * @param agent - add a new agent to the list of active agents, list of agents
	 *              that are currently residing on this host
	 */
	void addAgent(CommunicatingAgentInterface agent);

	/**
	 * @return id - unique identifier of the host
	 */
	Integer getId();

	/**
	 * Used when creating re-routing table
	 * 
	 * @param destinationRouter - final host destination 
	 * @param nextHopRouter - next hop in order to get tot the final destination
	 */
	void addRouteNextHop(Integer destinationRouterId, Integer nextHopRouterId);

	/**
	 * Calls protocol.init
	 * TODO CHECK THIS
	 */
	void initProtocol();

	/**
	 * Sends all existing hosts
	 * 
	 * @param normalHosts - agents can migrate to those
	 * @param specialHosts - agent cannot migrate to those
	 */
	void init(List<Integer> normalHostsIds);
	
	/**
	 * @return all hosts to which the agent can migrate to
	 */
	List<Integer> getAllNormalHostsIds();

	/**
	 * @param protocol 
	 * @return implementation of protocol
	 */
	ProtocolHost getProtocolHost(Protocol protocol);

	boolean hasAgentWithId(Integer communicatingAgentId);

	ProtocolAgent getProtocolAgentWithId(Integer communicatingAgentId);
	
	void reRouteMessage(MessageInterface message, Integer newDestinationHostId);
	
	public void routeMessage(MessageInterface message);
}