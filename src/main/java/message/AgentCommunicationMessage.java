package message;

/**
 * Generic message, implements the next hop logic Can be extended to create
 * custom, protocol specific messages
 */
public class AgentCommunicationMessage extends AbstractMessage implements AgentCommunicationMessageInterface {

	private int sourceAgentId;
	/**
	 * The destination agent. Can be null if the destination is a host.
	 */
	private int destinationAgentId;

	/**
	 * Warning. You should only use this constructor if the id provided is unique
	 * 
	 * @param id - the unique identifier
	 * @param sourceHostId - host from where the message is sent
	 * @param destinationHostId - the host where the message has to arrive
	 * @param sourceAgent - the agent that sends the message
	 * @param destinationAgent - the agent that has to receive the message
	 */
	public AgentCommunicationMessage(int id, int sourceHostId, int destinationHostId,
			int sourceAgent, int destinationAgent) {
		super(id, sourceHostId, destinationHostId);
		this.sourceAgentId = sourceAgent;
		this.destinationAgentId = destinationAgent;
//		TODO SET NEXT HOP !!!!!!!!!!
	}

	/**
	 * Use this constructor if you don't care about the id
	 * 
	 * @param sourceHost - host from where the message is sent
	 * @param destinationHost - the host where the message has to arrive
	 * @param sourceAgent - the agent that sends the message
	 * @param destinationAgent - the agent that has to receive the message
	 */
	public AgentCommunicationMessage(int sourceHost, int destinationHost,	int sourceAgent, int destinationAgent) {
		this(noMessages, sourceHost, destinationHost, sourceAgent, destinationAgent);
		noMessages++;
	}

	@Override
	public int getAgentSourceId() {
		return sourceAgentId;
	}

	@Override
	public int getAgentDestinationId() {
		return destinationAgentId;
	}
}
