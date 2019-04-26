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
	 * @param hostDestinationId - new destination of message
	 */
	void setHostDestinationId(int hostDestinationId);

	/**
	 * @return the host from which the message was sent
	 */
	int getHostSourceId();
	
	/**
	 * @param nextHopId - the next host to which it will be redirected in order to get
	 *                to the destination.
	 */
	void setNextHopHostId(int nextHopId);

	/**
	 * @return unique id of the message
	 */
	int getMessageId();

}
