package factors;

public enum LocationEnum {
	PLAINS("Plains", "../factors/resources/grasslands.png"),
	FOREST("Forest", "../factors/resources/woods.png"),
	HILL("Hill", "../factors/resources/hills.png"),
	MOUNTAIN("Mountain", "../factors/resources/mountains.png");
	
	private final String typeOfLocation;
	private final String LOCATION_IMAGE_PATH;
	
	private LocationEnum (String typeOfLocation, String LOCATION_IMAGE_PATH) {
		this.typeOfLocation = typeOfLocation;
		this.LOCATION_IMAGE_PATH = LOCATION_IMAGE_PATH;
	}
	
	public String getTypeOfLocation() {
		return this.typeOfLocation;
	}
	public String getLocationImageURL () {
		return this.LOCATION_IMAGE_PATH;
	}
}

