package mefs;

import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

import core.agent.communication.CommunicatingAgentInterface;
import core.agent.protocol.AbstractProtocolAgent;
import core.host.protocol.ProtocolHost;
import core.message.LocationUpdateMessage;
import core.message.MessageInterface;
import core.message.MigratingAgentMessageInterface;
import core.simulation.Simulation;
import protocol.Protocol;

/**
 * The Agent from the HSS documentation
 * 
 * When agent migrates it sends to it's home host message that updates the
 * location database
 * 
 * Agent 0 wants to send message to Agent 1 Agent 0 sends message to HomeServer
 * HomeServer sends message to Agent 1 Home Agent Home Agent sends message
 * location of the Agent
 */
@SuppressWarnings("javadoc")
public class MEFSAgent extends AbstractProtocolAgent {

	/**
	 * Size of length before sync
	 */
	private static final int AVERAGE_SIZE = 5;
	/**
	 * Server that has mapped agent to homeHost
	 */
	private Integer homeServerHostId;
	/**
	 * This agent's home Host
	 */
	private Integer homeAgentHostId;
	private int maxSpeed;
	private long lastMigrateTime;
	private int ttl;
	private int originalTimeToSync;
	private Queue<Long> queue;
	private int timeToSync;

	/**
	 * @param communicatingAgent - agent that is going to use this protocol
	 *                           implementation
	 */
	public MEFSAgent(CommunicatingAgentInterface communicatingAgent) {
		super(communicatingAgent.getId(), communicatingAgent, communicatingAgent.getProtocol());
	}

	@Override
	public void prepareMessageTo(Integer destinationAgentId) {
		CommunicatingAgentInterface communicatingAgent = getCommunicatingAgent();
		ProtocolHost protocolHost = getProtocolHost();

		MessageInterface forwardedMessage = new MEFSAgentCommunicationMessage(communicatingAgent.getHostId(),
				homeServerHostId, communicatingAgent.getId(), destinationAgentId, ttl);
		protocolHost.sendMessage(forwardedMessage);
	}

	@Override
	public void migrate(Integer destinationHostId, MigratingAgentMessageInterface migrationMessage) {

		CommunicatingAgentInterface sourceAgent = getCommunicatingAgent();
		MEFSHomeAgentHost protocolHost = (MEFSHomeAgentHost) getProtocolHost();
		boolean synch = false;
		timeToSync--;
		if (timeToSync <= 0) {
			synch = true;
		}
		Long averageStay = queue.stream().reduce(0L, (a, b) -> a + b) / AVERAGE_SIZE;
		if(averageStay < maxSpeed)
			synch = true;

		if (synch) {
			protocolHost.addSynchAgent(sourceAgent, migrationMessage);
			MEFSSyncMessage synchMessage = new MEFSSyncMessage(sourceAgent.getHostId(), homeAgentHostId,
					sourceAgent.getId());
			protocolHost.sendMessage(synchMessage);
			timeToSync = originalTimeToSync;
		} else {
			super.migrate(destinationHostId, migrationMessage);
			MessageInterface locationMessage = new LocationUpdateMessage(sourceAgent.getHostId(), homeAgentHostId,
					sourceAgent.getId(), destinationHostId, Protocol.HSS);
			protocolHost.sendMessage(locationMessage);
			protocolHost.updateProxy(sourceAgent.getId(), destinationHostId);
			if(queue.size() > AVERAGE_SIZE) {
				queue.poll();
			}
			queue.offer(Simulation.step - lastMigrateTime);
			lastMigrateTime = Simulation.step;
		}
	}

	@Override
	public void init(Map<String, String> protocolArguments, ProtocolHost protocolHost) {
		super.init(protocolArguments, protocolHost);
		queue = new LinkedList<Long>();
		homeServerHostId = Integer.parseInt(protocolArguments.get("homeServerHost"));
		maxSpeed = Integer.parseInt(protocolArguments.get("maxSpeed"));
		ttl = Integer.parseInt(protocolArguments.get("ttl"));
		originalTimeToSync = Integer.parseInt(protocolArguments.get("timeToSync"));
		timeToSync = originalTimeToSync;
		homeAgentHostId = getCommunicatingAgent().getHostId();
		CommunicatingAgentInterface communicatingAgent = getCommunicatingAgent();
		MessageInterface message = new LocationUpdateMessage(communicatingAgent.getHostId(), homeServerHostId,
				communicatingAgent.getId(), homeAgentHostId, Protocol.HSS);
		protocolHost.sendMessage(message);
	}
}
