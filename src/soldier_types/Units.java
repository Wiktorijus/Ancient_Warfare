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
	
	private UnitsStatusEnum status = UnitsStatusEnum.READY;
	private final int MAXNUMBER;
	private int damage;
	private int morale;
	private int number;
	private ArmiesEnum allegienceToArmy;
	
	/**
	 * Constructor Units assigns morale of unit and number of soldiers
	 * 
	 * */
	Units(int damage, int morale, int number, ArmiesEnum allegienceToArmy) {
		this.damage = damage;
		this.morale = morale;
		this.number = number;
		this.allegienceToArmy = allegienceToArmy;
		this.MAXNUMBER = number;
	}
	
	public abstract String getName();
	
	public UnitsStatusEnum getStatus() { return status; }
	public void setStatus(UnitsStatusEnum status) { this.status = status; }
	public int getMaxNumber () { return MAXNUMBER; }
	public int getDamage() { return damage; }
	public int getMorale() { return morale; }
	public int getNumber() { return number; }
	public void decreaseNumber(int change) { number -= change; }
	public ArmiesEnum getAllegienceToArmy() { return allegienceToArmy; }

	

}
