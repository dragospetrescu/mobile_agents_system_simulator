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
package core.agent.protocol;

import java.util.Map;

import core.agent.communication.CommunicatingAgentInterface;
import core.host.protocol.ProtocolHost;
import core.message.MessageInterface;
import core.message.MigratingAgentMessageInterface;
import protocol.Protocol;

/**
 * ProtocolAgents can extend this class.
 */
public abstract class AbstractProtocolAgent implements ProtocolAgent {

	/**
	 * Unique identifier
	 */
	private Integer id;
	/**
	 * The {@link CommunicatingAgentInterface} that is currently using this ProtocolAgent
	 */
	private CommunicatingAgentInterface communicatingAgent;
	
	/**
	 * Gets the protocol host corresponding to:
	 * - the host it is inhabiting
	 * - the protocol it is using
	 */
	private ProtocolHost protocolHost;
	/**
	 * The protocol that it is implementing
	 */
	private Protocol protocol;
	
	/**
	 * @param id - Unique identifier
	 * @param communicatingAgent - The {@link CommunicatingAgentInterface} that will use this ProtocolAgent
	 * @param protocol - The protocol that it is implementing
	 */
	public AbstractProtocolAgent(Integer id, CommunicatingAgentInterface communicatingAgent, Protocol protocol) {
		this.id = id;
		this.communicatingAgent = communicatingAgent;
		this.protocol = protocol;
	}
	
	@Override
	public void migrate(Integer destinationHostId, MigratingAgentMessageInterface migratingMessage) {
		ProtocolHost protocolHost = getProtocolHost();
		protocolHost.sendMessage(migratingMessage);
	}
	
	@Override
	public void receiveMessage(MessageInterface message) {
		communicatingAgent.receiveMessage(message);
	}

	@Override
	public CommunicatingAgentInterface getCommunicatingAgent() {
		return communicatingAgent;
	}

	@Override
	public ProtocolHost getProtocolHost() {
		return protocolHost;
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
	public String toString() {
		return protocol + " Agent " + id;
	}
	
	@Override
	public void init(Map<String, String> protocolArguments, ProtocolHost protocolHost) {
		this.protocolHost = protocolHost;
	}
	
	@Override
	public void setProtocolHost(ProtocolHost protocolHost) {
		this.protocolHost = protocolHost;
	}
}
