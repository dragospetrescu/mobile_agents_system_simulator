package test_protocol;

import java.util.Map;

import agent.communication.CommunicatingAgentInterface;
import agent.protocol.AbstractProtocolAgent;
import message.MessageInterface;

/**
 * Not working. This is a test
 */
public class TestAgent extends AbstractProtocolAgent {

	/**
	 * @param communicatingAgent that is using this protocol
	 */
	public TestAgent(CommunicatingAgentInterface communicatingAgent) {
		super(communicatingAgent.getId(), communicatingAgent, communicatingAgent.getProtocol());
	}

	@Override
	public void receiveMessage(MessageInterface message) {
		return;
	}

	@Override
	public void prepareMessageTo(CommunicatingAgentInterface randomElement) {
		return;
	}
	
	@Override
	public void init(Map<String, String> protocolArguments) {
	}

}
