package factors;

/**
 * Class Location control and sets location property
 * 
 * @author Viktor Szitkey
 * @version 1.3
 * */
public class Location {
	
	private static LocationEnum location;
	
	/**
	 * setLocation method sets location based on GUI choice box
	 * 
	 *  @param location a String holding type of location
	 *  
	 *  @see battle_phases.ArmyBuild#chooseLocation(String)
	 * */
	public static void setLocation(LocationEnum locationEnum) { location = locationEnum; }
	
	public static LocationEnum getLocation() { return location; }
}
