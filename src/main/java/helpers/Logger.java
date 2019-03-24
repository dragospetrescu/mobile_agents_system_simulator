package helpers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Logger {

	public static List<LogTag> ACCEPTED_LOGS = new ArrayList<LogTag>(
			Arrays.asList(LogTag.NORMAL_MESSAGE, LogTag.AGENT_MIGRATING));

	public static void i(LogTag tag, String message) {
		if (ACCEPTED_LOGS.contains(tag))
			System.out.println(LoggerTypes.INFO + " -> " + message);
	}

	public static void d(LogTag tag, String message) {
		if (ACCEPTED_LOGS.contains(tag))
			System.out.println(LoggerTypes.DEBUG + " -> " + message);
	}

	public static void w(LogTag tag, String message) {
		if (ACCEPTED_LOGS.contains(tag))
			System.out.println(LoggerTypes.WARNING + " -> " + message);
	}

	public static void e(LogTag tag, String message) {
		if (ACCEPTED_LOGS.contains(tag))
			System.out.println(LoggerTypes.ERROR + " -> " + message);
	}

	enum LoggerTypes {
		INFO, DEBUG, WARNING, ERROR
	}
}
