package protocol;

import agent.communication.CommunicatingAgent;
import agent.protocol.ProtocolAgent;
import broadcast.BroadcastAgent;
import broadcast.BroadcastHost;
import host.communication.CommunicatingHost;
import host.protocol.ProtocolHost;
import hss.HSSServerHost;
import hss.HSSAgent;
import hss.HSSHost;
import test_protocol.agent.TestAgent;
import test_protocol.host.TestHost;

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
	HSS,
	HSSServer
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
			return new HSSHost(communicationHost);
		case HSSServer:
			return new HSSServerHost(communicationHost);
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
		}
		throw new RuntimeException("NO PROTOCOL AGENT CREATED FOR " + this + " " + communicatingAgent);
	}

	@Override
	public String toString() {
		return name();
	}
}
