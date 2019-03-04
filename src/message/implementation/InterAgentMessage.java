package message.implementation;

import agent.implementation.Agent;
import host.implementation.Host;
import message.Message;

public class InterAgentMessage extends Message {

    private Agent destinationAgent;
    private Agent sourceAgent;



    public InterAgentMessage(int id, Host currentHost, Host destinationHost, Agent destinationAgent, Agent sourceAgent) {
        super(id, currentHost, destinationHost);
        this.destinationAgent = destinationAgent;
        this.sourceAgent = sourceAgent;
    }

    public InterAgentMessage(Host currentHost, Host destinationHost, Agent destinationAgent, Agent sourceAgent) {
        super(currentHost, destinationHost);
        this.destinationAgent = destinationAgent;
        this.sourceAgent = sourceAgent;
    }

    public InterAgentMessage(InterAgentMessage interAgentMessage, Host sourceHost, Host destinationHost) {
        this(sourceHost, destinationHost, interAgentMessage.getDestinationAgent(), interAgentMessage.getSourceAgent());
    }

    public Agent getDestinationAgent() {
        return destinationAgent;
    }

    public Agent getSourceAgent() {
        return sourceAgent;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
