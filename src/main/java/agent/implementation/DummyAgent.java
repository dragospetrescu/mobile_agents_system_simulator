package agent.implementation;

import agent.Agent;
import helpers.Logger;
import helpers.RandomAssigner;
import host.Host;
import message.Message;
import message.implementation.MigratingAgentMessage;

import java.util.List;
import java.util.Random;

public class DummyAgent extends Agent {

    private int work;
    private List<Host> allHosts;
    private List<Agent> allAgents;
    private Random rand;

    public DummyAgent(int id, Host host, List<Host> allHosts) {
        super(id, host);
        this.allHosts = allHosts;
        rand = new Random();
        work = RandomAssigner.assignWork();
        Logger.i("WORK " + work);
    }

    @Override
    public void work() {
        if(work == 0) {
            work = RandomAssigner.assignWork();
        }
        work--;
    }

    @Override
    public boolean wantsToMigrate() {
        return work <= 0;
    }

    @Override
    public void receiveMessage(Message message) {
        System.out.println("RECEIVED MESSAGE FROM " + message.getAgentSource());
    }

    @Override
    public boolean wantsToSendMessage() {
        return false;
    }

    @Override
    public List<Message> sendMessages() {
        return null;
    }

    @Override
    public Message prepareMigratingMessage() {
        Host destinationHost = getRandomHost();
        Logger.i(toString() + " traveling from " +getHost() + " to "+ destinationHost);
        return new MigratingAgentMessage(getHost(), destinationHost, this);
    }

    private Host getRandomHost() {
        Host randomElement = allHosts.get(rand.nextInt(allHosts.size()));
        if(randomElement.equals(getHost())) {
            return getRandomHost();
        }
        return randomElement;
    }
}
