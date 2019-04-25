package cs;

import java.util.Map;

import agent.communication.CommunicatingAgentInterface;
import agent.protocol.AbstractProtocolAgent;
import host.communication.CommunicatingHostInterface;
import hss.HSSLocationUpdateMessage;
import message.MessageInterface;
import message.implementation.NormalCommunicationMessage;

public class CSAgent extends AbstractProtocolAgent {

	private CommunicatingHostInterface serverHost;

	/**
	 * @param communicatingAgent - the CommunicatingAgent that will use this protocol
	 */
	public CSAgent(CommunicatingAgentInterface communicatingAgent) {
		super(communicatingAgent.getId(), communicatingAgent, communicatingAgent.getProtocol());
	}

	@Override
	public void prepareMessageTo(CommunicatingAgentInterface destinationAgent) {
		CommunicatingAgentInterface sourceAgent = getCommunicatingAgent();
		CommunicatingHostInterface sourceHost = sourceAgent.getHost();
		MessageInterface message = new NormalCommunicationMessage(sourceHost, serverHost, sourceAgent, destinationAgent);
		sourceAgent.addMessage(message);
	}

	@Override
	public void init(Map<String, String> protocolArguments) {
		int homeServerHostId = Integer.parseInt(protocolArguments.get("serverHost"));
		serverHost = getCommunicatingAgent().getHost().getHostById(homeServerHostId);
	}
	
	@Override
	public void migrate(CommunicatingHostInterface destinationHost) {
		CommunicatingAgentInterface communicatingAgent = getCommunicatingAgent();
		CommunicatingHostInterface sourceHost = communicatingAgent.getHost();
		MessageInterface message = new CSLocationUpdateMessage(sourceHost, serverHost, communicatingAgent, null, destinationHost);
		communicatingAgent.addMessage(message);
	
	}

}
