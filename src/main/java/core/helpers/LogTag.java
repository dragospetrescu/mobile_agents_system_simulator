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

/**
 * Marks the part of the functionality logged.
 * Used to filter logs by functionality
 */
public enum LogTag {
	/**
	 * Agents initialization
	 */
	AGENT_INIT,
	/**
	 * Hosts initializations
	 */
	HOST_INIT,
	/**
	 * Graph initialization 
	 */
	GRAPH_INIT,
	/**
	 * Message routing from host to host to the destination host 
	 */
	MESSAGE_ROUTING,
	/**
	 * Agent migration from one host to another host 
	 */
	AGENT_MIGRATING,
	/**
	 * Logging about normal communication messages
	 */
	NORMAL_MESSAGE
}
