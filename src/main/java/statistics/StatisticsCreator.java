package statistics;

import java.util.HashMap;
import java.util.Map;

import message.MessageInterface;

public class StatisticsCreator {

	public static Map<MessageInterface, Boolean> messagesDeliveryStatusMap = new HashMap<>();

	public static void messageSuccesfullyDelivered(MessageInterface messsage) {
		messagesDeliveryStatusMap.put(messsage, true);
	}

	public static void messageFailedDelivered(MessageInterface message) {
		messagesDeliveryStatusMap.put(message, false);
	}

	public static long getNumberOfFailed() {
		return messagesDeliveryStatusMap.keySet().stream()
				.filter(message -> !messagesDeliveryStatusMap.get(message)).count();
	}

	public static long getNumberOfSuccess() {
		return messagesDeliveryStatusMap.keySet().stream().filter(message -> messagesDeliveryStatusMap.get(message))
				.count();
	}

	public static long getNumberOfMessages() {
		return messagesDeliveryStatusMap.keySet().size();
	}
}
