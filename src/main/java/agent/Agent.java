package agent;

import host.Host;

public abstract class Agent implements AgentInterface {

    private int id;
    private Host host;
    private int workTime;

    public Agent(int id, Host host) {
        this.id = id;
        this.host = host;
    }

    @Override
    public Host getHost() {
        return host;
    }

    @Override
    public boolean isWorking() {
        return workTime != 0;
    }

    @Override
    public void work() {
        workTime--;
    }

    @Override
    public String toString() {
        return "Agent " + id;
    }
}
