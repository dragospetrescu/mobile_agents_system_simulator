package host.communication;

import agent.communication.CommunicatingAgentInterface;
import message.MessageInterface;
import protocol.Protocol;

import java.util.List;

public interface CommunicatingHostInterface {

    void receiveMessage(MessageInterface message);
    boolean wantsToSendMessage();
    void addMessageForSending(MessageInterface message);
    Protocol getProtocol();
    List<MessageInterface> sendMessages();
    
    List<CommunicatingAgentInterface> getActiveAgents();
    void addAgent(CommunicatingAgentInterface agent);

    CommunicatingHostInterface getNextHop(CommunicatingHostInterface destinationHost);
	int getId();
	void addRouteNextHop(CommunicatingHostInterface destinationRouter, CommunicatingHostInterface nextHopRouter);
	void setProtocolHost();
}
