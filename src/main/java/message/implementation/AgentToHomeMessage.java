package message.implementation;

import agent.Agent;
import host.Host;
import message.Message;

public class AgentToHomeMessage extends Message {

    private Host agentNextHost;
    private Agent sourceAgent;

    public AgentToHomeMessage(Host currentHost, Host destinationHost, Host agentNextHost, Agent sourceAgent) {
        super(currentHost, destinationHost);
        this.agentNextHost = agentNextHost;
        this.sourceAgent = sourceAgent;
    }

    public Host getAgentNextHost() {
        return agentNextHost;
    }

    public Agent getSourceAgent() {
        return sourceAgent;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}