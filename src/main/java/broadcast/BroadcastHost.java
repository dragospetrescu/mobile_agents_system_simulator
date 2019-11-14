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
package broadcast;

import java.util.Map;

import core.agent.protocol.ProtocolAgent;
import core.helpers.LogTag;
import core.helpers.Logger;
import core.host.communication.CommunicatingHostInterface;
import core.host.protocol.AbstractProtocolHost;
import core.message.AgentCommunicationMessageInterface;
import core.message.MessageInterface;

/**
 * Host implementation of the Broadcast Protocol If this host does not contain
 * the destination agent of the message it discards the message
 */
public class BroadcastHost extends AbstractProtocolHost {

	/**
	 * @param communicationHost - the CommunicatingAgent that will use this protocol
	 */
	public BroadcastHost(CommunicatingHostInterface communicationHost) {
		super(communicationHost.getId(), communicationHost, communicationHost.getProtocol());
	}

	@Override
	public void interpretMessage(MessageInterface message) {
		if (message instanceof AgentCommunicationMessageInterface) {
			AgentCommunicationMessageInterface agentCommunicationMessage = (AgentCommunicationMessageInterface) message;
			Integer communicatingAgentId = agentCommunicationMessage.getAgentDestinationId();
			CommunicatingHostInterface communicationHost = getCommunicationHost();
			if (communicationHost.hasAgentWithId(communicatingAgentId)) {
				ProtocolAgent protocolAgent = communicationHost.getProtocolAgentWithId(communicatingAgentId);
				protocolAgent.receiveMessage(message);
			} else {
				Logger.w(LogTag.AGENT_MIGRATING, message + " arrived at " + this + " but did not find Agent " + agentCommunicationMessage.getAgentDestinationId());
			}
		} else {
			super.interpretMessage(message);
		}
	}

	@Override
	public void init(Map<String, String> protocolArguments) {
	}
}
