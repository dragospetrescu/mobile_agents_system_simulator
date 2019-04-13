package main;

import simulation.Simulation;

/**
 *
 * Contains the main function
 * 
 */
public class ProtocolTestMain {

    /**
     * @param args - Should contain exactly 3 files:
     * 					-> graphPath - path to the network json file
     * 					-> hostsPath - path to the hosts json file
     * 					-> agentsPath - path to the agents json file
     * 
     */
    public static void main(String[] args) {
    	String graphPath = args[0];
    	String hostsPath = args[1];
    	String agentsPath = args[2];
    	
        Simulation simulation = new Simulation(graphPath, hostsPath, agentsPath);
        simulation.init();
        simulation.start();
        simulation.printStatistics();
    }
}
