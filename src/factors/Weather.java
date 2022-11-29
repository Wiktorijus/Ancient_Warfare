package factors;

import java.util.Random;
import java.util.Scanner;

/**
 * Class Weather control and sets weather property
 * 
 * @author Viktor Szitkey
 * @version 1.3
 * */
public class Weather {

	private static WeatherEnum weather;
	private static String[] climate = {"clear", "rainy", "foogy"};
	private static Random randomGenerator = new Random();
	private static Scanner sc = new Scanner(System.in); 
	
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