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
package fp;

import java.util.List;
import java.util.Map;

import core.agent.communication.CommunicatingAgentInterface;
import core.agent.protocol.AbstractProtocolAgent;
import core.host.protocol.ProtocolHost;
import core.message.AgentCommunicationMessage;
import core.message.LocationUpdateMessage;
import core.message.MessageInterface;
import core.message.MigratingAgentMessageInterface;
import protocol.Protocol;

/**
 * The Agent from the FP documentation
 * 
 * Uses protocolHost forwarding proxy to find out agent location When agent
 * migrates it updates future location to its current host
 */
public class FPAgent extends AbstractProtocolAgent {

	/**
	 * @param communicatingAgent - agents that will use this protocol implementation
	 */
	public FPAgent(CommunicatingAgentInterface communicatingAgent) {
		super(communicatingAgent.getId(), communicatingAgent, communicatingAgent.getProtocol());
	}

	@Override
	public void prepareMessageTo(Integer destinationAgentId) {
		CommunicatingAgentInterface sourceAgent = getCommunicatingAgent();
		FPHost protocolHost = (FPHost) getProtocolHost();

		Integer destinationHostId = protocolHost.getProxy(destinationAgentId);

		MessageInterface message = new AgentCommunicationMessage(sourceAgent.getHostId(), destinationHostId,
				sourceAgent.getId(), destinationAgentId);
		protocolHost.sendMessage(message);
	}

	@Override
	public void migrate(Integer destinationHostId, MigratingAgentMessageInterface migratingMessage) {
		super.migrate(destinationHostId, migratingMessage);

		CommunicatingAgentInterface sourceAgent = getCommunicatingAgent();
		FPHost protocolHost = (FPHost) getProtocolHost();

		protocolHost.updateProxy(sourceAgent.getId(), destinationHostId);
	}

	@Override
	public void init(Map<String, String> protocolArguments, ProtocolHost protocolHost) {
		super.init(protocolArguments, protocolHost);
		CommunicatingAgentInterface communicatingAgent = getCommunicatingAgent();
		FPHost fpProtocolHost = (FPHost) protocolHost;
		List<Integer> allNormalHosts = fpProtocolHost.getAllHosts();
		for (Integer hostId : allNormalHosts) {
			LocationUpdateMessage message = new LocationUpdateMessage(communicatingAgent.getHostId(), hostId, getId(),
					communicatingAgent.getHostId(), Protocol.FP);
			fpProtocolHost.sendMessage(message);

		}
	}
}
