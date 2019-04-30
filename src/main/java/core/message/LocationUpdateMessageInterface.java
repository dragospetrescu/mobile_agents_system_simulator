package core.message;

import protocol.Protocol;

/**
 * Interface for LocationUpdateMessage. This message transmits that a certain
 * agent changed it's current location.
 */
public interface LocationUpdateMessageInterface extends MessageInterface {

	/**
	 * @return the id of the agent that is changing location
	 */
	public Integer getAgentId();

	/**
	 * @return the protocol of the agent that is changing location
	 */
	public Protocol getProtocol();

	/**
	 * @return the id of the host to which the agent is moving
	 */
	public Integer getNewHostId();
}
