package agent;

import host.Host;

import java.util.List;

public abstract class Agent implements AgentInterface {

    private int id;
    private Host host;
    private List<Host> allHosts;
    private List<Agent> allAgents;

    public Agent(int id, Host host, List<Host> allHosts) {
        this.id = id;
        this.host = host;
        this.allHosts = allHosts;
    }

    @Override
    public Host getHost() {
        return host;
    }

    @Override
    public String toString() {
        return "Agent " + id;
    }

    public void setHost(Host host) {
        this.host = host;
    }

    public List<Host> getAllHosts() {
        return allHosts;
    }

    public void setAllHosts(List<Host> allHosts) {
        this.allHosts = allHosts;
    }

    public List<Agent> getAllAgents() {
        return allAgents;
    }

    public void setAllAgents(List<Agent> allAgents) {
        this.allAgents = allAgents;
    }
}
