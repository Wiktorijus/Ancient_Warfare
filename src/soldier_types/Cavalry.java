package soldier_types;

import armies.ArmiesEnum;

/**
 * Class Cavalry is an instance of cavalry regiment 
 * 
 * @author Viktor Szitkey
 * @version 1.3
 * */
public class Cavalry extends Units implements RandomFactors {
	
	private String name = "Cavalry";
	private final String SYMBOL_PATH = "../model/resources/Cavalry_Symbol.png";
	
	public Cavalry(int cD, ArmiesEnum allegienceToArmy) {
		super(cD, 10, 100, allegienceToArmy);
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
				return 0.9;
			case("foggy"):
				return 0.8;
		}
		return 1;
	}
	
	/**
	 * locationValue returns double value based on location property and scenario,
	 * and must override this method from superclass  
	 * */
	public double locationValue(String location, boolean attack) {
		if(attack) {
			switch(location) {
				case("plains"):
					return 1.5;
				case("forest"):
					return 0.6;
				case("hill"):
					return 0.5;
				case("mountain"):
					return 0.1;
			}
		}
		else {
			switch(location) {
			case("plains"):
				return 1.3;
			case("forest"):
				return 0.6;
			case("hill"):
				return 1.0;
			case("mountain"):
				return 0.9;
			}
		}
		return 1;
	}
}
