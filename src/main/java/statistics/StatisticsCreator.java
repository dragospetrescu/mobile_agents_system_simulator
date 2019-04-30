package statistics;

import java.util.HashMap;
import java.util.Map;

import message.AgentCommunicationMessageInterface;
import message.MessageInterface;

/**
 * Will create statistics for each protocol in order to compare them
 */
public class StatisticsCreator {
	
	
	public static StatisticsCreator statistics;
	
	public static synchronized StatisticsCreator getStatistics() {
		if(statistics == null) {
			statistics = new StatisticsCreator();
		}
		return statistics;
	}
	
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
}
