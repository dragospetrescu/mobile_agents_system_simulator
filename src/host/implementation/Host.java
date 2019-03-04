package host.implementation;

import agent.implementation.Agent;
import helpers.Logger;
import message.implementation.AgentToHomeMessage;
import message.implementation.InterAgentMessage;
import message.Message;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Host {

    private int id;
    private List<Agent> presentAgents;
    private Map<Agent, Host> homeAgents;

    public Host(int id) {
        this.id = id;
        presentAgents = new ArrayList<>();
        homeAgents = new HashMap<>();
    }

    public void addAgent(Agent agent) {
        presentAgents.add(agent);
    }

    public void addHomeAgent(Agent agent) {
        addAgent(agent);
        homeAgents.put(agent, this);
    }

    public List<Agent> getActiveAgents() {
        return presentAgents;
    }

    public void removeAgent(Agent agent) {
        presentAgents.remove(agent);
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Host " + id;
    }

    public Message receiveMessage(Message message) {

        if(message instanceof InterAgentMessage) {
            InterAgentMessage interAgentMessage = (InterAgentMessage)message;
            Agent destinationAgent = interAgentMessage.getDestinationAgent();

            if (presentAgents.contains(destinationAgent)) {
                destinationAgent.receiveMessage(interAgentMessage);
                return null;
            }

            if (homeAgents.containsKey(destinationAgent)) {
                Host destinationHost = homeAgents.get(destinationAgent);
                return new InterAgentMessage(interAgentMessage, this, destinationHost);
            }

            Logger.log(interAgentMessage + " failed to be delivered!");
            return null;
        } else if (message instanceof AgentToHomeMessage){
            AgentToHomeMessage agentToHomeMessage = (AgentToHomeMessage) message;
            Host agentNextHost = agentToHomeMessage.getAgentNextHost();
            homeAgents.put(agentToHomeMessage.getSourceAgent(), agentNextHost);
            return null;
        }
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Host host = (Host) o;

        return id == host.id;
    }

    @Override
    public int hashCode() {
        return id;
    }

    public boolean wantsToSendMessage() {
        return false;
    }

    public Message prepareMessage() {
        return null;
    }
}
