package message.implementation;

import agent.Agent;
import host.Host;
import message.Message;

public class MigratingAgentMessage extends Message {

    private Agent migratingAgent;

    public MigratingAgentMessage(Host sourceHost, Host destinationHost, Agent migratingAgent) {
        super(sourceHost, destinationHost, null, null);
        this.migratingAgent = migratingAgent;
    }

    public Agent getMigratingAgent() {
        return migratingAgent;
    }
}
