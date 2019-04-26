package cs;

import java.util.Map;

import agent.communication.CommunicatingAgentInterface;
import agent.protocol.AbstractProtocolAgent;
import host.communication.CommunicatingHostInterface;
import hss.HSSLocationUpdateMessage;
import message.AgentCommunicationMessageInterface;
import message.NormalCommunicationMessage;

public class CSAgent extends AbstractProtocolAgent {

	private int serverHost;

	/**
	 * @param communicatingAgent - the CommunicatingAgent that will use this protocol
	 */
	public CSAgent(CommunicatingAgentInterface communicatingAgent) {
		super(communicatingAgent.getId(), communicatingAgent, communicatingAgent.getProtocol());
	}

	@Override
	public void prepareMessageTo(int destinationAgentId) {
		CommunicatingAgentInterface sourceAgent = getCommunicatingAgent();
		CommunicatingHostInterface sourceHost = sourceAgent.getHost();
		AgentCommunicationMessageInterface message = new NormalCommunicationMessage(sourceHost.getId(), serverHost, sourceAgent.getId(), destinationAgentId);
		sourceAgent.addMessage(message);
	}

	@Override
	public void init(Map<String, String> protocolArguments) {
		int serverHost = Integer.parseInt(protocolArguments.get("serverHost"));
	}
	
	@Override
	public void migrate(int destinationHostId) {
		CommunicatingAgentInterface communicatingAgent = getCommunicatingAgent();
		CommunicatingHostInterface sourceHost = communicatingAgent.getHost();
		AgentCommunicationMessageInterface message = new CSLocationUpdateMessage(sourceHost.getId(), serverHost, communicatingAgent.getId(), destinationHostId);
		communicatingAgent.addMessage(message);
	
	}

}
