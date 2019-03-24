package test_protocol.host;

import host.communication.CommunicatingHostInterface;
import host.protocol.ProtocolHost;
import protocol.Protocol;

public class TestHost extends ProtocolHost {

	public TestHost(int id, CommunicatingHostInterface communicationHost, Protocol protocol) {
		super(id, communicationHost, protocol);
	}
}
