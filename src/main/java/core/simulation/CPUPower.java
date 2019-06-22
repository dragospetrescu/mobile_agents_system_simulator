package core.simulation;

/**
 * @author dragos
 *	How many messages can a host process during a 1 simulation step
 */
@SuppressWarnings("javadoc")
public enum CPUPower {
	EXTREMELY_SLOW(1),
	SLOW(5),
	NORMAL(10),
	FAST(25),
	EXTREMELY_FAST(50);
	
	private int value;

	private CPUPower(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}
}
