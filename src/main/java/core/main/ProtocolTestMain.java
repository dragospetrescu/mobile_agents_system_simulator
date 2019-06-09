package core.main;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import core.simulation.CPUPower;
import core.simulation.Constants;
import core.simulation.MessageFrequency;
import core.simulation.MigrationFrequency;
import core.simulation.Simulation;

/**
 *
 * Contains the main function
 * 
 */
public class ProtocolTestMain {

	/**
	 * @param args - Should contain exactly 3 files: -> graphPath - path to the
	 *             network json file -> hostsPath - path to the hosts json file ->
	 *             agentsPath - path to the agents json file
	 * 
	 */
	public static void main(String[] args) {
		CommandLine commandLine;
		Option graphOption = Option.builder("g").required(true).hasArg().desc("The path to the graph file")
				.longOpt("graph").build();
		Option hostOption = Option.builder("h").required(true).hasArg().desc("The path to the hosts file")
				.longOpt("hosts").build();
		Option agentsOption = Option.builder("a").required(true).hasArg().desc("The path to the agents file")
				.longOpt("agents").build();
		Option spacialOption = Option.builder("s").required(false).hasArg().desc("The path to the special hosts file")
				.longOpt("special").build();

		Option migrationFreqOption = Option.builder("migration").required(false).hasArg()
				.desc("Value can be LOW, NORMAL, HIGH").longOpt("migration_frequency").build();
		Option messageFreqOption = Option.builder("message").required(false).hasArg()
				.desc("Value can be LOW, NORMAL, HIGH").longOpt("message_frequency").build();
		Option cpuOption = Option.builder("cpu").required(false).hasArg().desc("Value can be LOW, NORMAL, HIGH")
				.longOpt("cpu_power").build();
		Option noStepsOption = Option.builder("steps").required(false).hasArg().desc("Number of steps")
				.longOpt("no_steps").build();
		CommandLineParser parser = new DefaultParser();

		Options options = new Options();
		options.addOption(graphOption);
		options.addOption(hostOption);
		options.addOption(agentsOption);
		options.addOption(spacialOption);
		options.addOption(migrationFreqOption);
		options.addOption(messageFreqOption);
		options.addOption(cpuOption);
		options.addOption(noStepsOption);

		try {
			commandLine = parser.parse(options, args);
			String graphPath = commandLine.getOptionValues("g")[0];
			String hostsPath = commandLine.getOptionValues("h")[0];
			String agentsPath = commandLine.getOptionValues("a")[0];
			String specialHostsPath = null;
			if (commandLine.hasOption("s"))
				specialHostsPath = commandLine.getOptionValues("s")[0];

			if (commandLine.hasOption("migration")) {
				Constants.MIGRATION_FREQUENCY = MigrationFrequency.valueOf(commandLine.getOptionValues("migration")[0])
						.getValue();
			}
			if (commandLine.hasOption("message")) {
				Constants.MESSAGE_FREQUENCY = MessageFrequency.valueOf(commandLine.getOptionValues("message")[0])
						.getValue();
			}
			if (commandLine.hasOption("cpu")) {
				Constants.MAXIMUM_CPU_POWER = CPUPower.valueOf(commandLine.getOptionValues("cpu")[0]).getValue();
			}
			if (commandLine.hasOption("no_steps")) {
				Constants.NO_WORKING_STEPS = Integer.parseInt(commandLine.getOptionValues("migration")[0]);
			}

			Simulation simulation = new Simulation(graphPath, hostsPath, agentsPath, specialHostsPath);
			simulation.init();
			simulation.start();
			simulation.printStatistics();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
}
