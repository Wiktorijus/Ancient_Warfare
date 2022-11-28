package soldier_types;

import armies.ArmiesEnum;

/**
 * Class Archers is an instance of archer regiment 
 * 
 * @author Viktor Szitkey
 * @version 1.3
 * */
public class Archers extends Units  implements RandomFactors {

	private String name = "Archers";
	private final String SYMBOL_PATH = "../model/resources/Archers_Symbol.png";
	
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
	public double weatherValue(String weather) {
		switch(weather) {
			case("clear"):
				return 1.0;
			case("rainy"):
				return 0.5;
			case("foggy"):
				return 0.1;
		
		}	
		return 1;
	}
	
	/**
	 * locationValue returns double value based on location property and scenario,
	 * and must override this method from superclass  
	 * */
	@Override
	public double locationValue(String location, boolean attack) {
		if(attack) {
			switch(location) {
				case("plains"):
					return 0.9;
				case("forest"):
					return 0.4;
				case("hill"):
					return 0.5;
				case("mountain"):
					return 0.2;
			}
		}
		else {
			switch(location) {
				case("plains"):
					return 1.0;
				case("forest"):
					return 0.5;
				case("hill"):
					return 1.5;
				case("mountain"):
					return 2;
			}
		}
		return 1;
	}
}
