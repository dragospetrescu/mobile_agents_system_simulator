package host.protocol;

import host.communication.CommunicatingHostInterface;
import message.MessageInterface;
import protocol.Protocol;

public interface ProtocolHostInterface {
	
    void interpretMessage(MessageInterface message);
    CommunicatingHostInterface getCommunicationHost();
    int getId();
    Protocol getProtocol();
}
