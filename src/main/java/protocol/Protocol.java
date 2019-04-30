package protocol;
import broadcast.BroadcastAgent;
import broadcast.BroadcastHost;
import core.agent.communication.CommunicatingAgent;
import core.agent.protocol.ProtocolAgent;
import core.host.communication.CommunicatingHost;
import core.host.protocol.ProtocolHost;
import cs.CSAgent;
import cs.CSHost;
import cs.CSServerHost;
import fp.FPAgent;
import fp.FPHost;
import hss.HSSHomeServerHost;
import shadow.ShadowAgent;
import shadow.ShadowHomeServerHost;
import shadow.ShadowHost;
import hss.HSSAgent;
import hss.HSSHomeAgentHost;

/**
 * List of available protocols If you want to add a new protocol add a new entry
 * to this list and modify the getProtocolHost and getProtocolAgent so the
 * switch will return the appropriate protocol host and protocol agent
 * 
 */
public enum Protocol {
	/**
	 * Agents broadcast all their messages.
	 */
	BROADCAST,
	/**
	 * HSS normal protocol
	 */
	HSS,
	/**
	 * Home Server protocol for HSS
	 */
	HSSServer, 
	/**
	 * Forward Proxy. Every host leaves some breadcrumbs before migrating to a new host
	 * The messages follow those breadcrumbs
	 */
	FP, 
	/**
	 * Combination between FP and HSS based on TTL
	 */
	Shadow, 
	/**
	 * Home Server protocol for Shadow
	 */
	ShadowServer,
	/**
	 * Central Server protocol
	 */
	CS,
	/**
	 * The server part of the Central Server protocol
	 */
	CSServer;
	;

	/**
	 * Based on the {@link Protocol} it returns a ProtocolHost
	 * 
	 * @param communicationHost that will use the protocol. Needed for logging
	 * @return a ProtocolHost associated with the Protocol
	 */
	public ProtocolHost getProtocolHost(CommunicatingHost communicationHost) {
		switch (this) {
		case BROADCAST:
			return new BroadcastHost(communicationHost);
		case HSS:
			return new HSSHomeAgentHost(communicationHost);
		case HSSServer:
			return new HSSHomeServerHost(communicationHost);
		case FP:
			return new FPHost(communicationHost);
		case Shadow:
			return new ShadowHost(communicationHost);
		case ShadowServer:
			return new ShadowHomeServerHost(communicationHost);
		case CS:
			return new CSHost(communicationHost);
		case CSServer:
			return new CSServerHost(communicationHost);
		default:
			break;
		}
		throw new RuntimeException("NO PROTOCOL HOST CREATED FOR " + this + " " + communicationHost);
	}

	/**
	 * Based on the {@link Protocol} it returns a ProtocolAgent
	 * 
	 * @param communicatingAgent that will use the protocol. Needed for logging
	 * @return a ProtocolAgent associated with the Protocol
	 */
	public ProtocolAgent getProtocolAgent(CommunicatingAgent communicatingAgent) {
		switch (this) {
		case BROADCAST:
			return new BroadcastAgent(communicatingAgent);
		case HSS:
			return new HSSAgent(communicatingAgent);
		case HSSServer:
			throw new RuntimeException("HSSServer is not an agent Protocol!");
		case FP:
			return new FPAgent(communicatingAgent);
		case Shadow:
			return new ShadowAgent(communicatingAgent);
		case ShadowServer:
			throw new RuntimeException("ShadowServer is not an agent Protocol!");
		case CS:
			return new CSAgent(communicatingAgent);
		case CSServer:
			throw new RuntimeException("CSServer is not an agent Protocol!");
		default:
			break;
		}
		throw new RuntimeException("NO PROTOCOL AGENT CREATED FOR " + this + " " + communicatingAgent);
	}

	@Override
	public String toString() {
		return name();
	}
}
