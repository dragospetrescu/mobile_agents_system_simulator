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

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import core.agent.communication.CommunicatingAgentInterface;
import core.host.communication.CommunicatingHostInterface;
import core.host.protocol.AbstractProtocolHost;
import core.message.Message;
@SuppressWarnings("javadoc")
public class MDPHost extends AbstractProtocolHost {
//#TODO
	private Integer gatewayHostId;

	public MDPHost(CommunicatingHostInterface communicationHost) {
		super(communicationHost.getId(), communicationHost, communicationHost.getProtocol());
	}

	@Override
	public void init(Map<String, String> protocolArguments) {

		gatewayHostId = Integer.parseInt(protocolArguments.get("gateway"));

		Collection<CommunicatingAgentInterface> activeAgents = getCommunicationHost().getActiveAgents();
		List<Integer> agentIds = activeAgents.stream().map(agent -> agent.getId()).collect(Collectors.toList());
		Integer sourceHostId = getCommunicationHost().getId();

		Message mdpLocationUpdateMessage = new MDPLocationUpdateMessage(sourceHostId, gatewayHostId, agentIds,
				sourceHostId);
		sendMessage(mdpLocationUpdateMessage);

	}

	public Integer getGatewayHostId() {
		return gatewayHostId;
	}
}
