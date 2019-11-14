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
package mefs;

import core.message.AgentCommunicationMessage;
@SuppressWarnings("javadoc")
public class MEFSAgentCommunicationMessage extends AgentCommunicationMessage {

	private int ttl;

	public MEFSAgentCommunicationMessage(Integer sourceHost, Integer destinationHost, Integer sourceAgent,
			Integer destinationAgent, int ttl) {
		super(sourceHost, destinationHost, sourceAgent, destinationAgent);
		this.ttl = ttl;
	}

	public int getTtl() {
		return ttl;
	}

	public void decreseTtl() {
		this.ttl--;
	}
	
	

}
