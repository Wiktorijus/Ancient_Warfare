package soldier_types;

public enum UnitsEnum {
	ARCHERS(50),
	CAVALRY(50),
	HEAVY(80),
	PIKE(70),
	LIGHT(50);
	
	int value;
	
	private UnitsEnum (int value) {
		this.value = value;
	} 
	
	public int getUnitTypeValue() { return value; }
}
