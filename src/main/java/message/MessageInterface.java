package message;

public interface MessageInterface {
	/**
	 * @return next host to the final destination. Like in the ip protocol
	 */
	Integer getNextHopId();

	/**
	 * @return the last host that redirected the message
	 */
	Integer getPreviousHopId();

	/**
	 * @return the final host destination
	 */
	Integer getHostDestinationId();
	
	/**
	 * @return the host from which the message was sent
	 */
	Integer getHostSourceId();
	
	/**
	 * @return unique id of the message
	 */
	Integer getMessageId();

	void route(Integer nextHopHostId);
	
	void route(Integer nextHopHostId, Integer newHostDestinationId);
	
}
