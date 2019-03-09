package host;

import agent.Agent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class Host implements HostInterface {

    public int id;
    private List<Agent> activeAgents;
    private Map<Host, Host> nextHopMap;

    public Host() {
        this.activeAgents = new ArrayList<Agent>();
        nextHopMap = new HashMap<Host, Host>();
    }

    public List<Agent> getActiveAgents() {
        return activeAgents;
    }

    public void addAgent(Agent agent) {
        activeAgents.add(agent);
    }

    public void addRouteNextHop(Host destinationRouter, Host nextHopRouter) {
        nextHopMap.put(destinationRouter, nextHopRouter);
    }

    @Override
    public String toString() {
        return "Host " + id;
    }

    public int getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Host host = (Host) o;

        return id == host.id;
    }

    @Override
    public int hashCode() {
        return id;
    }

    public void printRouting() {
        System.out.println(toString());

        for (Host destHost: nextHopMap.keySet()) {
            System.out.println("To " + destHost + " by " + nextHopMap.get(destHost));
        }
    }
}
