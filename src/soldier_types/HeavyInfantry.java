package soldier_types;

import armies.ArmiesEnum;

/**
 * Class HeavyInfantry is an instance of heavy infantry regiment 
 * 
 * @author Viktor Szitkey
 * @version 1.3
 * */
public class HeavyInfantry extends Units {
	
	private String name = "Heavy Infantry";
	
	public HeavyInfantry(int hD, ArmiesEnum allegienceToArmy) {
		super(hD, 5, 200, allegienceToArmy);
	}
	
	public String getName() { return name; }
}
