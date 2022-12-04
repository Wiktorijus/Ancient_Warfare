package soldier_types;

import armies.ArmiesEnum;
import factors.LocationEnum;
import factors.WeatherEnum;

/**
 * Class Archers is an instance of archer regiment 
 * 
 * @author Viktor Szitkey
 * @version 1.3
 * */
public class Archers extends Units  implements RandomFactors {

	private String name = "Archers";
	private final String SYMBOL_PATH = "../soldier_types/resources/Archers_Symbol.png";
	
	
	public Archers(int aD, ArmiesEnum allegienceToArmy) {
		super(aD, 2, 200, allegienceToArmy);
	}

	public String getName() { return name; }
	public String getSymbolPath() {	return SYMBOL_PATH; }
	
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
				return 0.5;
			case FOGGY:
				return 0.1;
		
		}	
		return 1;
	}
	
	/**
	 * locationValue returns double value based on location property and scenario,
	 * and must override this method from superclass  
	 * */
	@Override
	public double locationValue(LocationEnum location, boolean roleOfArmy) {
		if(roleOfArmy) {
			switch(location) {
				case PLAINS:
					return 0.9;
				case FOREST:
					return 0.4;
				case HILL:
					return 0.5;
				case MOUNTAIN:
					return 0.2;
			}
		}
		else {
			switch(location) {
				case PLAINS:
					return 1.0;
				case FOREST:
					return 0.5;
				case HILL:
					return 1.5;
				case MOUNTAIN:
					return 2;
			}
		}
		return 1;
	}

}
