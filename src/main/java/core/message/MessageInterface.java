package core.message;

/**
 * All messages need to implement this. Implementing Classes should be routed
 * successfully
 */
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

	/**
	 * @param nextHopHostId - the next hop to which the message is being routed to
	 *                      in order to reach it's current destination
	 */
	void route(Integer nextHopHostId);

	/**
	 * @param nextHopHostId        - the next hop to which the message is being
	 *                             routed to in order to reach the provided
	 *                             destination
	 * @param newHostDestinationId - the new destination of the message
	 */
	void route(Integer nextHopHostId, Integer newHostDestinationId);

	/**
	 * @param distance - distance from one hop to another 
	 */
	void addTravelingTime(long distance);
	
	void setTravelingTime(long distance);
	
	/**
	 * @return total time spent traveling to the final destination
	 */
	public long getTimeSpentToFinalDestination();

}
