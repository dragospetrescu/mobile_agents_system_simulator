package host.communication;

import agent.communication.CommunicatingAgentInterface;
import host.protocol.AbstractProtocolHost;
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

	void initProtocol();

	void init(List<CommunicatingHostInterface> hosts);
	
	List<CommunicatingHostInterface> getAllHosts();

	CommunicatingHostInterface getHostById(int homeServerHostId);
}
