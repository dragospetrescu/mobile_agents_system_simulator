package core.simulation;

@SuppressWarnings("javadoc")
/**
 * TODO this will be removed and those parameters will be received through a json 
 */
public class Constants {

    public static int NO_WORKING_STEPS = 50000;
	public static int STEPS_WAITING_FOR_LAST_MESSAGES = NO_WORKING_STEPS / 2;
	public static int STEPS_WAITING_FOR_INIT =  NO_WORKING_STEPS / 2;
	
	public static int MIGRATION_FREQUENCY = MigrationFrequency.HIGH.getValue();
	public static int MESSAGE_FREQUENCY = MessageFrequency.HIGH.getValue();
	public static int MAXIMUM_CPU_POWER = CPUPower.EXTREMELY_LOW.getValue();
}
	