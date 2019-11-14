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

import java.util.Map;

import core.agent.communication.CommunicatingAgentInterface;
import core.agent.protocol.AbstractProtocolAgent;
import core.host.protocol.ProtocolHost;
import core.message.AgentCommunicationMessage;
import core.message.AgentCommunicationMessageInterface;
import core.message.LocationUpdateMessage;
import core.message.LocationUpdateMessageInterface;
import protocol.Protocol;

/**
 * Agent implementation of the Broadcast Protocol The agent sends a message to
 * all the hosts hoping that one of them will be inhabited by the
 * destinationAgent
 */
public class RAMDPAgent extends AbstractProtocolAgent {

	private Integer hn;
	private Integer ls;

	/**
	 * @param communicatingAgent - the CommunicatingAgent that will use this
	 *                           protocol
	 */
	public RAMDPAgent(CommunicatingAgentInterface communicatingAgent) {
		super(communicatingAgent.getId(), communicatingAgent, communicatingAgent.getProtocol());
	}

	@Override
	public void prepareMessageTo(Integer destinationAgentId) {
		CommunicatingAgentInterface communicatingAgent = getCommunicatingAgent();
		ProtocolHost protocolHost = getProtocolHost();
		AgentCommunicationMessageInterface message = new AgentCommunicationMessage(communicatingAgent.getHostId(), ls,
				communicatingAgent.getId(), destinationAgentId);
		protocolHost.sendMessage(message);
	}

	@Override
	public void init(Map<String, String> protocolArguments, ProtocolHost protocolHost) {
		super.init(protocolArguments, protocolHost);
		hn = Integer.parseInt(protocolArguments.get("hn"));
		ls = Integer.parseInt(protocolArguments.get("ls"));

		RAMDPHomeNode ramdpHN = (RAMDPHomeNode) protocolHost;
		ramdpHN.setRS(getId());

		LocationUpdateMessageInterface locMessage = new LocationUpdateMessage(protocolHost.getId(), ls, getId(), hn,
				Protocol.RAMDP);
		ramdpHN.sendMessage(locMessage);
	}

	public Integer getHN() {
		return hn;
	}
}
