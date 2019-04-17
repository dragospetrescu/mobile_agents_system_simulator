package protocol;

import agent.communication.CommunicatingAgent;
import agent.protocol.ProtocolAgent;
import broadcast.BroadcastAgent;
import broadcast.BroadcastHost;
import fp.FPAgent;
import fp.FPHost;
import host.communication.CommunicatingHost;
import host.protocol.ProtocolHost;
import hss.HSSHomeServerHost;
import hss.HSSAgent;
import hss.HSSHomeAgentHost;
import test_protocol.TestAgent;
import test_protocol.TestHost;

/**
 * List of available protocols If you want to add a new protocol add a new entry
 * to this list and modify the getProtocolHost and getProtocolAgent so the
 * switch will return the appropriate protocol host and protocol agent
 * 
 */
public enum Protocol {

	/**
	 * Test, should be ignored.
	 */
	TEST_PROTOCOL, 
	/**
	 * Agents broadcast all their messages.
	 */
	BROADCAST,
	/**
	 * HSS normal protocol
	 */
	HSS,
	/**
	 * Home Server protocol for HSS
	 */
	HSSServer, 
	/**
	 * Forward Proxy. Every host leaves some breadcrumbs before migrating to a new host
	 * The messages follow those breadcrumbs
	 */
	FP
	;

	/**
	 * Based on the {@link Protocol} it returns a ProtocolHost
	 * 
	 * @param communicationHost that will use the protocol. Needed for logging
	 * @return a ProtocolHost associated with the Protocol
	 */
	public ProtocolHost getProtocolHost(CommunicatingHost communicationHost) {
		switch (this) {
		case TEST_PROTOCOL:
			return new TestHost(communicationHost);
		case BROADCAST:
			return new BroadcastHost(communicationHost);
		case HSS:
			return new HSSHomeAgentHost(communicationHost);
		case HSSServer:
			return new HSSHomeServerHost(communicationHost);
		case FP:
			return new FPHost(communicationHost);
		}
		throw new RuntimeException("NO PROTOCOL HOST CREATED FOR " + this + " " + communicationHost);
	}

	/**
	 * Based on the {@link Protocol} it returns a ProtocolAgent
	 * 
	 * @param communicatingAgent that will use the protocol. Needed for logging
	 * @return a ProtocolAgent associated with the Protocol
	 */
	public ProtocolAgent getProtocolAgent(CommunicatingAgent communicatingAgent) {
		switch (this) {
		case TEST_PROTOCOL:
			return new TestAgent(communicatingAgent);
		case BROADCAST:
			return new BroadcastAgent(communicatingAgent);
		case HSS:
			return new HSSAgent(communicatingAgent);
		case HSSServer:
			throw new RuntimeException("HSSServer is not an agent Protocol!");
		case FP:
			return new FPAgent(communicatingAgent);
		}
		throw new RuntimeException("NO PROTOCOL AGENT CREATED FOR " + this + " " + communicatingAgent);
	}

	@Override
	public String toString() {
		return name();
	}
}
