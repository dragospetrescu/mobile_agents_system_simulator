package statistics;

import java.util.HashMap;
import java.util.Map;

import message.MessageInterface;

/**
 * Will create statistics for each protocol in order to compare them
 */
public class StatisticsCreator {

	/**
	 * Maps each message to its delivery status
	 */
	private static Map<MessageInterface, Boolean> messagesDeliveryStatusMap = new HashMap<>();

	/**
	 * Marks a message was successfully to be delivered.
	 * 
	 * @param message that was successfully delivered
	 */
	public static void messageSuccesfullyDelivered(MessageInterface message) {
		messagesDeliveryStatusMap.put(message, true);
	}

	/**
	 * Marks a message failed to be delivered.
	 * 
	 * @param message that failed
	 */
	public static void messageFailedDelivered(MessageInterface message) {
		messagesDeliveryStatusMap.put(message, false);
	}

	/**
	 * Should only be called after the simulation is over
	 * 
	 * @return number of failed transmitted messages
	 */
	public static long getNumberOfFailed() {
		return messagesDeliveryStatusMap.keySet().stream().filter(message -> !messagesDeliveryStatusMap.get(message))
				.count();
	}

	/**
	 * Should only be called after the simulation is over
	 * 
	 * @return number of successfully transmitted messages
	 */
	public static long getNumberOfSuccess() {
		return messagesDeliveryStatusMap.keySet().stream().filter(message -> messagesDeliveryStatusMap.get(message))
				.count();
	}

	/**
	 * Should only be called after the simulation is over
	 * 
	 * @return total number of messages
	 */
	public static long getNumberOfMessages() {
		return messagesDeliveryStatusMap.keySet().size();
	}
}
