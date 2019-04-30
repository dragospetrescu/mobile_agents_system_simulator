package message;

import protocol.Protocol;

public class LocationUpdateMessage extends AbstractMessage implements LocationUpdateMessageInterface {

	private Integer agentId;
	private Integer newHostId;
	private Protocol protocol;

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
