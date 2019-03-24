package agent.communication;

import message.MessageInterface;
import protocol.Protocol;

import java.util.List;

import host.communication.CommunicatingHost;
import host.communication.CommunicatingHostInterface;

public interface CommunicatingAgentInterface {

    MessageInterface prepareMigratingMessage();
    CommunicatingHostInterface getHost();
    void work();
    boolean wantsToMigrate();
    boolean wantsToSendMessage();
    List<MessageInterface> sendMessages();
    void addMessage(MessageInterface message);
	void receiveMessage(MessageInterface message);
	Protocol getProtocol();
	void initAgent(List<CommunicatingAgentInterface> allAgents, List<CommunicatingHostInterface> allHosts);
	int getId();
	void setHost(CommunicatingHost communicatingHost);
}
