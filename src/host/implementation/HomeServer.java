package host.implementation;

import agent.implementation.Agent;
import message.implementation.InterAgentMessage;
import message.Message;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomeServer extends Host {

    private Map<Agent, Host> agentToHomeHostMap;

    public HomeServer(int id, List<Agent> agents, List<Host> hosts) {
        super(id);

        agentToHomeHostMap = new HashMap<>();

        for (int i = 0; i < agents.size(); i++) {
            Agent agent = agents.get(i);
            Host host = hosts.get(i);
            agentToHomeHostMap.put(agent, host);
        }

    }

    @Override
    public Message receiveMessage(Message message) {
        if (message instanceof  InterAgentMessage) {
            InterAgentMessage interAgentMessage = (InterAgentMessage)message;
            Agent destinationAgent = interAgentMessage.getDestinationAgent();
            Host destinationHost = agentToHomeHostMap.get(destinationAgent);
            return new InterAgentMessage(interAgentMessage, this, destinationHost);
        }
        return null;
    }

    @Override
    public String toString() {
        return "HomeServer";
    }
}
