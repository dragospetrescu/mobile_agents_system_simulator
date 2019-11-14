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
package core.host.protocol;

import java.util.List;
import java.util.Map;

import core.host.communication.CommunicatingHostInterface;
import core.message.MessageInterface;
import protocol.Protocol;

/**
 * ProtocolHost represents the protocol-dependent part of the Host. 
 * It's responsibilities are: 
 * - interpreting messages 
 */
public interface ProtocolHost {

	/**
	 * If the host contains the destination agent it redirects the message to the agent.
	 * Otherwise it marks it as a fail for statistic reasons.
	 * 
	 * @param message - the message to be interpreted / redirected
	 */
	void interpretMessage(MessageInterface message);

	/**
	 * @return the CommunicatingHost that is currently using this ProtocolHost
	 */
	CommunicatingHostInterface getCommunicationHost();

	/**
	 * @return id - unique identifier
	 */
	Integer getId();

	/**
	 * @return the protocol of this agent
	 */
	Protocol getProtocol();

	/**
	 * Protocol init
	 * @param protocolArguments - arguments for protocolHost
	 */
	void init(Map<String, String> protocolArguments);
	
	/**
	 * Communicating line is:
	 * CommunicatingAgent -> ProtocolAgent -> ProtocolHost -> CommunicationHost -> MessageManager ->
	 * CommunicationHost -> ProtocolHost -> ProtocolAgent -> CommuncatingAgent
	 * 
	 * @param message - the message that will be delivered to the communicationgHost
	 */
	void sendMessage(MessageInterface message);

	/**
	 * @return list of ids of all hosts in simulation
	 */
	public List<Integer> getAllHosts();
}
