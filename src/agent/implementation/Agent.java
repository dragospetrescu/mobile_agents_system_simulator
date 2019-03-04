package agents;

import helpers.Logger;
import helpers.RandomAssigner;
import hosts.Host;
import messages.InterAgentMessage;
import messages.Message;
import simulation.Constants;

public class Agent {

    private int id;
    private final Host homeHost;
    private Host host;
    private Host destination;

    private int workTime;
    private int migrateTime;
    private boolean isMigrating;

    public Agent(int id, Host host) {
        this.id = id;
        this.host = host;
        homeHost = host;
    }

    public boolean wantsToMigrate() {
        return !isMigrating && workTime <= 0;
    }

    public void work() {
        workTime--;
    }

    public Host getDestination() {
        return destination;
    }

    public void arrived() {
        host = destination;
        isMigrating = false;
        assignWork();
        Logger.log(this + " arrived at " + destination + " and was assigned work for " + workTime);
    }

    public void assignWork() {
        workTime = RandomAssigner.assignWork();
    }

    public void startMigrating(Host destinationHost) {
        isMigrating = true;
        migrateTime = RandomAssigner.assignMigrateTime(host, destinationHost);
        this.destination = destinationHost;
        Logger.log(this + " migrates to " + destination + " for " + migrateTime);
    }

    public boolean isArrived() {
        return isMigrating && migrateTime == 0;
    }

    public void travel() {
        migrateTime--;
    }

    @Override
    public String toString() {
        return "Agent " + id;
    }

    public boolean wantsToSendMessage() {
        return Math.random() <= Constants.MESSAGE_FREQUENCY;
    }

    public void receiveMessage(InterAgentMessage interAgentMessage) {
        Logger.log(this + " received " + interAgentMessage + " from " + interAgentMessage.getSourceAgent());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Agent agent = (Agent) o;

        return id == agent.id;
    }

    @Override
    public int hashCode() {
        return id;
    }

    public InterAgentMessage createMessage(Agent destinationAgent, Host destinationHost) {
        return new InterAgentMessage(host, destinationHost, destinationAgent, this);
    }

    public Host getHomeHost() {
        return homeHost;
    }

    public Message prepareMessage() {
        return null;
    }

    public Message prepareMigratingMessage() {
        return null;
    }
}
