package cs;

import java.util.HashMap;
import java.util.Map;

import core.host.communication.CommunicatingHostInterface;
import core.host.protocol.AbstractProtocolHost;
import core.message.AgentCommunicationMessageInterface;
import core.message.LocationUpdateMessageInterface;
import core.message.MessageInterface;

/**
 * Central server from the CS protocol. Every time an agent migrates it sends a
 * location update to the server. When an agent wants to send a message to an
 * agent, it sends the message to this server which redirects it to the last
 * known host of the destination agent
 */
public class CSServerHost extends AbstractProtocolHost {

	/**
	 * Map that tracks the position of each host
	 */
	Map<Integer, Integer> agentToHostDatabase;

	/**
	 * @param communicationHost - the CommunicatingAgent that will use this protocol
	 */
	public CSServerHost(CommunicatingHostInterface communicationHost) {
		super(communicationHost.getId(), communicationHost, communicationHost.getProtocol());
	}

	@Override
	public void init(Map<String, String> protocolArguments) {
		agentToHostDatabase = new HashMap<Integer, Integer>();
	}

	@Override
	public void interpretMessage(MessageInterface message) {

		if (message instanceof AgentCommunicationMessageInterface) {
			AgentCommunicationMessageInterface agentCommunicationMessage = (AgentCommunicationMessageInterface) message;
			Integer agentDestination = agentCommunicationMessage.getAgentDestinationId();
			Integer hostDestination = agentToHostDatabase.get(agentDestination);
			CommunicatingHostInterface communicationHost = getCommunicationHost();
			communicationHost.reRouteMessage(agentCommunicationMessage, hostDestination);
			communicationHost.addMessageForSending(agentCommunicationMessage);
		} else if (message instanceof LocationUpdateMessageInterface) {
			LocationUpdateMessageInterface csMessage = (LocationUpdateMessageInterface) message;
			Integer newInhabitingHostId = csMessage.getNewHostId();
			Integer agentSource = csMessage.getAgentId();
			agentToHostDatabase.put(agentSource, newInhabitingHostId);
		} else {
			super.interpretMessage(message);
		}
	}
}
