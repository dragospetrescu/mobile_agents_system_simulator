package fp;

import java.util.Map;

import agent.communication.CommunicatingAgentInterface;
import agent.protocol.AbstractProtocolAgent;
import host.communication.CommunicatingHostInterface;
import message.MessageInterface;
import message.implementation.NormalCommunicationMessage;
import protocol.Protocol;

/**
 * The Agent from the FP documentation
 * 
 * Uses protocolHost forwarding proxy to find out agent location
 * When agent migrates it updates future location to its current host
 */
public class FPAgent extends AbstractProtocolAgent {

	/**
	 * @param communicatingAgent - agents that will use this protocol implementation
	 */
	public FPAgent(CommunicatingAgentInterface communicatingAgent) {
		super(communicatingAgent.getId(), communicatingAgent, communicatingAgent.getProtocol());
	}

	@Override
	public void prepareMessageTo(CommunicatingAgentInterface destinationAgent) {
		CommunicatingAgentInterface sourceAgent = getCommunicatingAgent();
		CommunicatingHostInterface sourceHost = sourceAgent.getHost();
		FPHost protocolHost = (FPHost) sourceAgent.getHost().getProtocolHost(Protocol.FP);

		CommunicatingHostInterface destinationHost = protocolHost.getProxy(destinationAgent);

		MessageInterface message = new NormalCommunicationMessage(sourceHost, destinationHost, sourceAgent,
				destinationAgent);
		sourceAgent.addMessage(message);
	}

	@Override
	public void migrate(CommunicatingHostInterface destinationHost) {
		CommunicatingAgentInterface sourceAgent = getCommunicatingAgent();
		FPHost protocolHost = (FPHost) sourceAgent.getHost().getProtocolHost(Protocol.FP);
		protocolHost.updateProxy(sourceAgent, destinationHost);
	}

	@Override
	public void init(Map<String, String> protocolArguments) {
	}

}
