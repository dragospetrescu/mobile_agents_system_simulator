package message;

/**
 * 
 * A message that will be sent from one agent or host to another agent or host
 * All message types have to implement this interface.
 * It is recommended to extend the class {@link AgentCommunicationMessage} instead of implementing this class
 * 
 */
public interface AgentCommunicationMessageInterface extends MessageInterface {

	/**
	 * @return the agent that sent the message. Can be null if the message was sent
	 *         by a host.
	 */
	Integer getAgentSourceId();

	/**
	 * @return the destination agent. Can be null if the destination is a host.
	 *         Example: MigratingMessage
	 */
	Integer getAgentDestinationId();
}
