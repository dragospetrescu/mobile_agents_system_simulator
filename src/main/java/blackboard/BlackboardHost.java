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
package blackboard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import core.agent.communication.CommunicatingAgentInterface;
import core.agent.protocol.ProtocolAgent;
import core.host.communication.CommunicatingHostInterface;
import core.host.protocol.AbstractProtocolHost;
import core.message.AgentCommunicationMessageInterface;
import core.message.MessageInterface;
import core.message.MigratingAgentMessageInterface;

/**
 * Host implementation of the Broadcast Protocol If this host does not contain
 * the destination agent of the message it discards the message
 */
public class BlackboardHost extends AbstractProtocolHost {

	/**
	 * Holds messages that will be delivered when agent migrates to this host
	 */
	Map<Integer, List<AgentCommunicationMessageInterface>> blackboard;

	/**
	 * @param communicationHost - the CommunicatingAgent that will use this protocol
	 */
	public BlackboardHost(CommunicatingHostInterface communicationHost) {
		super(communicationHost.getId(), communicationHost, communicationHost.getProtocol());
	}

	@Override
	public void interpretMessage(MessageInterface message) {
		
		if (message instanceof MigratingAgentMessageInterface) {
			super.interpretMessage(message);
			MigratingAgentMessageInterface migrationMessage = (MigratingAgentMessageInterface) message;
			CommunicatingAgentInterface migratingAgent = migrationMessage.getMigratingAgent();
			ProtocolAgent protocolAgent = migratingAgent.getProtocolAgent();
			Integer agentId = migratingAgent.getId();
			
			List<AgentCommunicationMessageInterface> messages = blackboard.get(agentId);
			if(messages == null || messages.isEmpty())
				return;
			
			for (AgentCommunicationMessageInterface commMessage : messages) {
				protocolAgent.receiveMessage(commMessage);
			}
			
		} else {
			super.interpretMessage(message);
		}
	}

	@Override
	public void init(Map<String, String> protocolArguments) {
		blackboard = new HashMap<>();
	}

	@Override
	public void sendMessage(MessageInterface message) {
		if (message instanceof AgentCommunicationMessageInterface) {
			AgentCommunicationMessageInterface commMesage = (AgentCommunicationMessageInterface) message;
			Integer agentDestinationId = commMesage.getAgentDestinationId();
			if (!blackboard.containsKey(agentDestinationId))
				blackboard.put(agentDestinationId, new ArrayList<>());
			blackboard.get(agentDestinationId).add(commMesage);
		} else {
			super.sendMessage(message);
		}

	}
}
