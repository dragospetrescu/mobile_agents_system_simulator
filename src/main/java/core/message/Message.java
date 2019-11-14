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
package core.message;

import core.simulation.Simulation;

/**
 * All messages need to extend this. Takes care of routing part
 */
public class Message implements MessageInterface {

	/**
	 * Unique id
	 */
	private Integer id;
	/**
	 * The host from which the message was sent
	 */
	private Integer sourceHostId;
	/**
	 * The final host destination
	 */
	private Integer destinationHostId;
	/**
	 * The last host that redirected the message
	 */
	private Integer previousHopHostId;
	/**
	 * The next host to the final destination
	 */
	private Integer nextHopHostId;

	/**
	 * Simulation step at which the traveling started
	 */
	private Integer startTravelingStep;
	/**
	 * Simulation step at which the traveling ended
	 */
	private Integer endTravelingStep;

	/**
	 * Used to make sure each message receives a unique id
	 */
	public static Integer noMessages = 0;

	/**
	 * Warning. You should only use this constructor if the id provided is unique
	 * 
	 * @param id              - the unique identifier
	 * @param sourceHost      - host from where the message is sent
	 * @param destinationHost - the host where the message has to arrive
	 * @param startTravelingStep - the step at which traveling started
	 */
	public Message(Integer id, Integer sourceHost, Integer destinationHost, Integer startTravelingStep) {
		this.id = id;
		this.sourceHostId = sourceHost;
		this.destinationHostId = destinationHost;
		this.startTravelingStep = startTravelingStep;
	}

	/**
	 * Warning. You should only use this constructor if the id provided is unique
	 * 
	 * @param id              - the unique identifier
	 * @param sourceHost      - host from where the message is sent
	 * @param destinationHost - the host where the message has to arrive
	 */
	public Message(Integer id, Integer sourceHost, Integer destinationHost) {
		this(id, sourceHost, destinationHost, Simulation.step);
	}

	/**
	 * Use this constructor if you don't care about the id
	 * 
	 * @param sourceHost      - host from where the message is sent
	 * @param destinationHost - the host where the message has to arrive
	 */
	public Message(Integer sourceHost, Integer destinationHost) {
		this(noMessages, sourceHost, destinationHost);
		noMessages++;
	}

	@Override
	public Integer getHostDestinationId() {
		return destinationHostId;
	}

	@Override
	public Integer getMessageId() {
		return id;
	}

	@Override
	public String toString() {
		return "Message " + id;
	}

	@Override
	public Integer getNextHopId() {
		return nextHopHostId;
	}

	@Override
	public Integer getPreviousHopId() {
		return previousHopHostId;
	}

	@Override
	public Integer getHostSourceId() {
		return sourceHostId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Message other = (Message) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public void route(Integer nextHopHostId) {
		if (this.nextHopHostId == null) {
			previousHopHostId = this.sourceHostId;
		} else {
			previousHopHostId = this.nextHopHostId;
		}
		this.nextHopHostId = nextHopHostId;
	}

	@Override
	public void route(Integer nextHopHostId, Integer newHostDestinationId) {
		route(nextHopHostId);
		this.sourceHostId = this.destinationHostId;
		this.destinationHostId = newHostDestinationId;
	}

	@Override
	public void setEndTravelingStep(Integer endTravelingStep) {
		this.endTravelingStep = endTravelingStep;
	}

	@Override
	public Integer getTimeSpentToFinalDestination() {
		if (endTravelingStep == null) {
			return Simulation.step - startTravelingStep;
		}
		return endTravelingStep - startTravelingStep;
	}

}
