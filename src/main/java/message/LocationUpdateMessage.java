package message;

import protocol.Protocol;

public class LocationUpdateMessage extends AbstractMessage implements LocationUpdateMessageInterface {

	private int agentId;
	private int newHostId;
	private Protocol protocol;

	public LocationUpdateMessage(int sourceHost, int destinationHost, int agentId, int newHostId, Protocol protocol) {
		super(sourceHost, destinationHost);
		this.agentId = agentId;
		this.newHostId = newHostId;
		this.protocol = protocol;
	}

	public int getAgentId() {
		return agentId;
	}

	public int getNewHostId() {
		return newHostId;
	}

	public Protocol getProtocol() {
		return protocol;
	}
}
