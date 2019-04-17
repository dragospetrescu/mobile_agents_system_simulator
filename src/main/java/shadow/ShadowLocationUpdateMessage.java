package shadow;

import agent.communication.CommunicatingAgentInterface;
import host.communication.CommunicatingHostInterface;
import message.Message;

/**
 * The Update message sent when agent migrates
 * 
 * When agent migrates more than ttl times it sends this message to the home
 * server that updates the location database
 */
public class ShadowLocationUpdateMessage extends Message {

	/**
	 * The host to which the agent is traveling.
	 * This host will become the first one in the migrating path
	 */
	private CommunicatingHostInterface migratingToHost;

	/**
	 * @param sourceHost - host from where the message is sent
	 * @param destinationHost - the host where the message has to arrive
	 * @param sourceAgent - the agent that sends the message
	 * @param migratingToHost - the host to which the agent is migrating
	 */
	public ShadowLocationUpdateMessage(CommunicatingHostInterface sourceHost,
			CommunicatingHostInterface destinationHost, CommunicatingAgentInterface sourceAgent,
			CommunicatingHostInterface migratingToHost) {
		super(sourceHost, destinationHost, sourceAgent, null);
		this.migratingToHost = migratingToHost;
	}

	/**
	 * @return This host that will become the first one in the migrating path of the agent
	 */
	public CommunicatingHostInterface getMigratingToHost() {
		return migratingToHost;
	}
}
