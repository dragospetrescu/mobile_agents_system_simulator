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
package ds;

import java.util.List;

import core.agent.communication.CommunicatingAgentInterface;
import core.agent.protocol.AbstractProtocolAgent;
import core.message.AgentCommunicationMessage;
import core.message.AgentCommunicationMessageInterface;

/**
 * Agent implementation of the Broadcast Protocol The agent sends a message to
 * all the hosts hoping that one of them will be inhabited by the
 * destinationAgent
 */
public class DSAgent extends AbstractProtocolAgent {

	/**
	 * @param communicatingAgent - the CommunicatingAgent that will use this
	 *                           protocol
	 */
	public DSAgent(CommunicatingAgentInterface communicatingAgent) {
		super(communicatingAgent.getId(), communicatingAgent, communicatingAgent.getProtocol());
	}

	@Override
	public void prepareMessageTo(Integer destinationAgentId) {
		CommunicatingAgentInterface communicatingAgent = getCommunicatingAgent();
		DSHost protocolHost = (DSHost) getProtocolHost();

		List<Integer> allHostsIds = protocolHost.getAllHosts();

		for (Integer hostId : allHostsIds) {
			AgentCommunicationMessageInterface message = new AgentCommunicationMessage(
					AgentCommunicationMessage.noMessages, communicatingAgent.getHostId(), hostId,
					communicatingAgent.getId(), destinationAgentId);
			protocolHost.sendMessage(message);
		}
		AgentCommunicationMessage.noMessages++;
	}
}
