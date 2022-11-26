package soldier_types;

import armies.ArmiesEnum;

/**
 * Class HeavyInfantry is an instance of heavy infantry regiment 
 * 
 * @author Viktor Szitkey
 * @version 1.3
 * */
public class HeavyInfantry extends Units {
	
	private int value = 80;
	private String name = "Heavy Infantry";
	
	public HeavyInfantry(ArmiesEnum allegienceToArmy) {
		super(5, 200, allegienceToArmy);
	}
	
	public  int getValue() { return value; }
	
	public String getName() { return name; }
}
