package soldier_types;

import factors.LocationEnum;
import factors.WeatherEnum;

/**
 * Interface DefaultRandomFactors provides some default methods to superclass Unit
 * 
 * @author Viktor Szitkey
 * @version 1.3
 * */
public interface DefaultRandomFactors {

	public String getName();
	
	/**
	 * weatherValue method (default) is used by classes HeavyInfatry, Hoplites, LightInfantry
	 * and return value that will not affect calculations
	 * */
	public default double weatherValue(WeatherEnum weather) {
		return 1;
	}
	
	/**
	 * locationValue method (default) is used by classes HeavyInfatry, Hoplites, LightInfantry
	 * 
	 * @return double value that affecting calculations
	 * 
	 * @see armies.Armies#getArmyStrength(antic_factors.Commander)
	 * */
	public default double locationValue(LocationEnum location, boolean roleOfArmy) {
		if(roleOfArmy) {
			switch(location) {
				case PLAINS:
					return 1.2;
				case FOREST:
					return 0.9;
				case HILL:
					return 0.8;
				case MOUNTAIN:
					return 0.6;
			}
		}
		else {
			switch(location) {
				case PLAINS:
					return 1.0;
				case FOREST:
					return 0.9;
				case HILL:
					return 1.2;
				case MOUNTAIN:
					return 1.5;
			}
		}
		return 1;
	}
}
