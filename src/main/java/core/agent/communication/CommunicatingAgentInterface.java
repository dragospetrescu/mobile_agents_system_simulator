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
package core.agent.communication;

import protocol.Protocol;

import java.util.List;

import core.agent.protocol.ProtocolAgent;
import core.host.communication.CommunicatingHost;
import core.host.communication.CommunicatingHostInterface;
import core.message.MessageInterface;

/**
 * CommunicatingAgentInterface represents the protocol-independent part of the Agent.
 * It's responsibilities are:
 * - migrating
 * - calling the protocol to send messages to a certain agent
 * - giving the messages to the host
 * - create statistics about received messages
 */
public interface CommunicatingAgentInterface {

	/**
	 * When the agent decides to migrate from one host to another it has to prepare
	 * a MigratingMessage The removing from the current host will be done by the
	 * simulation
	 */
	void prepareMigratingMessage();

	/**
	 * @return the host that the agent is currently on
	 */
	CommunicatingHostInterface getHost();

	/**
	 * Counts the time between consecutive migrations
	 */
	void work();

	/**
	 * Resets the time between consecutive migrations
	 */
	void setWork();

	/**
	 * @return true if the work is done and false if not
	 */
	boolean wantsToMigrate();

	/**
	 * Receives message and creates statistics
	 * 
	 * @param message
	 */
	void receiveMessage(MessageInterface message);

	/**
	 * @return the communication protocol this agent is using
	 */
	Protocol getProtocol();

	/**
	 * @return the Protocol Agent that the CommunicatingAgent is using
	 */
	ProtocolAgent getProtocolAgent();

	/**
	 * Sets for each agent the allAgents of the simulations and allHosts of the simulation
	 * 
	 * @param allAgentsIds - all CommunicatingAgents ids of the simulation
	 * @param host - all CommunicatingHosts of the simulation
	 */
	void initAgent(List<Integer> allAgentsIds, CommunicatingHostInterface host);

	/**
	 * @return - the unique id of each CommunicatingAgentInterface
	 */
	Integer getId();

	/**
	 * Sets the host this CommunicatingAgent is currently inhabiting
	 * 
	 * @param communicatingHost
	 */
	void setHost(CommunicatingHost communicatingHost);
	/**
	 * Calls protocol.init()
	 */
	void initProtocol();

	/**
	 * @return id of the host that this agent is inhabiting
	 */
	Integer getHostId();

	/**
	 * Mark that agent should not create and send new messages any more
	 */
	void stopCreatingNewMessages();

}
