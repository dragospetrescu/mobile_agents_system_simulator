package core.host.protocol;

import java.util.List;
import java.util.Map;

import core.host.communication.CommunicatingHostInterface;
import core.message.MessageInterface;
import protocol.Protocol;

/**
 * ProtocolHost represents the protocol-dependent part of the Host. 
 * It's responsibilities are: 
 * - interpreting messages 
 */
public interface ProtocolHost {

	/**
	 * If the host contains the destination agent it redirects the message to the agent.
	 * Otherwise it marks it as a fail for statistic reasons.
	 * 
	 * @param message - the message to be interpreted / redirected
	 */
	void interpretMessage(MessageInterface message);

	/**
	 * @return the CommunicatingHost that is currently using this ProtocolHost
	 */
	CommunicatingHostInterface getCommunicationHost();

	/**
	 * @return id - unique identifier
	 */
	Integer getId();

	/**
	 * @return the protocol of this agent
	 */
	Protocol getProtocol();

	/**
	 * Protocol init
	 */
	void init(Map<String, String> protocolArguments);
	
	/**
	 * Communicating line is:
	 * CommunicatingAgent -> ProtocolAgent -> ProtocolHost -> CommunicationHost -> MessageManager ->
	 * CommunicationHost -> ProtocolHost -> ProtocolAgent -> CommuncatingAgent
	 * 
	 * @param message - the message that will be delivered to the communicationgHost
	 */
	void sendMessage(MessageInterface message);

	/**
	 * @return list of ids of all hosts in simulation
	 */
	public List<Integer> getAllHosts();
}
