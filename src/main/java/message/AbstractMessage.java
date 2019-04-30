package message;

public class AbstractMessage implements MessageInterface {

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
	 * Used to make sure each message receives a unique id
	 */
	public static Integer noMessages = 0;

	/**
	 * Warning. You should only use this constructor if the id provided is unique
	 * 
	 * @param id               - the unique identifier
	 * @param sourceHost       - host from where the message is sent
	 * @param destinationHost  - the host where the message has to arrive
	 */
	public AbstractMessage(Integer id, Integer sourceHost, Integer destinationHost) {
		this.id = id;
		this.sourceHostId = sourceHost;
		this.destinationHostId = destinationHost;
	}

	/**
	 * Use this constructor if you don't care about the id
	 * 
	 * @param sourceHost       - host from where the message is sent
	 * @param destinationHost  - the host where the message has to arrive
	 */
	public AbstractMessage(Integer sourceHost, Integer destinationHost) {
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
		final Integer prime = 31;
		int result = 1;
		result = prime * result + destinationHostId;
		result = prime * result + id;
		result = prime * result + sourceHostId;
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
		AbstractMessage other = (AbstractMessage) obj;
		if (destinationHostId != other.destinationHostId)
			return false;
		if (id != other.id)
			return false;
		if (sourceHostId != other.sourceHostId)
			return false;
		return true;
	}

	@Override
	public void route(Integer nextHopHostId) {
		if(this.nextHopHostId == null) {
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

}
