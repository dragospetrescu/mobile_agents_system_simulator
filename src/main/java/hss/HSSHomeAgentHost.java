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
package hss;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import core.agent.communication.CommunicatingAgentInterface;
import core.host.communication.CommunicatingHostInterface;
import core.host.protocol.AbstractProtocolHost;
import core.message.AgentCommunicationMessageInterface;
import core.message.LocationUpdateMessageInterface;
import core.message.MessageInterface;

/**
 * The HomeServer from the HSS documentation
 * 
 * It keeps a map agent -> host location
 * 
 * When agent migrates it sends to it's home host message that updates the
 * location database
 */
public class HSSHomeAgentHost extends AbstractProtocolHost {

	/**
	 * Map agent to current location It is updated every time agent migrates
	 */
	Map<Integer, Integer> agentToAddressDatabase;

	/**
	 * @param communicationHost - the CommunicatingAgent that will use this protocol
	 */
	public HSSHomeAgentHost(CommunicatingHostInterface communicationHost) {
		super(communicationHost.getId(), communicationHost, communicationHost.getProtocol());
	}

	@Override
	public void interpretMessage(MessageInterface message) {
		if (message instanceof AgentCommunicationMessageInterface) {
			AgentCommunicationMessageInterface agentCommunicationMessage = (AgentCommunicationMessageInterface) message;
			Integer agentDestinationId = agentCommunicationMessage.getAgentDestinationId();
			CommunicatingHostInterface communicationHost = getCommunicationHost();

			if (agentToAddressDatabase.containsKey(agentDestinationId)) {
				Integer hostDestination = agentToAddressDatabase.get(agentDestinationId);
				if (!hostDestination.equals(communicationHost.getId())) {
					communicationHost.reRouteMessage(agentCommunicationMessage, hostDestination);
					communicationHost.addMessageForSending(agentCommunicationMessage);
					return;
				}
			}
			super.interpretMessage(agentCommunicationMessage);

		} else if (message instanceof LocationUpdateMessageInterface) {
			LocationUpdateMessageInterface locationUpdateMessage = (LocationUpdateMessageInterface) message;
			Integer newInhabitingHostId = locationUpdateMessage.getNewHostId();
			Integer agentSourceId = locationUpdateMessage.getAgentId();
			agentToAddressDatabase.put(agentSourceId, newInhabitingHostId);
		} else {
			super.interpretMessage(message);
		}
	}

	@Override
	public void init(Map<String, String> protocolArguments) {
		agentToAddressDatabase = new HashMap<Integer, Integer>();
		CommunicatingHostInterface communicationHost = getCommunicationHost();
		Collection<CommunicatingAgentInterface> activeAgents = communicationHost.getActiveAgents();
		for (CommunicatingAgentInterface communicatingAgentInterface : activeAgents) {
			agentToAddressDatabase.put(communicatingAgentInterface.getId(), communicationHost.getId());
		}
	}

}
