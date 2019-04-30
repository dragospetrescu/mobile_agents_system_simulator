package core.message;

import protocol.Protocol;

/**
 * This message transmits that a certain agent changed it's current location.
 */
public class LocationUpdateMessage extends Message implements LocationUpdateMessageInterface {

	/**
	 * Id of the migrating agent
	 */
	private Integer agentId;
	/**
	 * Id of the host to which it is migrating
	 */
	private Integer newHostId;
	/**
	 * Protocol of the agent that sent this message
	 */
	private Protocol protocol;

	/**
	 * @param sourceHost - the host from which it is migrating & sending the message
	 * @param destinationHost - the host to which the message has to arrive
	 * @param agentId - the agent that is migrating
	 * @param newHostId - the host to which the agent is migrating
	 * @param protocol - the protocol of the agent which is migrating
	 */
	public LocationUpdateMessage(Integer sourceHost, Integer destinationHost, Integer agentId, Integer newHostId, Protocol protocol) {
		super(sourceHost, destinationHost);
		this.agentId = agentId;
		this.newHostId = newHostId;
		this.protocol = protocol;
	}

	public Integer getAgentId() {
		return agentId;
	}

	public Integer getNewHostId() {
		return newHostId;
	}

	public Protocol getProtocol() {
		return protocol;
	}
}
