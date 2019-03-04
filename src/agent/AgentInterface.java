package agent;

import agent.implementation.Agent;
import host.implementation.Host;
import message.Message;

public interface AgentInterface {

    Host getHost();
    boolean isWorking();
    boolean isMigrating();
    void receiveMessage(Message message);
    void sendMessage(Agent destinationAgent, Message message);

}
