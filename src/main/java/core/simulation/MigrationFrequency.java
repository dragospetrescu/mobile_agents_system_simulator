package core.simulation;
@SuppressWarnings("javadoc")
public enum MigrationFrequency {

	EXTREMELY_LOW(1000),
	LOW(500),
	NORMAL(100),
	HIGH(50),
	EXTREMELY_HIGH(10);
	
	private int value;

	private MigrationFrequency(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}
	
}
