package factors;

public enum WeatherEnum {
	CLEAR("Clear", "../factors/resources/Clear.png"),
	RAINY("Rainy", "../factors/resources/Rainy.png"),
	FOGGY("Foggy", "../factors/resources/Foggy.png");
	
	private String typeOfWeather;
	private final String WEATHER_IMAGE_PATH;
	
	private WeatherEnum (String typeOfWeather, String WEATHER_IMAGE_PATH) {
		this.typeOfWeather = typeOfWeather;
		this.WEATHER_IMAGE_PATH = WEATHER_IMAGE_PATH;
	}
	
	public String getTypeOfWeather() {
		return this.typeOfWeather;
	}
	public String getWeatherImageURL() {
		return this.WEATHER_IMAGE_PATH;
	}
}
