package message;

import protocol.Protocol;

public interface LocationUpdateMessageInterface extends MessageInterface {

	public int getAgentId();
	public Protocol getProtocol();
	public int getNewHostId();
}
