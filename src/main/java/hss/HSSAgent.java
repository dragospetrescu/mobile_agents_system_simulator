package hss;

import java.util.Map;

import agent.communication.CommunicatingAgentInterface;
import agent.protocol.AbstractProtocolAgent;
import host.communication.CommunicatingHostInterface;
import message.MessageInterface;
import message.implementation.NormalCommunicationMessage;

public class HSSAgent extends AbstractProtocolAgent {

	private CommunicatingHostInterface homeServerHost;

	public HSSAgent(CommunicatingAgentInterface communicatingAgent) {
		super(communicatingAgent.getId(), communicatingAgent, communicatingAgent.getProtocol());
	}

	@Override
	public void prepareMessageTo(CommunicatingAgentInterface destinationAgent) {
		CommunicatingAgentInterface communicatingAgent = getCommunicatingAgent();
		CommunicatingHostInterface sourceHost = communicatingAgent.getHost();

		HSSForwardedMessage forwardedMessage = new HSSForwardedMessage(sourceHost, homeServerHost, communicatingAgent,
				destinationAgent);
		communicatingAgent.addMessage(forwardedMessage);
	}
	
	@Override
	public void migrate(CommunicatingHostInterface newHost) {
		CommunicatingAgentInterface communicatingAgent = getCommunicatingAgent();
		CommunicatingHostInterface sourceHost = communicatingAgent.getHost();
		MessageInterface message = new HSSMigratingMessage(sourceHost, homeServerHost, communicatingAgent, null, newHost);
		communicatingAgent.addMessage(message);
	}

	@Override
	public void init(Map<String, String> protocolArguments) {
		int homeServerHostId = Integer.parseInt(protocolArguments.get("homeServerHost"));
		homeServerHost = getCommunicatingAgent().getHost().getHostById(homeServerHostId);
	}

}
