package soldier_types;

import armies.ArmiesEnum;

/**
 * Class Hoplites is an instance of hoplites regiment 
 * 
 * @author Viktor Szitkey
 * @version 2.0
 * */
public class Pikemen extends Units {
	
	private String name = "Hoplites";
	
	public Pikemen(int pD, ArmiesEnum allegienceToArmy) {
		super(pD, 5, 200, allegienceToArmy);
	}
	
	public String getName() { return name; }
}
