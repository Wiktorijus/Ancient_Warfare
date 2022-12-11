package soldier_types;

import armies.ArmiesEnum;

/**
 * Class LightInfatry is an instance of light infantry regiment 
 * 
 * @author Viktor Szitkey
 * @version 1.3
 * */
public class LightInfantry extends Units {
	
	private String name = "Light Infantry";
	
	public LightInfantry(int lightInfantryDamage, ArmiesEnum allegienceToArmy) {
		super(lightInfantryDamage, 2, 200, allegienceToArmy);
	}
	
	public String getName() { return name; }
}
