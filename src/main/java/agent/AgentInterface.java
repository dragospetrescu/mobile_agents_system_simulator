package agent;

import host.Host;
import message.Message;

public interface AgentInterface {

    Host getHost();
    boolean isWorking();
    void work();
    boolean wantsToMigrate();
    void receiveMessage(Message message);
    boolean wantsToSendMessage();
    Message prepareMessage();
    Message prepareMigratingMessage();

}
