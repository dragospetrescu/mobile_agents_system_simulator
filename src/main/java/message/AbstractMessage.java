package message;

public class AbstractMessage implements MessageInterface {

	/**
	 * Unique id
	 */
	private int id;
	/**
	 * The host from which the message was sent
	 */
	private int sourceHostId;
	/**
	 * The final host destination
	 */
	private int destinationHostId;
	/**
	 * The last host that redirected the message
	 */
	private int previousHopHostId;
	/**
	 * The next host to the final destination
	 */
	private int nextHopHostId;

	/**
	 * Used to make sure each message receives a unique id
	 */
	public static int noMessages = 0;

	/**
	 * Warning. You should only use this constructor if the id provided is unique
	 * 
	 * @param id               - the unique identifier
	 * @param sourceHost       - host from where the message is sent
	 * @param destinationHost  - the host where the message has to arrive
	 */
	public AbstractMessage(int id, int sourceHost, int destinationHost) {
		this.id = id;
		this.sourceHostId = sourceHost;
		this.destinationHostId = destinationHost;
//		TODO SET NEXT HOP !!!!!!!!!!
	}

	/**
	 * Use this constructor if you don't care about the id
	 * 
	 * @param sourceHost       - host from where the message is sent
	 * @param destinationHost  - the host where the message has to arrive
	 */
	public AbstractMessage(int sourceHost, int destinationHost) {
		this(noMessages, sourceHost, destinationHost);
		noMessages++;
	}

	@Override
	public int getHostDestinationId() {
		return destinationHostId;
	}

	@Override
	public int getMessageId() {
		return id;
	}

	@Override
	public String toString() {
		return "Message " + id;
	}

	@Override
	public int getNextHopId() {
		return nextHopHostId;
	}

	@Override
	public int getPreviousHopId() {
		return previousHopHostId;
	}

	@Override
	public int getHostSourceId() {
		return sourceHostId;
	}

	@Override
	public void setHostDestinationId(int destinationHostId) {
		this.destinationHostId = destinationHostId;
	}

	@Override
	public void setNextHopHostId(int nextHopId) {
//		 TODO CHECK THIS SHIT
		this.previousHopHostId = this.nextHopHostId;
		this.nextHopHostId = nextHopId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
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
		if (id != other.id)
			return false;
		return true;
	}

}
