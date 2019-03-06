package host;

import agent.Agent;
import message.Message;

import java.util.List;

public interface HostInterface {

    void receiveMessage(Message message);
    boolean wantsToSendMessage();
    Message prepareMessage();
    List<Agent> getActiveAgents();
    void addAgent(Agent agent);
}
