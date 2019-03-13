package message.implementation;

import agent.Agent;
import host.Host;
import message.Message;

public class DummyMessage extends Message {

    public DummyMessage(Host sourceHost, Host destinationHost, Agent sourceAgent, Agent destinationAgent) {
        super(sourceHost, destinationHost, sourceAgent, destinationAgent);
    }

}
