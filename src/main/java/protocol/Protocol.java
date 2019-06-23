package protocol;

import blackboard.BlackboardAgent;
import blackboard.BlackboardHost;
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
import mdp.MDPAgent;
import mdp.MDPGatewayHost;
import mdp.MDPHost;
import mefs.MEFSAgent;
import mefs.MEFSHomeAgentHost;
import mefs.MEFSHomeServerHost;
import ramdp.RAMDPAgent;
import ramdp.RAMDPHomeNode;
import ramdp.RAMDPRegionServer;
import ramdp.RAMPDLookupServer;
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
	 * Forward Proxy. Every host leaves some breadcrumbs before migrating to a new
	 * host The messages follow those breadcrumbs
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
	CSServer,
	/**
	* 
	*/
	MDP,
	/**
	* 
	*/
	MDP_GATEWAY,
	/**
	* 
	*/
	BLACKBOARD,
	/**
	* 
	*/
	MEFS,
	/**
	* 
	*/
	MEFS_SERVER, RAMDP, RAMDP_LS, RAMDP_RS;

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
		case MDP:
			return new MDPHost(communicationHost);
		case MDP_GATEWAY:
			return new MDPGatewayHost(communicationHost);
		case BLACKBOARD:
			return new BlackboardHost(communicationHost);
		case MEFS:
			return new MEFSHomeAgentHost(communicationHost);
		case MEFS_SERVER:
			return new MEFSHomeServerHost(communicationHost);
		case RAMDP:
			return new RAMDPHomeNode(communicationHost);
		case RAMDP_LS:
			return new RAMPDLookupServer(communicationHost);
		case RAMDP_RS:
			return new RAMDPRegionServer(communicationHost);
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
		case MDP:
			return new MDPAgent(communicatingAgent);
		case MDP_GATEWAY:
			throw new RuntimeException("MDP_GATEWAY is not an agent Protocol!");
		case BLACKBOARD:
			return new BlackboardAgent(communicatingAgent);
		case MEFS:
			return new MEFSAgent(communicatingAgent);
		case MEFS_SERVER:
			throw new RuntimeException("MEFS_SERVER is not an agent Protocol!");
		case RAMDP:
			return new RAMDPAgent(communicatingAgent);
		case RAMDP_LS:
			throw new RuntimeException("RAMDP_LS is not an agent Protocol!");
		case RAMDP_RS:
			throw new RuntimeException("RAMDP_RS is not an agent Protocol!");
		}
		throw new RuntimeException("NO PROTOCOL AGENT CREATED FOR " + this + " " + communicatingAgent);
	}

	@Override
	public String toString() {
		return name();
	}
}
