package fp;

import java.util.HashMap;
import java.util.List;

import agent.protocol.ProtocolAgent;
import host.communication.CommunicatingHostInterface;
import host.protocol.AbstractProtocolHost;
import message.AgentCommunicationMessageInterface;
import message.LocationUpdateMessageInterface;
import message.MessageInterface;

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
	private HashMap<Integer, Integer> agentForwardingProxy;

	/**
	 * @param communicationHost - the CommunicatingAgent that will use this protocol
	 */
	public FPHost(CommunicatingHostInterface communicationHost) {
		super(communicationHost.getId(), communicationHost, communicationHost.getProtocol());
	}

	@Override
	public void interpretMessage(MessageInterface message) {
		
		if(message instanceof LocationUpdateMessageInterface) {
			LocationUpdateMessageInterface locationUpdateMessage = (LocationUpdateMessageInterface) message;
			
			Integer agentId = locationUpdateMessage.getAgentId();
			Integer hostSourceId = locationUpdateMessage.getHostSourceId();
			agentForwardingProxy.put(agentId, hostSourceId);
			
		} else if (message instanceof AgentCommunicationMessageInterface) {
			AgentCommunicationMessageInterface agentCommunicationMessage = (AgentCommunicationMessageInterface) message;
			
			Integer destinationAgentId = agentCommunicationMessage.getAgentDestinationId();
			CommunicatingHostInterface communicationHost = getCommunicationHost();
			
			if (communicationHost.hasAgentWithId(destinationAgentId)) {
				ProtocolAgent protocolAgent = communicationHost.getProtocolAgentWithId(destinationAgentId);
				protocolAgent.receiveMessage(message);
			} else {
				Integer newHostDestinationId = agentForwardingProxy.get(destinationAgentId);
				CommunicatingHostInterface sourceHost = getCommunicationHost();
				Integer sourceAgentId = agentCommunicationMessage.getAgentSourceId();
				sourceHost.reRouteMessage(agentCommunicationMessage, newHostDestinationId);
				communicationHost.routeMessage(agentCommunicationMessage);
				communicationHost.addMessageForSending(agentCommunicationMessage);
				
			}
			
		}

	}

	/**
	 * @param agentId we are currently trying to find
	 * @return the host to which the agent migrated when it left this host
	 */
	public Integer getProxy(Integer agentId) {
		return agentForwardingProxy.get(agentId);
	}

	/**
	 * Agent is going to migrate to host so it leaves behind some breadcrumbs so the
	 * messages can follow him.
	 * 
	 * @param agentId - migrating agent
	 * @param hostId  - new host of agent
	 */
	public void updateProxy(Integer agentId, Integer hostId) {
		agentForwardingProxy.put(agentId, hostId);
	}
	
	@Override
	public void init() {
		agentForwardingProxy = new HashMap<Integer, Integer>();
	}
	
	public List<Integer> getAllNormalHosts() {
		CommunicatingHostInterface communicationHost = getCommunicationHost();
		return communicationHost.getAllNormalHostsIds();
	}
	
}
