package mdp;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import core.agent.communication.CommunicatingAgentInterface;
import core.host.communication.CommunicatingHostInterface;
import core.host.protocol.AbstractProtocolHost;
import core.message.Message;

public class MDPHost extends AbstractProtocolHost {

	private Integer gatewayHostId;

	public MDPHost(CommunicatingHostInterface communicationHost) {
		super(communicationHost.getId(), communicationHost, communicationHost.getProtocol());
	}

	@Override
	public void init(Map<String, String> protocolArguments) {

		gatewayHostId = Integer.parseInt(protocolArguments.get("gateway"));

		Collection<CommunicatingAgentInterface> activeAgents = getCommunicationHost().getActiveAgents();
		List<Integer> agentIds = activeAgents.stream().map(agent -> agent.getId()).collect(Collectors.toList());
		Integer sourceHostId = getCommunicationHost().getId();

		Message mdpLocationUpdateMessage = new MDPLocationUpdateMessage(sourceHostId, gatewayHostId, agentIds,
				sourceHostId);
		sendMessage(mdpLocationUpdateMessage);

	}

	public Integer getGatewayHostId() {
		return gatewayHostId;
	}
}
