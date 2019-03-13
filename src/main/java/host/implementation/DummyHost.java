package host.implementation;

import agent.Agent;
import host.Host;
import message.Message;

import java.util.List;

public class DummyHost extends Host {

    public DummyHost() {
        super();
    }

    public void interpretMessage(Message message) {

        List<Agent> activeAgents = getActiveAgents();
        Agent agentDestination = message.getAgentDestination();
        Agent agentSource = message.getAgentSource();

        if(activeAgents.contains(agentDestination)) {
            agentDestination.receiveMessage(message);
        } else {
            System.out.println(agentDestination + " FAILED to receive message from " + agentSource);
        }
    }


}
