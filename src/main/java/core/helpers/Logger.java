package core.helpers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Logs events based on tags and severity
 */
public class Logger {

	/**
	 * Used to filter only some tags
	 */
	public static List<LogTag> ACCEPTED_LOGS = new ArrayList<LogTag>(
			Arrays.asList());

	/**
	 * Logs info
	 * 
	 * @param tag     - the part of functionality it is logging
	 * @param message - string to be printed
	 */
	public static void i(LogTag tag, String message) {
		if (ACCEPTED_LOGS.contains(tag))
			System.out.println(Severity.INFO + " -> " + message);
	}

	/**
	 * Logs debug
	 * 
	 * @param tag     - the part of functionality it is logging
	 * @param message - string to be printed
	 */
	public static void d(LogTag tag, String message) {
		if (ACCEPTED_LOGS.contains(tag))
			System.out.println(Severity.DEBUG + " -> " + message);
	}

	/**
	 * Logs warning
	 * 
	 * @param tag     - the part of functionality it is logging
	 * @param message - string to be printed
	 */
	public static void w(LogTag tag, String message) {
		if (ACCEPTED_LOGS.contains(tag))
			System.out.println(Severity.WARNING + " -> " + message);
	}

	/**
	 * Logs error
	 * 
	 * @param tag     - the part of functionality it is logging
	 * @param message - string to be printed
	 */
	public static void e(LogTag tag, String message) {
		if (ACCEPTED_LOGS.contains(tag))
			System.out.println(Severity.ERROR + " -> " + message);
	}

	/**
	 * Logging severity
	 */
	enum Severity {
		/**
		 * A lot of them. Useful for statistics.
		 */
		INFO,
		/**
		 * A lot of them. Useful for debugging.
		 */
		DEBUG,
		/**
		 * Marks a possible problem. Should not be ignored.
		 */
		WARNING,
		/**
		 * Marks major error that probably stopped the simulation.
		 */
		ERROR
	}
}
