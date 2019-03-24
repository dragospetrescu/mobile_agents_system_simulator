package simulation;

import agent.communication.CommunicatingAgent;
import agent.communication.CommunicatingAgentInterface;
import host.communication.CommunicatingHost;
import host.communication.CommunicatingHostInterface;
import host.router.Graph;
import message.MessageInterface;
import message.MessagesManager;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.*;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

public class Simulation {

	private List<CommunicatingHostInterface> hosts;
	private List<CommunicatingAgentInterface> agents;
	private static Graph graph;
	private MessagesManager messagesManager;

	private String agentsFile;
	private String graphFile;
	private String hostsFile;

	public Simulation(String graphFile, String hostsFile, String agentsFile) {
		this.graphFile = graphFile;
		this.hostsFile = hostsFile;
		this.agentsFile = agentsFile;
		agents = new ArrayList<>();
	}

	public void init() {

		initHosts();
		initRouting();
		initAgents();
	}

	private void initRouting() {
		graph = new Graph(graphFile);
		graph.addRoutingToHosts(hosts);

		messagesManager = new MessagesManager(graph);
	}

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
			agent.initAgent(agents, hosts);
			System.out.println(agent);
		}
	}

	public void initHosts() {
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
		hosts = gson.fromJson(reader, listType);

		for (CommunicatingHostInterface host : hosts) {
			host.setProtocolHost();
		}
	}

	public void run() {

		for (int step = 0; step < Constants.NO_STEPS; step++) {

			messagesManager.travelMessages();
			List<MessageInterface> arrivedMessages = messagesManager.getArrivedMessages();
			for (MessageInterface message : arrivedMessages) {
				CommunicatingHostInterface nextHopHost = message.getNextHop();
				nextHopHost.receiveMessage(message);
			}

			for (CommunicatingHostInterface host : hosts) {

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
}
