package cs;

import core.host.communication.CommunicatingHostInterface;
import core.host.protocol.AbstractProtocolHost;

/**
 * Dummy CSHost. Does not have anny functionality
 */
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
