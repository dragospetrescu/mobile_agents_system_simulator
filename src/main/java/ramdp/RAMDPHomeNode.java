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

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import core.agent.communication.CommunicatingAgentInterface;
import core.agent.protocol.ProtocolAgent;
import core.host.communication.CommunicatingHostInterface;
import core.host.protocol.AbstractProtocolHost;
import core.message.AgentCommunicationMessageInterface;
import core.message.LocationUpdateMessage;
import core.message.LocationUpdateMessageInterface;
import core.message.MessageInterface;
import core.message.MigratingAgentMessageInterface;
import core.simulation.Simulation;
import core.statistics.Statistics;
import mefs.MEFSSyncMessage;
import protocol.Protocol;

/**
 * @author dragos
 *
 */
public class RAMDPHomeNode extends AbstractProtocolHost {

	/**
	 * 
	 */
	private Map<Integer, Integer> agentToRS;
	private Map<CommunicatingAgentInterface, MigratingAgentMessageInterface> agentsWaitingForSynch;
	private Integer RSId;

	/**
	 * @param communicationHost - the CommunicatingAgent that will use this protocol
	 */
	public RAMDPHomeNode(CommunicatingHostInterface communicationHost) {
		super(communicationHost.getId(), communicationHost, communicationHost.getProtocol());
	}

	@Override
	public void init(Map<String, String> protocolArguments) {
		agentToRS = new HashMap<Integer, Integer>();
		agentsWaitingForSynch = new HashMap<CommunicatingAgentInterface, MigratingAgentMessageInterface>();
		
		RSId = Integer.parseInt(protocolArguments.get("rs"));
		
	}

	@Override
	public void interpretMessage(MessageInterface message) {
		if (message instanceof AgentCommunicationMessageInterface) {
			AgentCommunicationMessageInterface commMessage = (AgentCommunicationMessageInterface) message;
			Integer agentDestinationId = commMessage.getAgentDestinationId();
			if(!agentToRS.containsKey(agentDestinationId)) {
				System.out.println("MERE VEERZI");
				message.setEndTravelingStep(Simulation.step);
				Statistics statistics = Statistics.getStatistics();
				statistics.messageFailedDelivered(message);
			}
			Integer rs = agentToRS.get(agentDestinationId);
			CommunicatingHostInterface communicationHost = getCommunicationHost();
			communicationHost.reRouteMessage(commMessage, rs);
			communicationHost.addMessageForSending(commMessage);

		} else if (message instanceof LocationUpdateMessageInterface) {
			LocationUpdateMessageInterface locMessage = (LocationUpdateMessageInterface) message;
			Integer agentId = locMessage.getAgentId();
			Integer rsID = locMessage.getNewHostId();
			agentToRS.put(agentId, rsID);

		} else if (message instanceof RAMDPSyncMessage) {
			RAMDPSyncMessage synchMessage = (RAMDPSyncMessage) message;
			Integer sourceAgentId = synchMessage.getSourceAgentId();
			Optional<CommunicatingAgentInterface> optionalAgent = agentsWaitingForSynch.keySet().stream()
					.filter(agent -> agent.getId().equals(sourceAgentId)).findFirst();
			if (!optionalAgent.isPresent())
				System.out.println("WHHAATT?????????");
			CommunicatingAgentInterface agent = optionalAgent.get();
			List<MessageInterface> messages = synchMessage.getMessages();
			if (messages != null)
				for (MessageInterface messageInterface : messages) {
					agent.getProtocolAgent().receiveMessage(messageInterface);
				}
			MigratingAgentMessageInterface migratingMessage = agentsWaitingForSynch.remove(agent);
			super.interpretMessage(migratingMessage);
		} else if (message instanceof MigratingAgentMessageInterface) {
			MigratingAgentMessageInterface migratingMessage = (MigratingAgentMessageInterface) message;
			CommunicatingAgentInterface migratingAgent = migratingMessage.getMigratingAgent();
			agentsWaitingForSynch.put(migratingAgent, migratingMessage);
			CommunicatingHostInterface communicationHost = getCommunicationHost();
			RAMDPSyncMessage synchMessage = new RAMDPSyncMessage(communicationHost.getId(), RSId,
					migratingAgent.getId());
			communicationHost.routeMessage(synchMessage);
			communicationHost.addMessageForSending(synchMessage);
			RAMDPAgent protocolAgent = (RAMDPAgent)migratingAgent.getProtocolAgent();
			
			LocationUpdateMessageInterface locationMessage = new LocationUpdateMessage(communicationHost.getId(), 
					protocolAgent.getHN(), migratingAgent.getId(), RSId, Protocol.RAMDP);
			communicationHost.routeMessage(locationMessage);
			communicationHost.addMessageForSending(locationMessage);
		} else {
			super.interpretMessage(message);
		}
	}
	
	public void setRS(Integer agentID) {
		agentToRS.put(agentID, RSId);
	}
	
}
