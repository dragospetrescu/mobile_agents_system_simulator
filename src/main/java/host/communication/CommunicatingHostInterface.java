package host.communication;

import agent.communication.CommunicatingAgentInterface;
import host.protocol.AbstractProtocolHost;
import host.protocol.ProtocolHost;
import message.MessageInterface;
import message.MessagesManager;
import message.implementation.MigratingAgentMessage;
import protocol.Protocol;

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
	 * @return list of agents that are currently residing on this host
	 */
	List<CommunicatingAgentInterface> getActiveAgents();

	/**
	 * @param agent - add a new agent to the list of active agents, list of agents
	 *              that are currently residing on this host
	 */
	void addAgent(CommunicatingAgentInterface agent);

	/**
	 * 
	 * Used to reroute message to the destination host by giving him the next hop
	 * 
	 * @param destinationHost - final destination of the message
	 * @return - next hop towards the final destination
	 */
	CommunicatingHostInterface getNextHop(CommunicatingHostInterface destinationHost);

	/**
	 * @return id - unique identifier of the host
	 */
	int getId();

	/**
	 * Used when creating re-routing table
	 * 
	 * @param destinationRouter - final host destination 
	 * @param nextHopRouter - next hop in order to get tot the final destination
	 */
	void addRouteNextHop(CommunicatingHostInterface destinationRouter, CommunicatingHostInterface nextHopRouter);

	/**
	 * Calls protocol.init
	 */
	void initProtocol();

	/**
	 * Sends all existing hosts
	 * 
	 * @param normalHosts - agents can migrate to those
	 * @param specialHosts - agent cannot migrate to those
	 */
	void init(List<CommunicatingHostInterface> normalHosts, List<CommunicatingHostInterface> specialHosts);
	
	/**
	 * @return all hosts to which the agent can migrate to
	 */
	List<CommunicatingHostInterface> getAllNormalHosts();

	/**
	 * Gets host by id. Should only be used in init
	 * 
	 * @param id 
	 * @return host with this id
	 */
	CommunicatingHostInterface getHostById(int id);
	
	/**
	 * @param protocol 
	 * @return implementation of protocol
	 */
	ProtocolHost getProtocolHost(Protocol protocol);
}
