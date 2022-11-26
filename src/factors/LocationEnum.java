package factors;

public enum LocationEnum {
	PLAINS("Plains"),
	FOREST("Forest"),
	HILL("Hill"),
	MOUNTAIN("Moutain");
	
	private String typeOfLocation;
	
	private LocationEnum (String typeOfLocation) {
		this.typeOfLocation = typeOfLocation;
	}
	
	public String getTypeOfLocation() {
		return this.typeOfLocation;
	}
}

