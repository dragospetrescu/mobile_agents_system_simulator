package core.message;

import protocol.Protocol;

public interface LocationUpdateMessageInterface extends MessageInterface {

	public Integer getAgentId();
	public Protocol getProtocol();
	public Integer getNewHostId();
}
