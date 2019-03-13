package host;

import agent.Agent;
import message.Message;

import java.util.List;

public interface HostInterface {

    void receiveMessage(Message message);
    void interpretMessage(Message message);
    boolean wantsToSendMessage();
    void addMessageForSending(Message message);
    List<Message> getMessagesToBeSent();
    List<Agent> getActiveAgents();
    void addAgent(Agent agent);
}
