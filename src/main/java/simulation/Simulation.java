package simulation;

import agent.communication.CommunicatingAgent;
import agent.communication.CommunicatingAgentInterface;
import host.communication.CommunicatingHost;
import host.communication.CommunicatingHostInterface;
import host.router.NetworkGraph;
import message.MessageInterface;
import message.MessagesManager;
import statistics.StatisticsCreator;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.*;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

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
	private List<CommunicatingHostInterface> allHosts;

	
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
		allHosts = new ArrayList<CommunicatingHostInterface>();
		allHosts.addAll(normalHosts);
		if (specialHosts != null)
			allHosts.addAll(specialHosts);
		
		for (CommunicatingHostInterface host : allHosts) {
			host.init(normalHosts, specialHosts);
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
		for (CommunicatingHostInterface communicatingHostInterface : allHosts) {
			communicatingHostInterface.initProtocol();
		}
	}

	/**
	 * Initializes the network
	 */
	private void initRouting() {
		graph = new NetworkGraph(graphFile);
		
		graph.addRoutingToHosts(allHosts);

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

		for (CommunicatingAgentInterface agent : agents) {
			agent.initAgent(agents, allHosts);
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

		for (int step = 0; step < Constants.NO_WORKING_STEPS + Constants.STEPS_WAITING_FOR_LAST_MESSAGES; step++) {
			
			messagesManager.travelMessages();
			List<MessageInterface> arrivedMessages = messagesManager.getArrivedMessages();
			for (MessageInterface message : arrivedMessages) {
				CommunicatingHostInterface nextHopHost = message.getNextHop();
				nextHopHost.receiveMessage(message);
			}

			for (CommunicatingHostInterface host : allHosts) {

				if (host.wantsToSendMessage()) {
					List<MessageInterface> messages = host.sendMessages();
					messagesManager.addAllMessages(messages);
				}

				List<CommunicatingAgentInterface> hostActiveAgents = host.getActiveAgents();
				for (Iterator<CommunicatingAgentInterface> agentsIterator = hostActiveAgents.iterator(); agentsIterator
						.hasNext();) {
					CommunicatingAgentInterface agent = agentsIterator.next();

					if (agent.wantsToSendMessage()) {
						List<MessageInterface> messages = agent.sendMessages();
						messagesManager.addAllMessages(messages);
					}

					if(step < Constants.NO_WORKING_STEPS)
					if (agent.wantsToMigrate()) {
						MessageInterface message = agent.prepareMigratingMessage();
						messagesManager.addMessage(message);
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
		System.out.println(1.0 * StatisticsCreator.getNumberOfSuccess() / StatisticsCreator.getNumberOfMessages() + " success rate");
	}
}
