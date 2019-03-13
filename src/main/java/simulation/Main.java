package simulation;

import simulation.implementation.DummySimulation;

public class Main {

    public static void main(String[] args) {
        Simulation simulation = new DummySimulation();
        simulation.init();
        simulation.run();
    }
}