package message;

import agent.communication.CommunicatingAgentInterface;

public interface MigratingAgentMessageInterface extends MessageInterface {

	public CommunicatingAgentInterface getMigratingAgent();
}
