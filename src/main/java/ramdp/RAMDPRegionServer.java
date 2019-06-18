package ramdp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import core.agent.communication.CommunicatingAgentInterface;
import core.agent.protocol.ProtocolAgent;
import core.host.communication.CommunicatingHostInterface;
import core.host.protocol.AbstractProtocolHost;
import core.message.AgentCommunicationMessageInterface;
import core.message.LocationUpdateMessageInterface;
import core.message.MessageInterface;
import core.message.MigratingAgentMessageInterface;
import core.simulation.Simulation;
import core.statistics.Statistics;

/**
 * Host implementation of the Broadcast Protocol If this host does not contain
 * the destination agent of the message it discards the message
 */
public class RAMDPRegionServer extends AbstractProtocolHost {

	Map<Integer, List<AgentCommunicationMessageInterface>> blackboard;

	/**
	 * @param communicationHost - the CommunicatingAgent that will use this protocol
	 */
	public RAMDPRegionServer(CommunicatingHostInterface communicationHost) {
		super(communicationHost.getId(), communicationHost, communicationHost.getProtocol());
	}

	@Override
	public void interpretMessage(MessageInterface message) {
		
		if (message instanceof LocationUpdateMessageInterface) {
			super.interpretMessage(message);
			LocationUpdateMessageInterface locationMessage = (LocationUpdateMessageInterface) message;
			Integer agentId = locationMessage.getAgentId();
			
			List<AgentCommunicationMessageInterface> messages = blackboard.get(agentId);
			if(messages == null || messages.isEmpty())
				return;
			
			for (AgentCommunicationMessageInterface commMessage : messages) {
//				protocolAgent.receiveMessage(commMessage);
			}
			
		} else {
			super.interpretMessage(message);
		}
	}

	@Override
	public void init(Map<String, String> protocolArguments) {
		blackboard = new HashMap<>();
	}

	@Override
	public void sendMessage(MessageInterface message) {
		if (message instanceof AgentCommunicationMessageInterface) {
			AgentCommunicationMessageInterface commMesage = (AgentCommunicationMessageInterface) message;
			Integer agentDestinationId = commMesage.getAgentDestinationId();
			if (!blackboard.containsKey(agentDestinationId))
				blackboard.put(agentDestinationId, new ArrayList<>());
			blackboard.get(agentDestinationId).add(commMesage);
		} else {
			super.sendMessage(message);
		}

	}
}
