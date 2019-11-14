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
package shadow;

import java.util.HashMap;
import java.util.Map;

import core.host.communication.CommunicatingHostInterface;
import core.host.protocol.AbstractProtocolHost;
import core.message.AgentCommunicationMessageInterface;
import core.message.LocationUpdateMessageInterface;
import core.message.MessageInterface;

/**
 * The ShadowHost from the Shadow documentation
 * 
 * It keeps a map agent -> host location
 * 
 * When agent migrates it keeps remembers where that agent left to so any
 * message can follow the exact path of the agent.
 */
public class ShadowHost extends AbstractProtocolHost {
	/**
	 * Map agent to the host to which the agent migrated after it left this host
	 */
	private Map<Integer, Integer> agentForwardingProxy;

	/**
	 * @param communicationHost - the CommunicatingAgent that will use this protocol
	 */
	public ShadowHost(CommunicatingHostInterface communicationHost) {
		super(communicationHost.getId(), communicationHost, communicationHost.getProtocol());
	}

	@Override
	public void init(Map<String, String> protocolArguments) {
		agentForwardingProxy = new HashMap<Integer, Integer>();
	}

	@Override
	public void interpretMessage(MessageInterface message) {
		if (message instanceof AgentCommunicationMessageInterface) {
			
			AgentCommunicationMessageInterface agentCommunicationMessage = (AgentCommunicationMessageInterface) message;
			Integer agentDestinationId = agentCommunicationMessage.getAgentDestinationId();
			CommunicatingHostInterface communicationHost = getCommunicationHost();
			
			if(communicationHost.hasAgentWithId(agentDestinationId)) {
				super.interpretMessage(agentCommunicationMessage);
				return;
			}

			if (agentForwardingProxy.containsKey(agentDestinationId)) {
				
				Integer hostDestination = agentForwardingProxy.get(agentDestinationId);
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
			agentForwardingProxy.put(agentSourceId, newInhabitingHostId);
		} else {
			super.interpretMessage(message);
		}
	}

	/**
	 * @param agent we are currently trying to find
	 * @return the host to which the agent migrated when it left this host
	 */
	public Integer getProxy(Integer agent) {
		return agentForwardingProxy.get(agent);
	}

	/**
	 * Agent is going to migrate to host so it leaves behind some breadcrumbs so the
	 * messages can follow him.
	 * 
	 * @param agent - migrating agent
	 * @param host  - new host of agent
	 */
	public void updateProxy(Integer agent, Integer host) {
		agentForwardingProxy.put(agent, host);
	}
}
