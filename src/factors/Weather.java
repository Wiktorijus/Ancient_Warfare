package factors;

/**
 * Class Weather control and sets weather property
 * 
 * @author Viktor Szitkey
 * @version 2.0
 * */
public class Weather {

	private static WeatherEnum weather; 
	
	/**
	 * setWeather method sets weather based on GUI choice box
	 * 
	 *  @param weather a String holding type of weather
	 *  
	 *  @see battle_phases.ArmyBuild#chooseWeather(String)
	 * */
	public static void setWeather(WeatherEnum choosedWeather) { weather = choosedWeather; }
	
	public static WeatherEnum getWeather() { return weather; }
}