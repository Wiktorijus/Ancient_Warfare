package soldier_types;

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
	public default double locationValue(String location, boolean roleOfArmy) {
		if(roleOfArmy) {
			switch(location) {
				case("plains"):
					return 1.2;
				case("forest"):
					return 0.9;
				case("hill"):
					return 0.8;
				case("mountain"):
					return 0.6;
			}
		}
		else {
			switch(location) {
				case("plains"):
					return 1.0;
				case("forest"):
					return 0.9;
				case("hill"):
					return 1.2;
				case("mountain"):
					return 1.5;
			}
		}
		return 1;
	}
}
