package message.implementation;

import agent.communication.CommunicatingAgentInterface;
import host.communication.CommunicatingHostInterface;
import message.Message;

public class MigratingAgentMessage extends Message {

    private CommunicatingAgentInterface migratingAgent;

    public MigratingAgentMessage(CommunicatingHostInterface sourceHost, CommunicatingHostInterface destinationHost, 
    		CommunicatingAgentInterface migratingAgent) {
        super(sourceHost, destinationHost, null, null);
        this.migratingAgent = migratingAgent;
    }

    public CommunicatingAgentInterface getMigratingAgent() {
        return migratingAgent;
    }
}
