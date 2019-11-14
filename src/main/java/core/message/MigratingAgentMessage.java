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

import core.agent.communication.CommunicatingAgentInterface;

/**
 * Protocol independent message All agents will migrate using this kind of
 * messages.
 */
public class MigratingAgentMessage extends Message implements MigratingAgentMessageInterface {

	/**
	 * The agent that is migrating from the sourceHost to the destinationHost
	 */
	private CommunicatingAgentInterface migratingAgent;

	/**
	 * @param sourceHost      - host from where the message is sent
	 * @param destinationHost - the host where the message has to arrive
	 * @param migratingAgent  - the agent that is migrating from the sourceHost to
	 *                        the destinationHost
	 */
	public MigratingAgentMessage(Integer sourceHost, Integer destinationHost, CommunicatingAgentInterface migratingAgent) {
		super(sourceHost, destinationHost);
		this.migratingAgent = migratingAgent;
	}

	/**
	 * @return the agent that is migrating from the sourceHost to the
	 *         destinationHost
	 */
	@Override
	public CommunicatingAgentInterface getMigratingAgent() {
		return migratingAgent;
	}
}
