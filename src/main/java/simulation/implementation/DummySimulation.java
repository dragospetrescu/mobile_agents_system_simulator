package simulation.implementation;

import agent.Agent;
import agent.implementation.DummyAgent;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import host.Host;
import host.implementation.DummyHost;
import simulation.Simulation;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class DummySimulation extends Simulation {

    @Override
    public void initAgents() {
        Host host0 = getHosts().get(0);
        Agent agent0 = new DummyAgent(0, host0, getHosts());
        addNewAgent(agent0, host0);

        Host host1 = getHosts().get(1);
        Agent agent1 = new DummyAgent(1, host1, getHosts());
        addNewAgent(agent1, host1);
    }

    @Override
    public void initHosts() {
        Gson gson = new Gson();
        JsonReader reader;
        try {
            reader = new JsonReader(new FileReader("hosts.json"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        }
        Type listType = new TypeToken<ArrayList<DummyHost>>(){}.getType();
        setHosts(gson.fromJson(reader, listType));
        super.initHosts();
    }
}
