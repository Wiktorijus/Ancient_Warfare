package factors;

public enum WeatherEnum {
	CLEAR("Clear"),
	RAINY("Rainy"),
	FOOGY("Foggy");
	
	private String typeOfWeather;
	
	private WeatherEnum (String typeOfWeather) {
		this.typeOfWeather = typeOfWeather;
	}
	
	public String getTypeOfWeather() {
		return this.typeOfWeather;
	}
}
