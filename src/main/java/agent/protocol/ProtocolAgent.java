package agent.protocol;

import message.MessageInterface;

import java.util.List;

import agent.communication.CommunicatingAgentInterface;

public interface ProtocolAgent {

    void receiveMessage(MessageInterface message);
    List<MessageInterface> prepareMessages();
	CommunicatingAgentInterface getCommunicatingAgent();
	void prepareMessageTo(CommunicatingAgentInterface randomElement);

}
