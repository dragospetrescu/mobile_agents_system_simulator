package mdp;

import core.agent.communication.CommunicatingAgentInterface;
import core.message.MigratingAgentMessage;

@SuppressWarnings("javadoc")
public class MDPMigratingMessage extends MigratingAgentMessage {

	private Integer finalDestination;

	public MDPMigratingMessage(Integer sourceHost, Integer gatewayHost, Integer finalDestination,
			CommunicatingAgentInterface migratingAgent) {
		super(sourceHost, gatewayHost, migratingAgent);
		this.finalDestination = finalDestination;
	}

	public Integer getFinalDestination() {
		return finalDestination;
	}

	
	
}
