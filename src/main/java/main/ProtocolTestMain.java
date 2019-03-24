package main;

import simulation.Simulation;

public class ProtocolTestMain {

    public static void main(String[] args) {
    	String graphPath = args[0];
    	String hostsPath = args[1];
    	String agentsPath = args[2];
    	
        Simulation simulation = new Simulation(graphPath, hostsPath, agentsPath);
        simulation.init();
        simulation.run();
        simulation.printStatistics();
    }
}
