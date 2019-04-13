package message.implementation;

import agent.communication.CommunicatingAgentInterface;
import host.communication.CommunicatingHostInterface;
import message.Message;

/**
 * Protocol independent message
 * All agents will migrate using this kind of messages.
 */
public class MigratingAgentMessage extends Message {

    /**
     * The agent that is migrating from the sourceHost to the destinationHost
     */
    private CommunicatingAgentInterface migratingAgent;
    
	/**
	 * @param sourceHost - host from where the message is sent
	 * @param destinationHost - the host where the message has to arrive
	 * @param migratingAgent - the agent that is migrating from the sourceHost to the destinationHost
	 */
    public MigratingAgentMessage(CommunicatingHostInterface sourceHost, CommunicatingHostInterface destinationHost, 
    		CommunicatingAgentInterface migratingAgent) {
        super(sourceHost, destinationHost, null, null);
        this.migratingAgent = migratingAgent;
    }

    /**
     * @return the agent that is migrating from the sourceHost to the destinationHost
     */
    public CommunicatingAgentInterface getMigratingAgent() {
        return migratingAgent;
    }
}
