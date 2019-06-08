package mefs;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import core.agent.communication.CommunicatingAgentInterface;
import core.host.communication.CommunicatingHostInterface;
import core.host.protocol.AbstractProtocolHost;
import core.message.LocationUpdateMessageInterface;
import core.message.MessageInterface;
import core.message.MigratingAgentMessageInterface;

/**
 * The HomeServer from the HSS documentation
 * 
 * It keeps a map agent -> host location
 * 
 * When agent migrates it sends to it's home host message that updates the
 * location database
 */
public class MEFSHomeAgentHost extends AbstractProtocolHost {

	/**
	 * Map agents to current location or forwarding pointer
	 */
	private Map<Integer, Integer> agentToAddressDatabase;
	private Set<Integer> homeAgencyForAgents;
	private int homeServerHostId;
	private Map<Integer, List<MessageInterface>> messagesWaitingForAgentSync;
	private Map<CommunicatingAgentInterface, MigratingAgentMessageInterface> agentsWaitingForSynch;

	/**
	 * @param communicationHost - the CommunicatingAgent that will use this protocol
	 */
	public MEFSHomeAgentHost(CommunicatingHostInterface communicationHost) {
		super(communicationHost.getId(), communicationHost, communicationHost.getProtocol());
	}

	@Override
	public void interpretMessage(MessageInterface message) {
		if(message.getMessageId() == 251) {
			System.out.println("MERE");
		}
		CommunicatingHostInterface communicationHost = getCommunicationHost();
		if (message instanceof MEFSAgentCommunicationMessage) {
			MEFSAgentCommunicationMessage commMessage = (MEFSAgentCommunicationMessage) message;
			Integer agentDestinationId = commMessage.getAgentDestinationId();

			if (communicationHost.hasAgentWithId(agentDestinationId)) {
				super.interpretMessage(message);
				return;
			}

			if (agentToAddressDatabase.containsKey(agentDestinationId)) {
				if (commMessage.getTtl() > 0) {
					Integer hostDestination = agentToAddressDatabase.get(agentDestinationId);
					communicationHost.reRouteMessage(commMessage, hostDestination);
					communicationHost.addMessageForSending(commMessage);
				} else {
					if (homeAgencyForAgents.contains(agentDestinationId)) {
						if (messagesWaitingForAgentSync.containsKey(agentDestinationId)) {
							messagesWaitingForAgentSync.put(agentDestinationId, new ArrayList<MessageInterface>());
						}
						messagesWaitingForAgentSync.get(agentDestinationId).add(message);
					} else {
						communicationHost.reRouteMessage(commMessage, homeServerHostId);
						communicationHost.addMessageForSending(commMessage);
					}
				}
			} else {
				communicationHost.reRouteMessage(commMessage, homeServerHostId);
				communicationHost.addMessageForSending(commMessage);
			}
		} else if (message instanceof LocationUpdateMessageInterface) {
			LocationUpdateMessageInterface locationUpdateMessage = (LocationUpdateMessageInterface) message;
			Integer newInhabitingHostId = locationUpdateMessage.getNewHostId();
			Integer agentSourceId = locationUpdateMessage.getAgentId();
			agentToAddressDatabase.put(agentSourceId, newInhabitingHostId);
			homeAgencyForAgents.add(agentSourceId);
		} else if (message instanceof MEFSSyncMessage) {
			MEFSSyncMessage synchMessage = (MEFSSyncMessage) message;
			Integer hostSourceId = synchMessage.getHostSourceId();
			Integer sourceAgentId = synchMessage.getSourceAgentId();
			Optional<CommunicatingAgentInterface> optionalAgent = agentsWaitingForSynch.keySet().stream()
					.filter(agent -> agent.getId().equals(sourceAgentId)).findFirst();
			if (optionalAgent.isPresent()) {
				CommunicatingAgentInterface agent = optionalAgent.get();
				Integer agentId = agent.getId();
				List<MessageInterface> messages;
				if (messagesWaitingForAgentSync.containsKey(agentId)) {
					messages = messagesWaitingForAgentSync.remove(agentId);
				} else {
					messages = synchMessage.getMessages();
				}
				if (messages != null)
					for (MessageInterface messageInterface : messages) {
						agent.getProtocolAgent().receiveMessage(messageInterface);
					}

				MigratingAgentMessageInterface migratingMessage = agentsWaitingForSynch.remove(agent);
				communicationHost.routeMessage(migratingMessage);
				communicationHost.addMessageForSending(migratingMessage);
				updateProxy(sourceAgentId, migratingMessage.getHostDestinationId());
			} else {

				List<MessageInterface> messageForSynch = messagesWaitingForAgentSync.get(sourceAgentId);
				synchMessage.setMessages(messageForSynch);

				communicationHost.reRouteMessage(synchMessage, hostSourceId);
				communicationHost.addMessageForSending(synchMessage);
			}

		} else {
			super.interpretMessage(message);
		}
	}

	@Override
	public void init(Map<String, String> protocolArguments) {
		homeServerHostId = Integer.parseInt(protocolArguments.get("homeServerHost"));
		agentToAddressDatabase = new HashMap<Integer, Integer>();
		homeAgencyForAgents = new HashSet<>();
		agentsWaitingForSynch = new HashMap<CommunicatingAgentInterface, MigratingAgentMessageInterface>();
		messagesWaitingForAgentSync = new HashMap<Integer, List<MessageInterface>>();
		CommunicatingHostInterface communicationHost = getCommunicationHost();
		Collection<CommunicatingAgentInterface> activeAgents = communicationHost.getActiveAgents();
		for (CommunicatingAgentInterface communicatingAgentInterface : activeAgents) {
			agentToAddressDatabase.put(communicatingAgentInterface.getId(), communicationHost.getId());
		}
	}

	public void updateProxy(Integer agentId, Integer destinationHostId) {
		agentToAddressDatabase.put(agentId, destinationHostId);
	}

	public void addSynchAgent(CommunicatingAgentInterface agent, MigratingAgentMessageInterface migrationMessage) {
		agentsWaitingForSynch.put(agent, migrationMessage);
	}

}
