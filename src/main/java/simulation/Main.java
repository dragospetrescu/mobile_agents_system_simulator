package simulation;

public class Main {

    public static void main(String[] args) {
        Simulation simulation = new Simulation("graph.json", "hosts.json", "agents.json");
        simulation.init();
//        simulation.run();
    }
}
