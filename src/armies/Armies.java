package armies;

import factors.Commander;
import factors.Location;
import factors.Weather;
import soldier_types.*;

/**
 * Class Armies serves every need in terms of creating and 
 * managing army. Also class Armies is abstract method because 
 * there should not be an army without faction.
 * 
 * @author Viktor Szitkey
 * @version 2.0
 * */
public abstract class Armies {
	
	private Units[][] units;

	private boolean attack;
	
	private final int AA, AD, CA, CD, HIA, HID, HA, HD, LIA, LID;
	
	/**
	 * Constructor Armies assigns specific values from faction classes to appropriate
	 * constant variable and creates arrays of unit types.
	 * 
	 * @param AA Archers attack
	 * @param AD Archers defend
	 * @param CA Cavalry attack
	 * @param CD Cavalry defend
	 * @param HIA Heavy infantry attack
	 * @param HID Heavy infantry defend
	 * @param HA Hoplites attack
	 * @param HD Hoplites defend
	 * @param LIA Light infantry attack
	 * @param LID Light infantry defend
	 * @param units array used for referencing to particular unit type
	 * @param attack a boolean containing scenario for the army
	 * 
	 * @see Roman#Roman(int, int, int, int, int, boolean)
	 * @see Carthagian#Carthagian(int, int, int, int, int, boolean)
	 * @see Greek#Greek(int, int, int, int, int, boolean)
	 * @see Celtic#Celtic(int, int, int, int, int, boolean)
	 * @see #initialization()
	 * */
	Armies(int AA, int AD, int CA, int CD, int HIA, int HID, int HA, int HD, int LIA, int LID,
		   int archers, int cavalry, int heavy, int hoplites, int light, boolean situation, ArmiesEnum allegienceToArmy){
		this.AA = AA;
		this.AD = AD;
		this.CA = CA;
		this.CD = CD;
		this.HIA = HIA;
		this.HID = HID;
		this.HA = HA;
		this.HD = HD;
		this.LIA = LIA;
		this.LID = LID;
		units = new Units[5][];
		
		units[0] = new Archers[archers+1];
		units[1] = new Cavalry[cavalry+1];
		units[2] = new HeavyInfantry[heavy+1];
		units[3] = new Pikemen[hoplites+1];
		units[4] = new LightInfantry[light+1];
		
		this.attack = situation;
		
		initialization(allegienceToArmy);
	}
	
	/**
	 * initialization method creates instance of every unit type, 
	 * which are used as reference in following methods  
	 * */
	private void initialization(ArmiesEnum allegienceToArmy) {
		units[0][0] = new Archers(allegienceToArmy);
		units[1][0] = new Cavalry(allegienceToArmy);
		units[2][0] = new HeavyInfantry(allegienceToArmy);
		units[3][0] = new Pikemen(allegienceToArmy);
		units[4][0] = new LightInfantry(allegienceToArmy);
	}
	
	/**
	 * getArmyStrenght method determines the strength of an army
	 * 
	 * @return Double value, which represents the final strength of an army
	 * 
	 * @see factors.Commander#getModifier(String)
	 * @see factors.Commander#getValueOfSkill()
	 * @see soldier_types.Units#getName()
	 * @see #archersStrength()
	 * @see #cavalryStrength()
	 * @see #heavyStrength()
	 * @see #hoplitesStrength()
	 * @see #lightStrength()
	 * */
	public Double getArmyStrength(Commander leader) {
		double strength = 1;
		strength =  ((units[0].length-1) * archersStrength() * leader.getModifier(units[0][0].getName()))
				   +((units[1].length-1) * cavalryStrength() * leader.getModifier(units[1][0].getName()))
				   +((units[2].length-1) * heavyStrength() * leader.getModifier(units[2][0].getName()))
				   +((units[3].length-1) * hoplitesStrength() * leader.getModifier(units[3][0].getName()))
				   +((units[4].length-1) * lightStrength() * leader.getModifier(units[4][0].getName()));
			
		return strength*leader.getValueOfSkill();
	}
	
	/**
	 * getArmyCount method returns number of soldiers in the army
	 * 
	 * @see soldier_types.Units#getNumber()
	 * */
	public int getArmyCount() {
		
		int count;
		
		count = ((units[0].length-1) * units[0][0].getNumber())+
				((units[1].length-1) * units[1][0].getNumber())+
				((units[2].length-1) * units[2][0].getNumber())+
				((units[3].length-1) * units[3][0].getNumber())+
				((units[4].length-1) * units[4][0].getNumber());
		
		return count; 
	}
	
	/**
	 * getUnitNumber method returns number of soldiers in one regiment of this type of unit
	 * 
	 * @see soldier_types.Units#getNumber()
	 * @see factors.Composition#getUnitNumber(int)
	 * @see battle_phases.ArmyBuild#getUnitNumber(int)
	 * */
	public int getUnitNumber(int i) { return units[i][0].getNumber(); }
	
	/**
	 * archersStrength method calculates strength of one archer regiment based on
	 * weather and location property, number of soldiers in regiment, morale of unit and scenario.
	 * Method contains example of polymorphism and upcasting.
	 * 
	 * @param attack a boolean determines scenario
	 * 
	 * @return Double value with calculated archer strength
	 * 
	 * @see factors.Weather#getWeather()
	 * @see factors.Location#getLocation()
	 * @see soldier_types.Units#getMorale()
	 * @see soldier_types.Units#getNumber()
	 * */
	private Double archersStrength(){
		
		double strength;
		if(attack) {
		strength = (AA * units[0][0].weatherValue(Weather.getWeather())
					*units[0][0].getMorale()*units[0][0].locationValue(Location.getLocation(), attack)
					+units[0][0].getNumber());
		}
		else {
			strength = (AD * units[0][0].weatherValue(Weather.getWeather())
					    *units[0][0].getMorale()*units[0][0].locationValue(Location.getLocation(), attack)
					    +units[0][0].getNumber());
		}
		return strength;

	}
	
	/**
	 * cavalryStrength method calculates strength of one cavalry regiment based on
	 * weather and location property, number of soldiers in regiment, morale of unit and scenario
	 * Method contains example of polymorphism and upcasting.
	 * 
	 * @param attack a boolean determines scenario
	 * 
	 * @return Double value with calculated cavalry strength
	 * 
	 * @see factors.Weather#getWeather()
	 * @see factors.Location#getLocation()
	 * @see soldier_types.Units#getMorale()
	 * @see soldier_types.Units#getNumber()
	 * */
	private Double cavalryStrength(){
		double strength = 1;
		if(attack) {
			strength = (CA * units[1][0].weatherValue(Weather.getWeather())
						*units[1][0].getMorale()*units[1][0].locationValue(Location.getLocation(), attack)
						+units[1][0].getNumber());
		}
		else {
			strength = (CD * units[1][0].weatherValue(Weather.getWeather())
					    *units[1][0].getMorale()*units[1][0].locationValue(Location.getLocation(), attack)
					    +units[1][0].getNumber());
		}
		return strength;
	}
	
	/**
	 * heavyStrength method calculates strength of one heavy infantry regiment based on
	 * weather and location property, number of soldiers in regiment, morale of unit and scenario
	 * Method contains example of polymorphism and upcasting.
	 * 
	 * @param attack a boolean determines scenario
	 * 
	 * @return Double value with calculated  heavy infantry strength
	 * 
	 * @see factors.Weather#getWeather()
	 * @see factors.Location#getLocation()
	 * @see soldier_types.Units#getMorale()
	 * @see soldier_types.Units#getNumber()
	 * */
	private Double heavyStrength(){
		double strength = 1;
		if(attack) {
			strength = (HIA * units[2][0].weatherValue(Weather.getWeather())
						*units[2][0].getMorale()*units[2][0].locationValue(Location.getLocation(), attack)
						+units[2][0].getNumber());
		}
		else {
			strength = (HID * units[2][0].weatherValue(Weather.getWeather())
					    *units[2][0].getMorale()*units[2][0].locationValue(Location.getLocation(), attack)
					    +units[2][0].getNumber());
		}
		return strength;
	}
	
	/**
	 * hoplitesStrength method calculates strength of one hoplites regiment based on
	 * weather and location property, number of soldiers in regiment, morale of unit and scenario
	 * Method contains example of polymorphism and upcasting.
	 * 
	 * @param attack a boolean determines scenario
	 * 
	 * @return Double value with calculated hoplites strength
	 * 
	 * @see factors.Weather#getWeather()
	 * @see factors.Location#getLocation()
	 * @see soldier_types.Units#getMorale()
	 * @see soldier_types.Units#getNumber()
	 * */
	private Double hoplitesStrength(){
		double strength = 1;
		if(attack) {
			strength = (HA * units[3][0].weatherValue(Weather.getWeather())
						*units[3][0].getMorale()*units[3][0].locationValue(Location.getLocation(), attack)
						+units[3][0].getNumber());
		}
		else {
			strength = (HD * units[3][0].weatherValue(Weather.getWeather())
					    *units[3][0].getMorale()*units[3][0].locationValue(Location.getLocation(), attack)
					    +units[3][0].getNumber());
		}
		return strength;
	}
	
	/**
	 * lightStrength method calculates strength of one light infantry regiment based on
	 * weather and location property, number of soldiers in regiment, morale of unit and scenario
	 * Method contains example of polymorphism and upcasting.
	 * 
	 * @param attack a boolean determines scenario
	 * 
	 * @return Double value with calculated light infantry strength
	 * 
	 * @see factors.Weather#getWeather()
	 * @see factors.Location#getLocation()
	 * @see soldier_types.Units#getMorale()
	 * @see soldier_types.Units#getNumber()
	 * */
	private Double lightStrength(){
		double strength = 1;
		if(attack) {
			strength = (LIA * units[4][0].weatherValue(Weather.getWeather())
						*units[4][0].getMorale()*units[4][0].locationValue(Location.getLocation(), attack)
						+units[4][0].getNumber());
		}
		else {
			strength = (LID * units[4][0].weatherValue(Weather.getWeather())
					    *units[4][0].getMorale()*units[4][0].locationValue(Location.getLocation(), attack)
					    +units[4][0].getNumber());
		}
		return strength;
	}
	
	/**
	 * getUnitsName method creates array of unit types names a returns it
	 * 
	 * @param names a String array containing list of names
	 * 
	 * @return String array containing list of unit type names
	 * 
	 * @see factors.Composition#getUnitsName()
	 * @see battle_phases.ArmyBuild#getUnitsName()
	 * */
	public String[] getUnitsName() {
		String[] names = new String[units.length];
		for( int i = 0; i < units.length; i++) {
			names[i] = units[i][0].getName();
		}
		return names;
	}
	
	/**
	 * getValue method returns value of unit needed in methods composing the army.
	 * Method contains example of polymorphism and downcasting.
	 * 
	 * @return value of unit 
	 * 
	 * @see factors.Composition#getValue(Units)
	 * */
	public static int getValue(Units unit) {
		if(unit instanceof Archers) {
			return ((Archers)unit).getValue();
		}
		else if(unit instanceof Cavalry) {
			return ((Cavalry)unit).getValue();
		}
		else if(unit instanceof HeavyInfantry) {
			return ((HeavyInfantry)unit).getValue();
		}
		else if(unit instanceof Pikemen) {
			return ((Pikemen)unit).getValue();
		}
		else if(unit instanceof LightInfantry) {
			return ((LightInfantry)unit).getValue();
		}
		return 0;
	}

	public Units[][] getUnits() {
		return units;
	}
}
