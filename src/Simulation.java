import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class Simulation {

    public static void main(String[] args) {

        Simulation simulation = new Simulation();
        simulation.init();
        simulation.run();
    }

    private List<Host> hosts;
    private List<Agent> agents;
    private List<Agent> travelingAgents;

    private long noSteps;
    private Random randomHost;


    private void init() {

        noSteps = Constants.NO_STEPS;
        randomHost = new Random(42);

        hosts = new ArrayList<>();
        for (int hostId = 0; hostId < Constants.NO_HOSTS; hostId++) {
            Host host = new Host(hostId);
            hosts.add(host);
        }

        travelingAgents = new ArrayList<>();

        agents = new ArrayList<>();
        for (int agentId = 0; agentId < Constants.NO_AGENTS; agentId++) {
            int hostId = agentId;
            Host host = hosts.get(hostId);
            Agent agent = new Agent(agentId, host);
            host.addAgent(agent);
            agents.add(agent);
        }

    }

    private void run() {

        for (long step = 0; step < noSteps; step++) {
            Logger.log("STEP " + step);

            for (Agent agent : travelingAgents) {
                if (agent.isArrived()) {
                    Host destination = agent.getDestination();
                    destination.addAgent(agent);
                    agent.arrived();
                } else {
                    agent.travel();
                }
            }

            for (Host host : hosts) {

                List<Agent> hostAgents = host.getAgents();
                for (Iterator<Agent> agentsIterator = hostAgents.iterator(); agentsIterator.hasNext(); ) {
                    Agent agent = agentsIterator.next();

                    if (agent.isWorkDone()) {
                        agentsIterator.remove();
                        agent.startMigrating(getRandomHost());
                        travelingAgents.add(agent);
                    } else {
                        agent.work();
                    }

                }

            }


        }

    }



    public Host getRandomHost() {
        return hosts.get(randomHost.nextInt(hosts.size()));
    }
}
