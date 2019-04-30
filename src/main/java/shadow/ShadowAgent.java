package shadow;

import java.util.Map;

import agent.communication.CommunicatingAgentInterface;
import agent.protocol.AbstractProtocolAgent;
import host.communication.CommunicatingHostInterface;
import message.AgentCommunicationMessageInterface;
import protocol.Protocol;

/**
 * The Agent from the Shadow documentation
 * 
 * The agent migrates ttl times and leaving to the current host a 
 * proxy to it's new host.
 * After the ttl'nth migration agent sends an update migration start
 * host to the home server
 */
public class ShadowAgent extends AbstractProtocolAgent {

	/**
	 * Server that has mapped agent to start of the migration path
	 */
	private CommunicatingHostInterface homeServerHost;
	/**
	 * Time to live - migrations until the home server receives update about the
	 * agent's migration decision
	 */
	private Integer ttl;

	/**
	 * @param communicatingAgent that is going to use this protocol
	 */
	public ShadowAgent(CommunicatingAgentInterface communicatingAgent) {
		super(communicatingAgent.getId(), communicatingAgent, communicatingAgent.getProtocol());
	}

	@Override
	public void prepareMessageTo(CommunicatingAgentInterface destinationAgent) {
		CommunicatingAgentInterface communicatingAgent = getCommunicatingAgent();
		CommunicatingHostInterface sourceHost = communicatingAgent.getHost();

		ShadowForwardedMessage forwardedMessage = new ShadowForwardedMessage(sourceHost, homeServerHost,
				communicatingAgent, destinationAgent);
		communicatingAgent.addMessage(forwardedMessage);
	}

	@Override
	public void migrate(CommunicatingHostInterface destinationHost) {
		ttl--;
		CommunicatingAgentInterface sourceAgent = getCommunicatingAgent();
		if (ttl <= 0) {
			CommunicatingHostInterface sourceHost = sourceAgent.getHost();
			AgentCommunicationMessageInterface message = new ShadowLocationUpdateMessage(sourceHost, homeServerHost, sourceAgent,
					destinationHost);
			ShadowHost protocolHost = (ShadowHost) sourceAgent.getHost().getProtocolHost(Protocol.Shadow);
			protocolHost.updateProxy(sourceAgent, homeServerHost);
			sourceAgent.addMessage(message);
		} else {
			ShadowHost protocolHost = (ShadowHost) sourceAgent.getHost().getProtocolHost(Protocol.Shadow);
			protocolHost.updateProxy(sourceAgent, destinationHost);
		}
	}

	@Override
	public void init(Map<String, String> protocolArguments) {
		ttl = Integer.parseInt(protocolArguments.get("ttl"));
		Integer homeServerHostId = Integer.parseInt(protocolArguments.get("homeServerHost"));
		homeServerHost = getCommunicatingAgent().getHost().getHostById(homeServerHostId);
	}

}
