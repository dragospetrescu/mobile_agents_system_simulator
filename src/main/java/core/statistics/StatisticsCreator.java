package core.statistics;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import core.message.AgentCommunicationMessageInterface;
import core.message.MessageInterface;

/**
 * Will create statistics for each protocol in order to compare them
 */
public class StatisticsCreator {

	/**
	 * Singleton pattern
	 */
	public static StatisticsCreator statistics;

	/**
	 * Singleton pattern.
	 * 
	 * @return a unique StatisticsCreator object
	 */
	public static synchronized StatisticsCreator getStatistics() {
		if (statistics == null) {
			statistics = new StatisticsCreator();
		}
		return statistics;
	}

	/**
	 * Singleton pattern
	 */
	private StatisticsCreator() {
		messagesDeliveryStatusMap = new HashMap<>();
	}

	/**
	 * Maps each message to its delivery status
	 */
	private Map<MessageInterface, Boolean> messagesDeliveryStatusMap;

	/**
	 * Marks a message was successfully to be delivered.
	 * 
	 * @param message that was successfully delivered
	 */
	public void messageSuccesfullyDelivered(MessageInterface message) {
		messagesDeliveryStatusMap.put(message, true);
	}

	/**
	 * Marks a message failed to be delivered.
	 * 
	 * @param message that failed
	 */
	public void messageFailedDelivered(MessageInterface message) {
		messagesDeliveryStatusMap.put(message, false);
	}

	/**
	 * Should only be called after the simulation is over
	 * 
	 * @return number of failed transmitted messages
	 */
	public long getNumberOfFailed() {
		return messagesDeliveryStatusMap.keySet().stream().filter(message -> !messagesDeliveryStatusMap.get(message))
				.count();
	}

	/**
	 * Should only be called after the simulation is over
	 * 
	 * @return number of successfully transmitted messages
	 */
	public long getNumberOfSuccess() {
		return messagesDeliveryStatusMap.keySet().stream().filter(message -> messagesDeliveryStatusMap.get(message))
				.count();
	}

	/**
	 * Should only be called after the simulation is over
	 * 
	 * @return total number of messages
	 */
	public long getNumberOfMessages() {
		return messagesDeliveryStatusMap.keySet().size();
	}

	/**
	 * @return average delivery time of AgentCommunicationMessageInterface messages
	 */
	public double getAverageDeliveryTime() {
		List<MessageInterface> agentCommunicationMessages = messagesDeliveryStatusMap.keySet().stream()
				.filter(message -> message instanceof AgentCommunicationMessageInterface).collect(Collectors.toList());
		double average = 0.0;
		long numMessages = agentCommunicationMessages.size();
		for (MessageInterface messageInterface : agentCommunicationMessages) {
			average += 1.0 * messageInterface.getTimeSpentToFinalDestination() / numMessages;
		}
		return average;
	}
}
