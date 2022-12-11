package soldier_types;

import armies.ArmiesEnum;
import factors.LocationEnum;
import factors.WeatherEnum;

/**
 * Class Cavalry is an instance of cavalry regiment 
 * 
 * @author Viktor Szitkey
 * @version 1.3
 * */
public class Cavalry extends Units implements RandomFactors {
	
	private String name = "Cavalry";

	
	public Cavalry(int cD, ArmiesEnum allegienceToArmy) {
		super(cD, 10, 100, allegienceToArmy);
	}
	
	public String getName() { return name; }
	
	/**
	 * weatherValue returns double value based on weather property,
	 * and must override this method from superclass  
	 * */
	@Override
	public double weatherValue(WeatherEnum weather) {
		switch(weather) {
			case CLEAR:
				return 1.0;
			case RAINY:
				return 0.9;
			case FOGGY:
				return 0.8;
		}
		return 1;
	}
	
	/**
	 * locationValue returns double value based on location property and scenario,
	 * and must override this method from superclass  
	 * */
	public double locationValue(LocationEnum location, boolean roleOfArmy) {
		if(roleOfArmy) {
			switch(location) {
				case PLAINS:
					return 1.5;
				case FOREST:
					return 0.6;
				case HILL:
					return 0.5;
				case MOUNTAIN:
					return 0.1;
			}
		}
		else {
			switch(location) {
			case PLAINS:
				return 1.3;
			case FOREST:
				return 0.6;
			case HILL:
				return 1.0;
			case MOUNTAIN:
				return 0.9;
			}
		}
		return 1;
	}
}
