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
package ramdp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import core.host.communication.CommunicatingHostInterface;
import core.host.protocol.AbstractProtocolHost;
import core.message.AgentCommunicationMessageInterface;
import core.message.MessageInterface;

/**
 * Host implementation of the Broadcast Protocol If this host does not contain
 * the destination agent of the message it discards the message
 */
public class RAMDPRegionServer extends AbstractProtocolHost {

	/**
	 * Holds messages that will be delivered
	 */
	Map<Integer, List<MessageInterface>> blackboard;

	/**
	 * @param communicationHost - the CommunicatingAgent that will use this protocol
	 */
	public RAMDPRegionServer(CommunicatingHostInterface communicationHost) {
		super(communicationHost.getId(), communicationHost, communicationHost.getProtocol());
	}

	@Override
	public void interpretMessage(MessageInterface message) {
		
		if (message instanceof RAMDPSyncMessage) {
			RAMDPSyncMessage locationMessage = (RAMDPSyncMessage) message;
			Integer agentId = locationMessage.getSourceAgentId();
			Integer newHostId = locationMessage.getHostSourceId();
			
			List<MessageInterface> messages = blackboard.get(agentId);
			locationMessage.setMessages(messages);
			blackboard.put(agentId, new ArrayList<>());
			
			CommunicatingHostInterface communicationHost = getCommunicationHost();
			communicationHost.reRouteMessage(locationMessage, newHostId);
			communicationHost.addMessageForSending(locationMessage);
			
		} else if(message instanceof AgentCommunicationMessageInterface){
			AgentCommunicationMessageInterface commMessage = (AgentCommunicationMessageInterface) message;
			Integer agentDestinationId = commMessage.getAgentDestinationId();
			
			if(!blackboard.containsKey(agentDestinationId))
				blackboard.put(agentDestinationId, new ArrayList<MessageInterface>());
			List<MessageInterface> agentMessages = blackboard.get(agentDestinationId);
			agentMessages.add(commMessage);
		} else {
			super.interpretMessage(message);
		}
	}

	@Override
	public void init(Map<String, String> protocolArguments) {
		blackboard = new HashMap<>();
	}
}
