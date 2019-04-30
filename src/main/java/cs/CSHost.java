package cs;

import core.host.communication.CommunicatingHostInterface;
import core.host.protocol.AbstractProtocolHost;
import protocol.Protocol;

public class CSHost extends AbstractProtocolHost {

	/**
	 * @param communicationHost - the CommunicatingAgent that will use this protocol
	 */
	public CSHost(CommunicatingHostInterface communicationHost) {
		super(communicationHost.getId(), communicationHost, communicationHost.getProtocol());
	}

	@Override
	public void init() {
	}

}
