package host.protocol;

import host.communication.CommunicatingHostInterface;
import message.MessageInterface;
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
	int getId();

	/**
	 * @return the protocol of this agent
	 */
	Protocol getProtocol();

	/**
	 * Protocol init
	 */
	void init();
}
