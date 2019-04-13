package message;

import agent.communication.CommunicatingAgentInterface;
import host.communication.CommunicatingHostInterface;

/**
 * 
 * A message that will be sent from one agent or host to another agent or host
 * All message types have to implement this interface.
 * It is recommended to extend the class {@link Message} instead of implementing this class
 * 
 */
public interface MessageInterface {

	/**
	 * @return next host to the final destination. Like in the ip protocol
	 */
	CommunicatingHostInterface getNextHop();

	/**
	 * @return the last host that redirected the message
	 */
	CommunicatingHostInterface getPreviousHop();

	/**
	 * @return the final host destination
	 */
	CommunicatingHostInterface getHostDestination();

	/**
	 * @return the host from which the message was sent
	 */
	CommunicatingHostInterface getHostSource();

	/**
	 * @return the agent that sent the message. Can be null if the message was sent
	 *         by a host.
	 */
	CommunicatingAgentInterface getAgentSource();

	/**
	 * @return the destination agent. Can be null if the destination is a host.
	 *         Example: MigratingMessage
	 */
	CommunicatingAgentInterface getAgentDestination();

	/**
	 * @param nextHop - the next host to which it will be redirected in order to get
	 *                to the destination.
	 */
	void setNextHopHost(CommunicatingHostInterface nextHop);

	/**
	 * @return unique id of the message
	 */
	int getId();
}
