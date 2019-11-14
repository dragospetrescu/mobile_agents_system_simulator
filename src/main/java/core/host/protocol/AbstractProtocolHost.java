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
package core.host.protocol;

import java.util.List;

import core.agent.communication.CommunicatingAgentInterface;
import core.agent.protocol.ProtocolAgent;
import core.helpers.LogTag;
import core.helpers.Logger;
import core.host.communication.CommunicatingHostInterface;
import core.message.AgentCommunicationMessageInterface;
import core.message.MessageInterface;
import core.message.MigratingAgentMessageInterface;
import core.simulation.Simulation;
import core.statistics.Statistics;
import protocol.Protocol;

/**
 * ProtocolHosts can extend this class.
 */
public abstract class AbstractProtocolHost implements ProtocolHost {

	/**
	 * Unique identifier
	 */
	private Integer id;
	/**
	 * The {@link CommunicatingHostInterface} that is currently using this
	 * ProtocolHost
	 */
	private CommunicatingHostInterface communicationHost;
	/**
	 * The protocol that it is implementing
	 */
	private Protocol protocol;

	/**
	 * @param id                - Unique identifier
	 * @param communicationHost - The {@link CommunicatingHostInterface} that will
	 *                          use this ProtocolHost
	 * @param protocol          - The protocol that it is implementing
	 */
	public AbstractProtocolHost(Integer id, CommunicatingHostInterface communicationHost, Protocol protocol) {
		super();
		this.id = id;
		this.communicationHost = communicationHost;
		this.protocol = protocol;
	}

	@Override
	public void interpretMessage(MessageInterface message) {
		if (message instanceof AgentCommunicationMessageInterface) {
			AgentCommunicationMessageInterface agentCommunicationMessage = (AgentCommunicationMessageInterface) message;
			
			Integer communicatingAgentId = agentCommunicationMessage.getAgentDestinationId();
			if (communicationHost.hasAgentWithId(communicatingAgentId)) {
				ProtocolAgent protocolAgent = communicationHost.getProtocolAgentWithId(communicatingAgentId);
				protocolAgent.receiveMessage(message);
			} else {
				Logger.w(LogTag.NORMAL_MESSAGE,
						message + " did non find destination Agent " + communicatingAgentId + " at " + this);
				Statistics statistics = Statistics.getStatistics();
				message.setEndTravelingStep(Simulation.step);
				statistics.messageFailedDelivered(message);
			}
		} else if (message instanceof MigratingAgentMessageInterface) {
			MigratingAgentMessageInterface migratingAgentMessage = (MigratingAgentMessageInterface) message;
			CommunicatingAgentInterface migratingAgent = migratingAgentMessage.getMigratingAgent();
			communicationHost.addAgent(migratingAgent);
			migratingAgent.getProtocolAgent().setProtocolHost(communicationHost.getProtocolHost(migratingAgent.getProtocol()));
			Logger.i(LogTag.AGENT_MIGRATING, toString() + " received " + migratingAgent);
		}
	}

	@Override
	public CommunicatingHostInterface getCommunicationHost() {
		return communicationHost;
	}

	@Override
	public Integer getId() {
		return id;
	}

	@Override
	public Protocol getProtocol() {
		return protocol;
	}
	
	@Override
	public void sendMessage(MessageInterface message) {
		communicationHost.routeMessage(message);
		communicationHost.addMessageForSending(message);
	}

	@Override
	public String toString() {
		return protocol + " HOST " + id;
	}
	
	@Override
	public List<Integer> getAllHosts() {
		CommunicatingHostInterface communicationHost = getCommunicationHost();
		return communicationHost.getAllNormalHostsIds();
	}
}
