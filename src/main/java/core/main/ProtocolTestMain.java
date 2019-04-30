package core.main;

import core.simulation.Simulation;

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
    	String specialHostsPath = null;
    	if (args.length == 4) {
    		specialHostsPath = args[3];
    	}
    	
        Simulation simulation = new Simulation(graphPath, hostsPath, agentsPath, specialHostsPath);
        simulation.init();
        simulation.start();
        simulation.printStatistics();
    }
}
