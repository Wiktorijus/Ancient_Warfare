package soldier_types;

public enum UnitsEnum {
	ARCHERS(50),
	CAVALRY(50),
	HEAVY(80),
	PIKE(70),
	LIGHT(50);
	
	int cost, maxNumber;
	
	private UnitsEnum (int cost) {
		this.cost = cost;
	} 
	
	public int getUnitTypeCost() { return cost; }
}
