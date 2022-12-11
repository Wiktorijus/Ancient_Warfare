package soldier_types;

import armies.Armies;
import armies.ArmiesEnum;
import factors.Location;
import factors.Weather;

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
	private double morale;
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
	public double getMorale() { return morale; }
	public int getNumber() { return number; }
	public void decreaseNumber(int change) { number -= change; }
	public void decreaseMorale (double change) { morale -= change; }
	public ArmiesEnum getAllegienceToArmy() { return allegienceToArmy; }
	
	// determines damage based on faction role of army and unit + weather, location, number of remaining men and morale
	public Double unitDamage(boolean roleOfArmy, Armies army){
		double damage = 0;
		try {
			damage = (army.getDamage(this, roleOfArmy) * this.weatherValue(Weather.getWeather())
						*this.getMorale()*this.locationValue(Location.getLocation(), roleOfArmy)
						+this.getNumber()) /10; // TODO for now divided damage by 10 othervise everyone kills everyone too fast
		} catch (Exception e) {
			return 0.0;
		}
		return damage;
	}

}
