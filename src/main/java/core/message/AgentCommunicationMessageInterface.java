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
package core.message;

/**
 * 
 * A message that will be sent from one agent or host to another agent or host
 * All message types have to implement this interface.
 * It is recommended to extend the class {@link AgentCommunicationMessage} instead of implementing this class
 * 
 */
public interface AgentCommunicationMessageInterface extends MessageInterface {

	/**
	 * @return the agent that sent the message. Can be null if the message was sent
	 *         by a host.
	 */
	Integer getAgentSourceId();

	/**
	 * @return the destination agent. Can be null if the destination is a host.
	 *         Example: MigratingMessage
	 */
	Integer getAgentDestinationId();
}
