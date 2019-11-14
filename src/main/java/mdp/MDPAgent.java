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

import core.agent.communication.CommunicatingAgentInterface;
import core.agent.protocol.AbstractProtocolAgent;
import core.message.AgentCommunicationMessage;
import core.message.AgentCommunicationMessageInterface;
import core.message.MigratingAgentMessageInterface;

@SuppressWarnings("javadoc")
public class MDPAgent extends AbstractProtocolAgent {
	//#TODO
	public MDPAgent(CommunicatingAgentInterface communicatingAgent) {
		super(communicatingAgent.getId(), communicatingAgent, communicatingAgent.getProtocol());
	}

	@Override
	public void prepareMessageTo(Integer destinationAgentId) {
		MDPHost protocolHost = (MDPHost) getProtocolHost();
		Integer gatewayHostId = protocolHost.getGatewayHostId();
		CommunicatingAgentInterface communicatingAgent = getCommunicatingAgent();
		Integer sourceAgentId = communicatingAgent.getId();
		Integer sourceHostId = communicatingAgent.getHostId();

		AgentCommunicationMessageInterface message = new AgentCommunicationMessage(sourceHostId, gatewayHostId,
				sourceAgentId, destinationAgentId);
		protocolHost.sendMessage(message);
	}

	@Override
	public void migrate(Integer destinationHostId, MigratingAgentMessageInterface migratingMessage) {
		MDPHost protocolHost = (MDPHost)getProtocolHost();
		Integer gatewayHostId = protocolHost.getGatewayHostId();
		CommunicatingAgentInterface communicatingAgent = getCommunicatingAgent();
		Integer sourceHostId = communicatingAgent.getHostId();
		Integer finalDestinationHostId = migratingMessage.getHostDestinationId();
		CommunicatingAgentInterface migratingAgent = migratingMessage.getMigratingAgent();
		
		MDPMigratingMessage message = new MDPMigratingMessage(sourceHostId, gatewayHostId, finalDestinationHostId, migratingAgent);
		protocolHost.sendMessage(message);
	}
}
