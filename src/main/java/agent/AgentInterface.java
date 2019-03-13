package agent;

import host.Host;
import message.Message;

import java.util.List;

public interface AgentInterface {

    Host getHost();
    void work();
    boolean wantsToMigrate();
    void receiveMessage(Message message);
    boolean wantsToSendMessage();
    List<Message> sendMessages();
    Message prepareMigratingMessage();

}
