package core.message;

import core.agent.communication.CommunicatingAgentInterface;

/**
 * Protocol independent message All agents will migrate using this kind of
 * messages.
 */
public class MigratingAgentMessage extends AbstractMessage implements MigratingAgentMessageInterface {

	/**
	 * The agent that is migrating from the sourceHost to the destinationHost
	 */
	private CommunicatingAgentInterface migratingAgent;

	/**
	 * @param sourceHost      - host from where the message is sent
	 * @param destinationHost - the host where the message has to arrive
	 * @param migratingAgent  - the agent that is migrating from the sourceHost to
	 *                        the destinationHost
	 */
	public MigratingAgentMessage(Integer sourceHost, Integer destinationHost, CommunicatingAgentInterface migratingAgent) {
		super(sourceHost, destinationHost);
		this.migratingAgent = migratingAgent;
	}

	/**
	 * @return the agent that is migrating from the sourceHost to the
	 *         destinationHost
	 */
	@Override
	public CommunicatingAgentInterface getMigratingAgent() {
		return migratingAgent;
	}
}
