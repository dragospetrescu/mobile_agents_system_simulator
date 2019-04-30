package core.simulation;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.*;
import java.util.stream.Collectors;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import core.agent.communication.CommunicatingAgent;
import core.agent.communication.CommunicatingAgentInterface;
import core.host.communication.CommunicatingHost;
import core.host.communication.CommunicatingHostInterface;
import core.host.router.NetworkGraph;
import core.message.AgentCommunicationMessageInterface;
import core.message.MessageInterface;
import core.message.MessagesManager;
import core.statistics.StatisticsCreator;

/**
 * 
 * This is where the simulation is initiated and run
 * 
 * Should not be extended. 
 *
 */
public class Simulation {

	/**
	 * Normal hosts of the simulation. They are protocol independent.
	 */
	private List<CommunicatingHostInterface> normalHosts;
	
	/**
	 * All CommunicatingAgents of the simulation. They are protocol independent.
	 */
	private List<CommunicatingAgentInterface> agents;
	
	/**
	 * Represents the network as a graph
	 */
	private static NetworkGraph graph;
	/**
	 * Represents the physical layer of communication.
	 */
	private MessagesManager messagesManager;
	/**
	 * Json file where the simulation's agents are described
	 */
	private String agentsFile;
	/**
	 * Json file where the network is described
	 */
	private String graphFile;
	/**
	 * Json file where the simulation's normal hosts are described
	 */
	private String hostsFile;

	/**
	 * Json file where the simulation's special hosts are described (protocol specific)
	 * May be null
	 */
	private String specialHostsFile;

	/**
	 * Special hosts of the simulation. They are protocol dependent.
	 */
	private List<CommunicatingHostInterface> specialHosts;

	/**
	 * All hosts of the simulation
	 */
	private Map<Integer, CommunicatingHostInterface> allHostsMap;

	
	/**
	 * @param graphFile - Json file where the simulation's agents are described
	 * @param hostsFile - Json file where the network is described
	 * @param agentsFile - Json file where the simulation's hosts are described
	 * @param specialHostsFile - Json file where the simulation's special hosts are described
	 */
	public Simulation(String graphFile, String hostsFile, String agentsFile, String specialHostsFile) {
		this.graphFile = graphFile;
		this.hostsFile = hostsFile;
		this.agentsFile = agentsFile;
		this.specialHostsFile = specialHostsFile;
		agents = new ArrayList<>();
	}

	/**
	 * Main initialization function. Should be called before starting the simulation
	 */
	public void init() {
		createSpecialHosts();
		createNormalHosts();
		initAllHosts();
		initRouting();
		initAgents();
		initHostProtocol();
		initAgentProtocol();
	}

	
	/**
	 * Inits all hosts
	 */
	private void initAllHosts() {
		allHostsMap = new HashMap<Integer, CommunicatingHostInterface>();
		for (CommunicatingHostInterface host : normalHosts) {
			allHostsMap.put(host.getId(), host);
		}
		if (specialHosts != null) {
			for (CommunicatingHostInterface host : specialHosts) {
				allHostsMap.put(host.getId(), host);
			}
		}
		
		for (CommunicatingHostInterface host : allHostsMap.values()) {
			host.init(normalHosts.stream().map(CommunicatingHostInterface::getId).collect(Collectors.toList()));
		}	
	}

	/**
	 * Creates special hosts from json file or if
	 * the specialHostsFile is null it does nothing
	 */
	private void createSpecialHosts() {
		if (specialHostsFile == null)
			return;
		Gson gson = new Gson();
		JsonReader reader;
		try {
			reader = new JsonReader(new FileReader(specialHostsFile));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return;
		}
		Type listType = new TypeToken<ArrayList<CommunicatingHost>>() {
		}.getType();
		specialHosts = gson.fromJson(reader, listType);
	}

	/**
	 * Initializes agent's protocol
	 */
	private void initAgentProtocol() {
		for (CommunicatingAgentInterface communicatingAgentInterface : agents) {
			communicatingAgentInterface.initProtocol();
		}
	}

	/**
	 * Initializes host's protocol
	 */
	private void initHostProtocol() {
		for (CommunicatingHostInterface communicatingHostInterface : allHostsMap.values()) {
			communicatingHostInterface.initProtocol();
		}
	}

	/**
	 * Initializes the network
	 */
	private void initRouting() {
		graph = new NetworkGraph(graphFile);
		
		graph.addRoutingToHosts(allHostsMap.values());

		messagesManager = new MessagesManager(graph);
	}

	
	/**
	 * Initializes the agents
	 */
	public void initAgents() {
		Gson gson = new Gson();
		JsonReader reader;
		try {
			reader = new JsonReader(new FileReader(agentsFile));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return;
		}
		Type listType = new TypeToken<ArrayList<CommunicatingAgent>>() {
		}.getType();
		agents = gson.fromJson(reader, listType);
		List<Integer> agentsIds = agents.stream().map(CommunicatingAgentInterface::getId).collect(Collectors.toList());
		for (CommunicatingAgentInterface agent : agents) {
			agent.initAgent(agentsIds, normalHosts.get(agent.getHostId()));
		}
	}

	/**
	 * Initializes the hosts
	 */
	public void createNormalHosts() {
		Gson gson = new Gson();
		JsonReader reader;
		try {
			reader = new JsonReader(new FileReader(hostsFile));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return;
		}
		Type listType = new TypeToken<ArrayList<CommunicatingHost>>() {
		}.getType();
		normalHosts = gson.fromJson(reader, listType);

	}

	
	
	/**
	 * Starts the simulation. The init function should be called before starting the simulation.
	 */
	public void start() {

		for (Integer step = 0; step < Constants.NO_WORKING_STEPS + 2 * Constants.STEPS_WAITING_FOR_LAST_MESSAGES; step++) {
			
			messagesManager.travelMessages();
			List<MessageInterface> arrivedMessages = messagesManager.getArrivedMessages();
			for (MessageInterface message : arrivedMessages) {
				Integer nextHopHostId = message.getNextHopId();
				CommunicatingHostInterface nextHopHost = allHostsMap.get(nextHopHostId);
				nextHopHost.receiveMessage(message);
			}

			for (CommunicatingHostInterface host : allHostsMap.values()) {

				if (host.wantsToSendMessage()) {
					List<MessageInterface> messages = host.sendMessages();
					messagesManager.addAllMessages(messages);
				}

				Collection<CommunicatingAgentInterface> hostActiveAgents = host.getActiveAgents();
				for (Iterator<CommunicatingAgentInterface> agentsIterator = hostActiveAgents.iterator(); agentsIterator
						.hasNext();) {
					CommunicatingAgentInterface agent = agentsIterator.next();

					if(step >= Constants.STEPS_WAITING_FOR_LAST_MESSAGES && step <= Constants.STEPS_WAITING_FOR_LAST_MESSAGES + Constants.NO_WORKING_STEPS)
						if (agent.wantsToMigrate()) {
							agent.prepareMigratingMessage();
							agentsIterator.remove();
						} else {
							agent.work();
						}
				}
			}
		}
	}
	
	/**
	 * TODO: change this
	 */
	public void printStatistics() {
		StatisticsCreator statistics = StatisticsCreator.getStatistics();
		System.out.println(1.0 * statistics.getNumberOfSuccess() / statistics.getNumberOfMessages() + " success rate");
	}
}
