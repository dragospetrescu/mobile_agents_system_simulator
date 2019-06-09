package core.simulation;

public enum MessageFrequency {
	EXTREMELY_LOW(100),
	LOW(50),
	NORMAL(10),
	HIGH(5),
	EXTREMELY_HIGH(1);
	
	private int value;

	private MessageFrequency(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}
}
