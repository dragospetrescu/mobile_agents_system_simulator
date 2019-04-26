package fp;

import java.util.HashMap;
import java.util.List;

import agent.communication.CommunicatingAgentInterface;
import agent.protocol.ProtocolAgent;
import host.communication.CommunicatingHostInterface;
import host.protocol.AbstractProtocolHost;
import message.AgentCommunicationMessageInterface;
import message.NormalCommunicationMessage;

/**
 * The Host from the FP documentation
 * 
 * If host contains the message's destination agent then it receives it.
 * Otherwise it redirects the messages to the place the agent migrated when it
 * left this host.
 */
public class FPHost extends AbstractProtocolHost {

	/**
	 * Map agent to the host to which the agent migrated after it left this host
	 */
	private HashMap<CommunicatingAgentInterface, CommunicatingHostInterface> agentForwardingProxy;

	/**
	 * @param communicationHost - the CommunicatingAgent that will use this protocol
	 */
	public FPHost(CommunicatingHostInterface communicationHost) {
		super(communicationHost.getId(), communicationHost, communicationHost.getProtocol());
	}

	@Override
	public void init() {
		agentForwardingProxy = new HashMap<CommunicatingAgentInterface, CommunicatingHostInterface>();
		List<CommunicatingHostInterface> allHosts = getCommunicationHost().getAllNormalHosts();
		for (CommunicatingHostInterface host : allHosts) {
			List<CommunicatingAgentInterface> activeAgents = host.getActiveAgents();
			for (CommunicatingAgentInterface agent : activeAgents) {
				agentForwardingProxy.put(agent, host);
			}
		}
	}

	@Override
	public void interpretMessage(AgentCommunicationMessageInterface message) {
		CommunicatingAgentInterface destinationAgent = message.getAgentDestinationId();
		List<CommunicatingAgentInterface> communicatingAgents = getCommunicationHost().getActiveAgents();
		if (communicatingAgents.contains(destinationAgent)) {
			ProtocolAgent protocolAgent = destinationAgent.getProtocolAgent();
			protocolAgent.receiveMessage(message);
		} else {
			CommunicatingHostInterface newHostDestination = agentForwardingProxy.get(destinationAgent);
			CommunicatingHostInterface sourceHost = getCommunicationHost();
			CommunicatingAgentInterface sourceAgent = message.getAgentSourceId();
			AgentCommunicationMessageInterface forwardedMessage = new NormalCommunicationMessage(message.getMessageId(), sourceHost,
					newHostDestination, sourceAgent, destinationAgent);
			sourceHost.addMessageForSending(forwardedMessage);
		}

	}

	/**
	 * @param agent we are currently trying to find
	 * @return the host to which the agent migrated when it left this host
	 */
	public CommunicatingHostInterface getProxy(CommunicatingAgentInterface agent) {
		return agentForwardingProxy.get(agent);
	}

	/**
	 * Agent is going to migrate to host so it leaves behind some breadcrumbs so the
	 * messages can follow him.
	 * 
	 * @param agent - migrating agent
	 * @param host  - new host of agent
	 */
	public void updateProxy(CommunicatingAgentInterface agent, CommunicatingHostInterface host) {
		agentForwardingProxy.put(agent, host);
	}
}
