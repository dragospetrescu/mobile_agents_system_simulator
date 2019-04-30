package core.message;

import core.agent.communication.CommunicatingAgentInterface;

/**
 * Protocol independent message All agents will migrate using this kind of
 * messages.
 */
public interface MigratingAgentMessageInterface extends MessageInterface {

	/**
	 * @return the reference of the agent that is migrating
	 */
	public CommunicatingAgentInterface getMigratingAgent();
}
