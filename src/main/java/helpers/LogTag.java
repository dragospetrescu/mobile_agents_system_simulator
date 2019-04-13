package helpers;

/**
 * Marks the part of the functionality logged.
 * Used to filter logs by functionality
 */
public enum LogTag {
	/**
	 * Agents initialization
	 */
	AGENT_INIT,
	/**
	 * Hosts initializations
	 */
	HOST_INIT,
	/**
	 * Graph initialization 
	 */
	GRAPH_INIT,
	/**
	 * Message routing from host to host to the destination host 
	 */
	MESSAGE_ROUTING,
	/**
	 * Agent migration from one host to another host 
	 */
	AGENT_MIGRATING,
	/**
	 * Logging about normal communication messages
	 */
	NORMAL_MESSAGE
}
