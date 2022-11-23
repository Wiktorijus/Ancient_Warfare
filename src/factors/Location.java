package factors;

import java.util.Random;
import java.util.Scanner;

/**
 * Class Location control and sets location property
 * 
 * @author Viktor Szitkey
 * @version 1.3
 * */
public class Location {
	
	private static String location;
	private static String[] place = {"plains", "forest", "hill", "mountain"};
	private static Random randomGenerator = new Random();
	private static Scanner sc = new Scanner(System.in);
	
	/**
	 * setLocation method sets location based on GUI choice box
	 * 
	 *  @param location a String holding type of location
	 *  
	 *  @see battle_phases.ArmyBuild#chooseLocation(String)
	 * */
	public static void setLocation(String choosedLocation) { location = choosedLocation; }
	
	public static String getLocation() { return location; }
}
