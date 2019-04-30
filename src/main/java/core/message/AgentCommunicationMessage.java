package core.message;

import core.statistics.StatisticsCreator;

/**
 * All agent communication will use this kind of messages.
 * This is the only kind of message that counts for the statistics
 */
public class AgentCommunicationMessage extends Message implements AgentCommunicationMessageInterface {

	/**
	 * The source agent id.
	 */
	private Integer sourceAgentId;
	/**
	 * The destination agent id. 
	 */
	private Integer destinationAgentId;

	/**
	 * Warning. You should only use this constructor if the id provided is unique
	 * 
	 * @param id - the unique identifier
	 * @param sourceHostId - host from where the message is sent
	 * @param destinationHostId - the host where the message has to arrive
	 * @param sourceAgent - the agent that sends the message
	 * @param destinationAgent - the agent that has to receive the message
	 */
	public AgentCommunicationMessage(Integer id, Integer sourceHostId, Integer destinationHostId,
			Integer sourceAgent, Integer destinationAgent) {
		super(id, sourceHostId, destinationHostId);
		this.sourceAgentId = sourceAgent;
		this.destinationAgentId = destinationAgent;
		StatisticsCreator statistics = StatisticsCreator.getStatistics();
		statistics.messageFailedDelivered(this);
	}

	/**
	 * Use this constructor if you don't care about the id
	 * 
	 * @param sourceHost - host from where the message is sent
	 * @param destinationHost - the host where the message has to arrive
	 * @param sourceAgent - the agent that sends the message
	 * @param destinationAgent - the agent that has to receive the message
	 */
	public AgentCommunicationMessage(Integer sourceHost, Integer destinationHost,	Integer sourceAgent, Integer destinationAgent) {
		this(noMessages, sourceHost, destinationHost, sourceAgent, destinationAgent);
		noMessages++;
	}

	@Override
	public Integer getAgentSourceId() {
		return sourceAgentId;
	}

	@Override
	public Integer getAgentDestinationId() {
		return destinationAgentId;
	}
}
