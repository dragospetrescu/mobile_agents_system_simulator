package ramdp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import core.host.communication.CommunicatingHostInterface;
import core.host.protocol.AbstractProtocolHost;
import core.message.AgentCommunicationMessageInterface;
import core.message.MessageInterface;

/**
 * Host implementation of the Broadcast Protocol If this host does not contain
 * the destination agent of the message it discards the message
 */
public class RAMDPRegionServer extends AbstractProtocolHost {

	/**
	 * Holds messages that will be delivered
	 */
	Map<Integer, List<MessageInterface>> blackboard;

	/**
	 * @param communicationHost - the CommunicatingAgent that will use this protocol
	 */
	public RAMDPRegionServer(CommunicatingHostInterface communicationHost) {
		super(communicationHost.getId(), communicationHost, communicationHost.getProtocol());
	}

	@Override
	public void interpretMessage(MessageInterface message) {
		
		if (message instanceof RAMDPSyncMessage) {
			RAMDPSyncMessage locationMessage = (RAMDPSyncMessage) message;
			Integer agentId = locationMessage.getSourceAgentId();
			Integer newHostId = locationMessage.getHostSourceId();
			
			List<MessageInterface> messages = blackboard.get(agentId);
			locationMessage.setMessages(messages);
			blackboard.put(agentId, new ArrayList<>());
			
			CommunicatingHostInterface communicationHost = getCommunicationHost();
			communicationHost.reRouteMessage(locationMessage, newHostId);
			communicationHost.addMessageForSending(locationMessage);
			
		} else if(message instanceof AgentCommunicationMessageInterface){
			AgentCommunicationMessageInterface commMessage = (AgentCommunicationMessageInterface) message;
			Integer agentDestinationId = commMessage.getAgentDestinationId();
			
			if(!blackboard.containsKey(agentDestinationId))
				blackboard.put(agentDestinationId, new ArrayList<MessageInterface>());
			List<MessageInterface> agentMessages = blackboard.get(agentDestinationId);
			agentMessages.add(commMessage);
		} else {
			super.interpretMessage(message);
		}
	}

	@Override
	public void init(Map<String, String> protocolArguments) {
		blackboard = new HashMap<>();
	}
}
