package soldier_types;

import armies.ArmiesEnum;

/**
 * Class Pikemen is an instance of pikemen regiment 
 * 
 * @author Viktor Szitkey
 * @version 2.0
 * */
public class Pikemen extends Units {
	
	private String name = "Pikemen";
	
	public Pikemen(int pD, ArmiesEnum allegienceToArmy) {
		super(pD, 5, 200, allegienceToArmy);
	}
	
	public String getName() { return name; }
}
