package hss;

import java.util.Map;

import agent.communication.CommunicatingAgentInterface;
import agent.protocol.AbstractProtocolAgent;
import host.communication.CommunicatingHostInterface;
import message.AgentCommunicationMessageInterface;

/**
 * The Agent from the HSS documentation
 * 
 * When agent migrates it sends to it's home host message
 * that updates the location database
 * 
 * Agent 0 wants to send message to Agent 1
 * Agent 0 sends message to HomeServer
 * HomeServer sends message to Agent 1 Home Agent
 * Home Agent sends message location of the Agent
 */
public class HSSAgent extends AbstractProtocolAgent {

	/**
	 * Server that has mapped agent to homeHost
	 */
	private CommunicatingHostInterface homeServerHost;
	/**
	 * This agent's home Host
	 */
	private CommunicatingHostInterface homeAgentHost;

	/**
	 * @param communicatingAgent - agent that is going to use this protocol implementation
	 */
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
		AgentCommunicationMessageInterface message = new HSSLocationUpdateMessage(sourceHost, homeAgentHost, communicatingAgent, null, newHost);
		communicatingAgent.addMessage(message);
	}

	@Override
	public void init(Map<String, String> protocolArguments) {
		int homeServerHostId = Integer.parseInt(protocolArguments.get("homeServerHost"));
		homeServerHost = getCommunicatingAgent().getHost().getHostById(homeServerHostId);
		homeAgentHost = getCommunicatingAgent().getHost();
	}

}
