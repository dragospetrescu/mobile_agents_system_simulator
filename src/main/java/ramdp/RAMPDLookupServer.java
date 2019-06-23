package ramdp;

import java.util.HashMap;
import java.util.Map;

import core.host.communication.CommunicatingHostInterface;
import core.host.protocol.AbstractProtocolHost;
import core.message.AgentCommunicationMessageInterface;
import core.message.LocationUpdateMessageInterface;
import core.message.MessageInterface;

/**
 * @author dragos
 *
 */
public class RAMPDLookupServer extends AbstractProtocolHost {

	/**
	 * 
	 */
	Map<Integer, Integer> agentToHN;

	/**
	 * @param communicationHost - the CommunicatingAgent that will use this protocol
	 */
	public RAMPDLookupServer(CommunicatingHostInterface communicationHost) {
		super(communicationHost.getId(), communicationHost, communicationHost.getProtocol());
	}

	@Override
	public void interpretMessage(MessageInterface message) {
		if (message instanceof AgentCommunicationMessageInterface) {
			AgentCommunicationMessageInterface commMessage = (AgentCommunicationMessageInterface) message;
			Integer agentDestinationId = commMessage.getAgentDestinationId();
			Integer hn = agentToHN.get(agentDestinationId);
			CommunicatingHostInterface communicationHost = getCommunicationHost();
			communicationHost.reRouteMessage(commMessage, hn);
			communicationHost.addMessageForSending(commMessage);

		} else if (message instanceof LocationUpdateMessageInterface) {
			LocationUpdateMessageInterface locMessage = (LocationUpdateMessageInterface) message;
			Integer agentId = locMessage.getAgentId();
			Integer hnID = locMessage.getNewHostId();
			agentToHN.put(agentId, hnID);

		} else {
			super.interpretMessage(message);
		}
	}

	@Override
	public void init(Map<String, String> protocolArguments) {
		agentToHN = new HashMap<Integer, Integer>();
	}
}
