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

import java.util.HashMap;
import java.util.Map;

import core.host.communication.CommunicatingHostInterface;
import core.host.protocol.AbstractProtocolHost;
import core.message.AgentCommunicationMessageInterface;
import core.message.LocationUpdateMessageInterface;
import core.message.MessageInterface;

/**
 * @author dragos
 *
 */
public class RAMPDLookupServer extends AbstractProtocolHost {

	/**
	 * 
	 */
	Map<Integer, Integer> agentToHN;

	/**
	 * @param communicationHost - the CommunicatingAgent that will use this protocol
	 */
	public RAMPDLookupServer(CommunicatingHostInterface communicationHost) {
		super(communicationHost.getId(), communicationHost, communicationHost.getProtocol());
	}

	@Override
	public void interpretMessage(MessageInterface message) {
		if (message instanceof AgentCommunicationMessageInterface) {
			AgentCommunicationMessageInterface commMessage = (AgentCommunicationMessageInterface) message;
			Integer agentDestinationId = commMessage.getAgentDestinationId();
			Integer hn = agentToHN.get(agentDestinationId);
			CommunicatingHostInterface communicationHost = getCommunicationHost();
			communicationHost.reRouteMessage(commMessage, hn);
			communicationHost.addMessageForSending(commMessage);

		} else if (message instanceof LocationUpdateMessageInterface) {
			LocationUpdateMessageInterface locMessage = (LocationUpdateMessageInterface) message;
			Integer agentId = locMessage.getAgentId();
			Integer hnID = locMessage.getNewHostId();
			agentToHN.put(agentId, hnID);

		} else {
			super.interpretMessage(message);
		}
	}

	@Override
	public void init(Map<String, String> protocolArguments) {
		agentToHN = new HashMap<Integer, Integer>();
	}
}
