package armies;

public enum FactionEnum {
	ROMAN("Roman", "Romans have the greatest heavy infantry but their pikemen are lacking behind their counterparts"),
	CARTHAGINIAN("Carthaginian","Carthaginian cavalry is uncontested in the world, but their infantry units are lacking"),
	GREEK("Greek", "Greek pikemen are legendary, but rest of their army is mediocre"),
	CELTIC("Celtic", "Celtic archers are the finest, but they lack good heavy infantry");
	
	private String nameOfFaction;
	private String description;
	
	private FactionEnum (String nameOfFaction, String description) {
		this.nameOfFaction = nameOfFaction;
		this.description = description;
	}
	
	public String getNameOfFaction() {
		return this.nameOfFaction;
	}
	public String getFactionDescription () {
		return description;
	}
}
