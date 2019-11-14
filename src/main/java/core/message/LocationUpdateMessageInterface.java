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
 * Interface for LocationUpdateMessage. This message transmits that a certain
 * agent changed it's current location.
 */
public interface LocationUpdateMessageInterface extends MessageInterface {

	/**
	 * @return the id of the agent that is changing location
	 */
	public Integer getAgentId();

	/**
	 * @return the protocol of the agent that is changing location
	 */
	public Protocol getProtocol();

	/**
	 * @return the id of the host to which the agent is moving
	 */
	public Integer getNewHostId();
}
