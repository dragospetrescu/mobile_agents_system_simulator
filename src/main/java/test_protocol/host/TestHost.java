package test_protocol.host;

import host.communication.CommunicatingHostInterface;
import host.protocol.ProtocolHost;
import protocol.Protocol;

public class TestHost extends ProtocolHost {

	public TestHost(CommunicatingHostInterface communicationHost) {
		super(communicationHost.getId(), communicationHost, Protocol.TEST_PROTOCOL);
	}
}
