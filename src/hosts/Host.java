package hosts;

import agents.Agent;

import java.util.ArrayList;
import java.util.List;

public class Host {

    private int id;
    private List<Agent> agents;

    public Host(int id) {
        this.id = id;
        agents = new ArrayList<>();
    }

    public void addAgent(Agent agent) {
        agents.add(agent);
    }

    public List<Agent> getAgents() {
        return agents;
    }

    public void removeAgent(Agent agent) {
        agents.remove(agent);
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "hosts.Host " + id;
    }
}
