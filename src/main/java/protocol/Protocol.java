package protocol;

import agent.communication.CommunicatingAgent;
import agent.protocol.ProtocolAgent;
import host.communication.CommunicatingHost;
import host.protocol.ProtocolHostInterface;
import test_protocol.host.TestHost;

public enum Protocol {
	
	TEST_PROTOCOL;

	
	public ProtocolHostInterface getProtocolHost(CommunicatingHost communicationHost) {
		switch (this) {
		case TEST_PROTOCOL:
			return new TestHost(communicationHost);
		}
		return null;
	}

	public ProtocolAgent getProtocolAgent(CommunicatingAgent communicatingAgent) {
		switch (this) {
		case TEST_PROTOCOL:
			return null;
		}
		return null;
	}
}
