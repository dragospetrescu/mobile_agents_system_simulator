package mdp;

import core.agent.communication.CommunicatingAgentInterface;
import core.agent.protocol.AbstractProtocolAgent;
import core.message.AgentCommunicationMessage;
import core.message.AgentCommunicationMessageInterface;
import core.message.MigratingAgentMessageInterface;

public class MDPAgent extends AbstractProtocolAgent {

	public MDPAgent(CommunicatingAgentInterface communicatingAgent) {
		super(communicatingAgent.getId(), communicatingAgent, communicatingAgent.getProtocol());
	}

	@Override
	public void prepareMessageTo(Integer destinationAgentId) {
		MDPHost protocolHost = (MDPHost) getProtocolHost();
		Integer gatewayHostId = protocolHost.getGatewayHostId();
		CommunicatingAgentInterface communicatingAgent = getCommunicatingAgent();
		Integer sourceAgentId = communicatingAgent.getId();
		Integer sourceHostId = communicatingAgent.getHostId();

		AgentCommunicationMessageInterface message = new AgentCommunicationMessage(sourceHostId, gatewayHostId,
				sourceAgentId, destinationAgentId);
		protocolHost.sendMessage(message);
	}

	@Override
	public void migrate(Integer destinationHostId, MigratingAgentMessageInterface migratingMessage) {
		MDPHost protocolHost = (MDPHost)getProtocolHost();
		Integer gatewayHostId = protocolHost.getGatewayHostId();
		CommunicatingAgentInterface communicatingAgent = getCommunicatingAgent();
		Integer sourceHostId = communicatingAgent.getHostId();
		Integer finalDestinationHostId = migratingMessage.getHostDestinationId();
		CommunicatingAgentInterface migratingAgent = migratingMessage.getMigratingAgent();
		
		MDPMigratingMessage message = new MDPMigratingMessage(sourceHostId, gatewayHostId, finalDestinationHostId, migratingAgent);
		protocolHost.sendMessage(message);
	}
}
