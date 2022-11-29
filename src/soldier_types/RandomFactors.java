package soldier_types;

import factors.LocationEnum;
import factors.WeatherEnum;

/**
 * Interface RandomFactors implements it's methods to classes Archers and Cavalry
 * */
public interface RandomFactors {
 
	public double weatherValue(WeatherEnum weather);	
	public double locationValue(LocationEnum location, boolean attack);
}
