/*******************************************************************************
 * Copyright 2019 dragos
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License.  You may obtain a copy
 * of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations under
 * the License.
 ******************************************************************************/
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
