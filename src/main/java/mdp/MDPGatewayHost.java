package mdp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import core.host.communication.CommunicatingHostInterface;
import core.host.protocol.AbstractProtocolHost;
import core.message.AgentCommunicationMessageInterface;
import core.message.MessageInterface;

public class MDPGatewayHost extends AbstractProtocolHost {

	private Integer gatewayHostId;
	private List<Integer> directChildernMDPHostIds;
	private Map<Integer, Integer> mapHostIdToGateway;
	private Map<Integer, Integer> mapAgentIdToHostId;

	public MDPGatewayHost(CommunicatingHostInterface communicationHost) {
		super(communicationHost.getId(), communicationHost, communicationHost.getProtocol());
	}

	@Override
	public void interpretMessage(MessageInterface message) {
		CommunicatingHostInterface communicationHost = getCommunicationHost();
		if (message instanceof MDPMigratingMessage) {
			MDPMigratingMessage migratingMessage = (MDPMigratingMessage) message;
			Integer destinationHostId = migratingMessage.getFinalDestination();
			Integer migratingAgentId = migratingMessage.getMigratingAgent().getId();
			if (directChildernMDPHostIds.contains(destinationHostId)) {
				communicationHost.reRouteMessage(migratingMessage, destinationHostId);
				mapAgentIdToHostId.put(migratingAgentId, destinationHostId);
			} else {
				if (mapHostIdToGateway.containsKey(destinationHostId)) {
					Integer nextGateway = mapHostIdToGateway.get(destinationHostId);
					communicationHost.reRouteMessage(migratingMessage, nextGateway);
					mapAgentIdToHostId.put(migratingAgentId, nextGateway);
				} else {
					mapAgentIdToHostId.remove(migratingAgentId);
					communicationHost.reRouteMessage(migratingMessage, gatewayHostId);
				}
			}
			communicationHost.addMessageForSending(migratingMessage);
		} else if (message instanceof AgentCommunicationMessageInterface) {
			AgentCommunicationMessageInterface commMessage = (AgentCommunicationMessageInterface) message;
			Integer agentDestinationId = commMessage.getAgentDestinationId();
			if (mapAgentIdToHostId.containsKey(agentDestinationId)) {
				communicationHost.reRouteMessage(commMessage, mapAgentIdToHostId.get(agentDestinationId));
			} else {
				communicationHost.reRouteMessage(commMessage, gatewayHostId);
			}
			communicationHost.addMessageForSending(commMessage);
		} else if (message instanceof MDPLocationUpdateMessage) {
			MDPLocationUpdateMessage locUpdateMessage = (MDPLocationUpdateMessage) message;
			List<Integer> agents = locUpdateMessage.getAgents();
			Integer mdpSenderHostId = locUpdateMessage.getMdpSenderHostId();
			Integer hostSourceId = locUpdateMessage.getHostSourceId();
			
			if(mdpSenderHostId.equals(hostSourceId)) {
				directChildernMDPHostIds.add(mdpSenderHostId);
			}
			
			mapHostIdToGateway.put(mdpSenderHostId, hostSourceId);
			for (Integer agentId : agents) {
				mapAgentIdToHostId.put(agentId, hostSourceId);
			}
			
			if(gatewayHostId != null) {
				communicationHost.reRouteMessage(locUpdateMessage, gatewayHostId);
				communicationHost.addMessageForSending(locUpdateMessage);
			}
			
		} else {
			super.interpretMessage(message);
		}
	}

	@Override
	public void init(Map<String, String> protocolArguments) {
		directChildernMDPHostIds = new ArrayList<>();
		mapHostIdToGateway = new HashMap<Integer, Integer>();
		mapAgentIdToHostId = new HashMap<Integer, Integer>();
		
		if(protocolArguments.containsKey("gateway"))
			gatewayHostId = Integer.parseInt(protocolArguments.get("gateway"));
	}

}
