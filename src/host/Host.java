package host;

import agent.Agent;
import message.Message;

import java.util.ArrayList;
import java.util.List;

public abstract class Host implements HostInterface {

    private static int hostsCount = 0;

    private int id;
    private List<Agent> activeAgents;

    public Host() {
        this.id = hostsCount;
        hostsCount++;
        this.activeAgents = new ArrayList<>();
    }

    @Override
    public List<Agent> getActiveAgents() {
        return activeAgents;
    }

    @Override
    public void addAgent(Agent agent) {
        activeAgents.add(agent);
    }

    @Override
    public String toString() {
        return "Host " + id;
    }

    public int getId() {
        return id;
    }
}
