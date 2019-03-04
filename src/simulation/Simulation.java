package simulation;

import agents.Agent;
import helpers.Logger;
import hosts.HomeServer;
import hosts.Host;
import messages.AgentToHomeMessage;
import messages.InterAgentMessage;
import messages.Message;

import java.util.*;

public class Simulation {

    public static void main(String[] args) {

        Simulation simulation = new Simulation();
        simulation.init();
        simulation.run();
    }

    private List<Host> hosts;
    private List<Agent> agents;
    private List<Agent> migratingAgents;
    private List<Message> travelingMessages;
    private HomeServer homeServer;

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

        migratingAgents = new ArrayList<>();
        travelingMessages = new ArrayList<>();


        agents = new ArrayList<>();
        for (int agentId = 0; agentId < Constants.NO_AGENTS; agentId++) {
            int hostId = agentId;
            Host host = hosts.get(hostId);
            Agent agent = new Agent(agentId, host);
            host.addHomeAgent(agent);
            agents.add(agent);
        }
        homeServer = new HomeServer(Constants.NO_AGENTS, agents, hosts);
    }

    private void run() {

        for (long step = 0; step < noSteps; step++) {

            List<Message> messagesToBeRemoved = new ArrayList<>();
            for (ListIterator<Message> lit = travelingMessages.listIterator(); lit.hasNext();) {
                Message message = lit.next();
                if(message.isArrived()) {
                    Host hostDestination = message.getHostDestination();
                    Message forwardedMessage = hostDestination.parseMessage(message);
                    if(forwardedMessage != null) {
                        lit.add(forwardedMessage);
                    }
                    messagesToBeRemoved.add(message);
                } else {
                    message.travel();
                }
            }
            travelingMessages.removeAll(messagesToBeRemoved);

            for (Agent agent : migratingAgents) {
                if (agent.isArrived()) {
                    Host destination = agent.getDestination();
                    destination.addAgent(agent);
                    agent.arrived();
                } else {
                    agent.travel();
                }
            }

            for (Host host : hosts) {

                List<Agent> hostAgents = host.getPresentAgents();
                for (Iterator<Agent> agentsIterator = hostAgents.iterator(); agentsIterator.hasNext(); ) {
                    Agent agent = agentsIterator.next();

                    if(agent.wantsToSendMessage()) {
                        Agent destinationAgent = getRandomAgent();
                        InterAgentMessage message = agent.createMessage(destinationAgent, homeServer);
                        Logger.log(agent + " sends comm" + message + " to " + homeServer);
                        travelingMessages.add(message);
                    }

                    if (agent.isWorkDone()) {
                        agentsIterator.remove();
                        Host newAgentHost = getRandomHost();
                        agent.startMigrating(newAgentHost);
                        AgentToHomeMessage agentToHomeMessage = new AgentToHomeMessage(host, agent.getHomeHost(), newAgentHost, agent);
                        travelingMessages.add(agentToHomeMessage);
                        Logger.log(agent + " sends migrate" + agentToHomeMessage + " to " + agent.getHomeHost());
                        migratingAgents.add(agent);
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

    public Agent getRandomAgent() {
        return agents.get(randomHost.nextInt(agents.size()));
    }
}
