package agent.communication;

import message.MessageInterface;
import java.util.List;
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
}
