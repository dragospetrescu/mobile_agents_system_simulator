package core.statistics;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import core.message.AgentCommunicationMessageInterface;
import core.message.MessageInterface;
import core.simulation.Constants;

/**
 * Will create statistics for each protocol in order to compare them
 */
public class Statistics {

	/**
	 * Singleton pattern
	 */
	public static Statistics statistics;

	/**
	 * Singleton pattern.
	 * 
	 * @return a unique StatisticsCreator object
	 */
	public static synchronized Statistics getStatistics() {
		if (statistics == null) {
			statistics = new Statistics();
		}
		return statistics;
	}

	/**
	 * Singleton pattern
	 */
	private Statistics() {
		messagesDeliveryStatusMap = new HashMap<>();
	}

	/**
	 * Maps each message to its delivery status
	 */
	private Map<MessageInterface, Boolean> messagesDeliveryStatusMap;
	/**
	 * Holds network load
	 */
	private double networkLoad;

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
//		messagesDeliveryStatusMap.keySet().stream().filter(message -> !messagesDeliveryStatusMap.get(message))
//				.forEach(e -> System.out.println(e));
		
		List<MessageInterface> agentCommunicationMessages = messagesDeliveryStatusMap.keySet().stream()
				.filter(message -> message instanceof AgentCommunicationMessageInterface).collect(Collectors.toList());
		double average = 0.0;
		long numMessages = agentCommunicationMessages.size();
		for (MessageInterface messageInterface : agentCommunicationMessages) {
			average += 1.0 * messageInterface.getTimeSpentToFinalDestination() / numMessages;
		}
		return average;
	}

	/**
	 * Calculate network load at each step
	 * @param size
	 */
	public void calculateNetworkLoad(int size) {
		int numSteps = Constants.NO_WORKING_STEPS + Constants.STEPS_WAITING_FOR_INIT + Constants.STEPS_WAITING_FOR_LAST_MESSAGES;
		networkLoad += size * 1.0 / numSteps;
	}

	/**
	 * @return the network load
	 */
	public double getNetworkLoad() {
		return networkLoad;
	}
	
	/**
	 * @return time spent delivering the failed messages
	 */
	public double getNetworkTimeOfFailedMessages() {
		List<MessageInterface> failedMessages = messagesDeliveryStatusMap.keySet().stream().filter(message -> !messagesDeliveryStatusMap.get(message)).collect(Collectors.toList());
		double average = 0.0;
		long numMessages = failedMessages.size();
		for (MessageInterface messageInterface : failedMessages) {
			average += 1.0 * messageInterface.getTimeSpentToFinalDestination() / numMessages;
		}
		return average;
	}
}
