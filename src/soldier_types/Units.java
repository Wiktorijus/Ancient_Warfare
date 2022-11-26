package soldier_types;

import armies.ArmiesEnum;

/**
 * Class Units is a superclass for more specific unit types.
 * Class Units is abstract 
 * 
 * @author Viktor Szitkey
 * @version 1.3
 * */

public abstract class Units implements DefaultRandomFactors {
	
	private int morale;
	private int number;
	private ArmiesEnum allegienceToArmy;
	
	/**
	 * Constructor Units assigns morale of unit and number of soldiers
	 * 
	 * */
	Units(int morale, int number, ArmiesEnum allegienceToArmy) {
		this.morale = morale;
		this.number = number;
		this.allegienceToArmy = allegienceToArmy;
	}
	
	public abstract String getName();
	
	public int getMorale() { return morale; }
	public int getNumber() { return number; }
	public ArmiesEnum getAllegienceToArmy() { return allegienceToArmy; }

}
