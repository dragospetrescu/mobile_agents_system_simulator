package message;

public interface MessageInterface {
	/**
	 * @return next host to the final destination. Like in the ip protocol
	 */
	int getNextHopId();

	/**
	 * @return the last host that redirected the message
	 */
	int getPreviousHopId();

	/**
	 * @return the final host destination
	 */
	int getHostDestinationId();
	
	/**
	 * @return the host from which the message was sent
	 */
	int getHostSourceId();
	
	/**
	 * @return unique id of the message
	 */
	int getMessageId();

	void route(int nextHopHostId);
	
	void route(int nextHopHostId, int newHostDestinationId);
	
}
