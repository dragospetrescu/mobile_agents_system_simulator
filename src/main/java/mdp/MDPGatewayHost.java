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
package mdp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import core.host.communication.CommunicatingHostInterface;
import core.host.protocol.AbstractProtocolHost;
import core.message.AgentCommunicationMessageInterface;
import core.message.MessageInterface;
@SuppressWarnings("javadoc")
public class MDPGatewayHost extends AbstractProtocolHost {
//#TODO
	private Integer gatewayHostId;
	private List<Integer> directChildernMDPHostIds;
	private Map<Integer, Integer> mapHostIdToGateway;
	private Map<Integer, Integer> mapAgentIdToHostId;

	public MDPGatewayHost(CommunicatingHostInterface communicationHost) {
		super(communicationHost.getId(), communicationHost, communicationHost.getProtocol());
	}

	@Override
	public void interpretMessage(MessageInterface message) {
		CommunicatingHostInterface communicationHost = getCommunicationHost();
		if (message instanceof MDPMigratingMessage) {
			MDPMigratingMessage migratingMessage = (MDPMigratingMessage) message;
			Integer destinationHostId = migratingMessage.getFinalDestination();
			Integer migratingAgentId = migratingMessage.getMigratingAgent().getId();
			if (directChildernMDPHostIds.contains(destinationHostId)) {
				communicationHost.reRouteMessage(migratingMessage, destinationHostId);
				mapAgentIdToHostId.put(migratingAgentId, destinationHostId);
			} else {
				if (mapHostIdToGateway.containsKey(destinationHostId)) {
					Integer nextGateway = mapHostIdToGateway.get(destinationHostId);
					communicationHost.reRouteMessage(migratingMessage, nextGateway);
					mapAgentIdToHostId.put(migratingAgentId, nextGateway);
				} else {
					mapAgentIdToHostId.remove(migratingAgentId);
					communicationHost.reRouteMessage(migratingMessage, gatewayHostId);
				}
			}
			communicationHost.addMessageForSending(migratingMessage);
		} else if (message instanceof AgentCommunicationMessageInterface) {
			AgentCommunicationMessageInterface commMessage = (AgentCommunicationMessageInterface) message;
			Integer agentDestinationId = commMessage.getAgentDestinationId();
			if (mapAgentIdToHostId.containsKey(agentDestinationId)) {
				communicationHost.reRouteMessage(commMessage, mapAgentIdToHostId.get(agentDestinationId));
			} else {
				communicationHost.reRouteMessage(commMessage, gatewayHostId);
			}
			communicationHost.addMessageForSending(commMessage);
		} else if (message instanceof MDPLocationUpdateMessage) {
			MDPLocationUpdateMessage locUpdateMessage = (MDPLocationUpdateMessage) message;
			List<Integer> agents = locUpdateMessage.getAgents();
			Integer mdpSenderHostId = locUpdateMessage.getMdpSenderHostId();
			Integer hostSourceId = locUpdateMessage.getHostSourceId();
			
			if(mdpSenderHostId.equals(hostSourceId)) {
				directChildernMDPHostIds.add(mdpSenderHostId);
			}
			
			mapHostIdToGateway.put(mdpSenderHostId, hostSourceId);
			for (Integer agentId : agents) {
				mapAgentIdToHostId.put(agentId, hostSourceId);
			}
			
			if(gatewayHostId != null) {
				communicationHost.reRouteMessage(locUpdateMessage, gatewayHostId);
				communicationHost.addMessageForSending(locUpdateMessage);
			}
			
		} else {
			super.interpretMessage(message);
		}
	}

	@Override
	public void init(Map<String, String> protocolArguments) {
		directChildernMDPHostIds = new ArrayList<>();
		mapHostIdToGateway = new HashMap<Integer, Integer>();
		mapAgentIdToHostId = new HashMap<Integer, Integer>();
		
		if(protocolArguments.containsKey("gateway"))
			gatewayHostId = Integer.parseInt(protocolArguments.get("gateway"));
	}

}
