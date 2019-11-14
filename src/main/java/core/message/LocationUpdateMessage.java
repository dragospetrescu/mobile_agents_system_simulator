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

import protocol.Protocol;

/**
 * This message transmits that a certain agent changed it's current location.
 */
public class LocationUpdateMessage extends Message implements LocationUpdateMessageInterface {

	/**
	 * Id of the migrating agent
	 */
	private Integer agentId;
	/**
	 * Id of the host to which it is migrating
	 */
	private Integer newHostId;
	/**
	 * Protocol of the agent that sent this message
	 */
	private Protocol protocol;

	/**
	 * @param sourceHost - the host from which it is migrating & sending the message
	 * @param destinationHost - the host to which the message has to arrive
	 * @param agentId - the agent that is migrating
	 * @param newHostId - the host to which the agent is migrating
	 * @param protocol - the protocol of the agent which is migrating
	 */
	public LocationUpdateMessage(Integer sourceHost, Integer destinationHost, Integer agentId, Integer newHostId, Protocol protocol) {
		super(sourceHost, destinationHost);
		this.agentId = agentId;
		this.newHostId = newHostId;
		this.protocol = protocol;
	}

	public Integer getAgentId() {
		return agentId;
	}

	public Integer getNewHostId() {
		return newHostId;
	}

	public Protocol getProtocol() {
		return protocol;
	}
}
