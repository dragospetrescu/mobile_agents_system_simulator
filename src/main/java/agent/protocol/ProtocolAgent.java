package agent.protocol;

import message.MessageInterface;
import protocol.Protocol;

import agent.communication.CommunicatingAgentInterface;

public interface ProtocolAgent {

    void receiveMessage(MessageInterface message);
	CommunicatingAgentInterface getCommunicatingAgent();
	void prepareMessageTo(CommunicatingAgentInterface randomElement);
	int getId();
	Protocol getProtocol();

}
