package core.simulation;

public enum CPUPower {
	EXTREMELY_LOW(1),
	LOW(5),
	NORMAL(10),
	HIGH(25),
	EXTREMELY_HIGH(50);
	
	private int value;

	private CPUPower(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}
}
