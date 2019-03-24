package agent.communication;

import helpers.Logger;
import helpers.RandomAssigner;
import host.communication.CommunicatingHostInterface;
import message.MessageInterface;
import message.implementation.MigratingAgentMessage;
import protocol.Protocol;

import java.util.ArrayList;
import java.util.List;

import agent.protocol.ProtocolAgent;

public class CommunicatingAgent implements CommunicatingAgentInterface {

    private int id;
    private CommunicatingHostInterface host;
    private List<CommunicatingHostInterface> allHosts;
    private List<CommunicatingAgentInterface> allAgents;
    private int work;
    private Protocol protocol;
    private ProtocolAgent agentProtocol;
    private List<MessageInterface> messages;
    
    public CommunicatingAgent(int id, CommunicatingHostInterface host, List<CommunicatingHostInterface> allHosts, Protocol protocol) {
        this.id = id;
        this.host = host;
        this.allHosts = allHosts;
        this.protocol = protocol;
        this.agentProtocol = protocol.getProtocolAgent(this);
        work = RandomAssigner.assignWork();
        messages = new ArrayList<MessageInterface>();
    }

    @Override
    public void work() {
        if(work == 0) {
            work = RandomAssigner.assignWork();
        }
        if(work % 23 == 0) {
        	agentProtocol.prepareMessageTo(RandomAssigner.getRandomElement(allAgents, ((CommunicatingAgentInterface)this)));
        }
        work--;
    }

    @Override
    public boolean wantsToMigrate() {
        return work <= 0;
    }

    @Override
    public CommunicatingHostInterface getHost() {
        return host;
    }

    @Override
    public String toString() {
        return "Agent " + id;
    }

    @Override
    public MessageInterface prepareMigratingMessage() {
        CommunicatingHostInterface destinationHost = RandomAssigner.getRandomElement(allHosts, host);
        Logger.i(toString() + " traveling from " +getHost() + " to "+ destinationHost);
        return new MigratingAgentMessage(getHost(), destinationHost, this);
    }

	@Override
	public boolean wantsToSendMessage() {
		return !messages.isEmpty();
	}

	@Override
	public List<MessageInterface> sendMessages() {
		List<MessageInterface> messagesToBeSent = messages;
		messages = new ArrayList<MessageInterface>();
		return messagesToBeSent;
	}

	@Override
	public void receiveMessage(MessageInterface message) {
		agentProtocol.receiveMessage(message);
	}

	@Override
	public void addMessage(MessageInterface message) {
		messages.add(message);
	}
}
