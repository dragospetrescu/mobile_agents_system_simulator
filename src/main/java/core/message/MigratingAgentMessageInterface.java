package core.message;

import core.agent.communication.CommunicatingAgentInterface;

public interface MigratingAgentMessageInterface extends MessageInterface {

	public CommunicatingAgentInterface getMigratingAgent();
}
