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
package core.agent.protocol;

import protocol.Protocol;

import java.util.Map;

import core.agent.communication.CommunicatingAgentInterface;
import core.host.protocol.ProtocolHost;
import core.message.MessageInterface;
import core.message.MigratingAgentMessageInterface;

/**
 * ProtocolAgent represents the protocol-dependent part of the Agent. It's
 * responsibilities are: - interpreting messages - getting the necessary
 * information in order for them to create a message to a certain destination
 */
public interface ProtocolAgent {

	/**
	 * Receives and interprets the messages. If the message's destination is the
	 * CommunicatingAgent it redirects it to the CommunicatingAgent Otherwise, it
	 * interprets it.
	 * 
	 * @param message - the message to be redirected / interpreted
	 */
	void receiveMessage(MessageInterface message);

	/**
	 * @return the CommunicatingAgent that is currently using this ProtocolAgent
	 */
	CommunicatingAgentInterface getCommunicatingAgent();

	/**
	 * @return the CommunicatingAgent that is currently using this ProtocolAgent
	 */
	ProtocolHost getProtocolHost();

	/**
	 * The CommunicatingAgent asks to prepare a message for the destinationAgent
	 * received as a param
	 * 
	 * @param destinationAgentId - the destinationAgent id for the message that has
	 *                           to be created
	 */
	void prepareMessageTo(Integer destinationAgentId);

	/**
	 * @return id - unique identifier
	 */
	Integer getId();

	/**
	 * @return the protocol of this agent
	 */
	Protocol getProtocol();

	/**
	 * Protocol dependent migration process HAVE TO CALL SUPER IF YOU OVERWRITE
	 * 
	 * @param migratingMessage  - message that will transport the agent
	 * 
	 * @param destinationHostId - migrating to this host
	 */
	void migrate(Integer destinationHostId, MigratingAgentMessageInterface migratingMessage);

	/**
	 * HAVE TO CALL SUPER IF YOU OVERWRITE
	 * 
	 * @param protocolArguments extra arguments for the protocol
	 * @param protocolHost
	 */
	void init(Map<String, String> protocolArguments, ProtocolHost protocolHost);

	/**
	 * @param protocolHost - the new protocol host of the agent. Used when the agent
	 *                     is migrating to a new host
	 */
	void setProtocolHost(ProtocolHost protocolHost);
}
