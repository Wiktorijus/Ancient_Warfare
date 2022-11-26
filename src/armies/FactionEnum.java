package armies;

public enum FactionEnum {
	ROMAN("Roman"),
	CARTHAGINIAN("Carthaginian"),
	GREEK("Greek"),
	CELTIC("Celtic");
	
	private String nameOfFaction;
	
	private FactionEnum (String nameOfFaction) {
		this.nameOfFaction = nameOfFaction;
	}
	
	public String getNameOfFaction() {
		return this.nameOfFaction;
	}
}
