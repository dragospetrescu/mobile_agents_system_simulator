package test_protocol.host;

import host.communication.CommunicatingHostInterface;
import host.protocol.AbstractProtocolHost;
import protocol.Protocol;

/**
 * Not working. This is a test
 */
public class TestHost extends AbstractProtocolHost {

	/**
	 * @param communicationHost that is using this protocol
	 */
	public TestHost(CommunicatingHostInterface communicationHost) {
		super(communicationHost.getId(), communicationHost, Protocol.TEST_PROTOCOL);
	}

	@Override
	public void init() {
	}
}
