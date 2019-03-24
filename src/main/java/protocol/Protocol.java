package protocol;

import agent.communication.CommunicatingAgent;
import agent.protocol.ProtocolAgent;
import broadcast.BroadcastAgent;
import broadcast.BroadcastHost;
import host.communication.CommunicatingHost;
import host.protocol.ProtocolHostInterface;
import test_protocol.agent.TestAgent;
import test_protocol.host.TestHost;

public enum Protocol {
	
	TEST_PROTOCOL,
	BROADCAST;

	
	public ProtocolHostInterface getProtocolHost(CommunicatingHost communicationHost) {
		switch (this) {
		case TEST_PROTOCOL:
			return new TestHost(communicationHost);
		case BROADCAST:
			return new BroadcastHost(communicationHost);
		}
		throw new RuntimeException("NO PROTOCOL HOST CREATED FOR " + this + " " + communicationHost);
	}

	public ProtocolAgent getProtocolAgent(CommunicatingAgent communicatingAgent) {
		switch (this) {
		case TEST_PROTOCOL:
			return new TestAgent(communicatingAgent);
		case BROADCAST:
			return new BroadcastAgent(communicatingAgent);
		}
		throw new RuntimeException("NO PROTOCOL AGENT CREATED FOR " + this + " " + communicatingAgent);
	}
	
	@Override
	public String toString() {
		return name();
	}
}
