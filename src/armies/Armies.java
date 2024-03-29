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
	
	private final int AA, AD, CA, CD, PA, PD, HA, HD, LIA, LID;
	
	private double maxArmyMorale;
	
	/**
	 * Constructor Armies assigns specific values from faction classes to appropriate
	 * constant variable and creates arrays of unit types.
	 * 
	 * @param AA Archers attack
	 * @param AD Archers defend
	 * @param CA Cavalry attack
	 * @param CD Cavalry defend
	 * @param HA Heavy infantry attack
	 * @param HD Heavy infantry defend
	 * @param PA Hoplites attack
	 * @param PD Hoplites defend
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
	Armies(int AA, int AD, int CA, int CD, int HA, int HD, int PA, int PD, int LIA, int LID,
		   int archers, int cavalry, int heavy, int pikemen, int light, boolean situation, ArmiesEnum allegienceToArmy){
		this.AA = AA;
		this.AD = AD;
		this.CA = CA;
		this.CD = CD;
		this.PA = HA;
		this.PD = HD;
		this.HA = PA;
		this.HD = PD;
		this.LIA = LIA;
		this.LID = LID;
		units = new Units[5][];
		
		
		units[0] = new Archers[archers];
		units[1] = new Cavalry[cavalry];
		units[2] = new HeavyInfantry[heavy];
		units[3] = new Pikemen[pikemen];
		units[4] = new LightInfantry[light];
		
		this.attack = situation;
		
		initialization(allegienceToArmy);
		setMaxArmyMorale();
	}
	
	/**
	 * initialization method creates instance of every unit type, 
	 * which are used as reference in following methods  
	 * */
	private void initialization(ArmiesEnum allegienceToArmy) {
		
		for(int i = 0; i < units[0].length; i++ ) { units[0][i] = new Archers(AD, allegienceToArmy); }
		for(int i = 0; i < units[1].length; i++ ) { units[1][i] = new Cavalry(CD, allegienceToArmy); }
		for(int i = 0; i < units[2].length; i++ ) { units[2][i] = new HeavyInfantry(HD, allegienceToArmy); }
		for(int i = 0; i < units[3].length; i++ ) { units[3][i] = new Pikemen(PD, allegienceToArmy); }
		for(int i = 0; i < units[4].length; i++ ) { units[4][i] = new LightInfantry(LID, allegienceToArmy); }
	}
	
	private void setMaxArmyMorale() {
		for(int type = 0; type < units.length; type++ ) {
			for(int regiments = 0; regiments < units[type].length; regiments++ ) {
				maxArmyMorale +=  units[type][regiments].getMorale();
			} 
		}
	}
	
	
	/**
	 * getArmyCount method returns number of soldiers in the army
	 * 
	 * @see soldier_types.Units#getNumber()
	 * */
	public int getArmyCount() {
		
		int count = 0;
		
		for(int type = 0; type < units.length; type++ ) {
			for(int regiments = 0; regiments < units[type].length; regiments++ ) {
				if(units[type][regiments].getStatus().equals(UnitsStatusEnum.READY)) {
					count +=  units[type][regiments].getNumber();
				}
					
			} 
		}
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

	public Units[][] getUnits() {
		return units;
	}
	public int getNumberOfUnits() {
		int numberOfUnits = 0;
		
		for( int type = 0; type < units.length; type++) {
			numberOfUnits += units[type].length;
		}
		
		return numberOfUnits;
		
	}
	
	public int getDamage(Units unit, boolean role) {
		
		if(role) { // role = true means this unit is attacking
			if(unit instanceof Archers) {
				return AA;
			}
			else if(unit instanceof Cavalry) {
				return CA;
			}
			else if(unit instanceof HeavyInfantry) {
				return HA;
			}
			else if(unit instanceof Pikemen) {
				return PA;
			}
			else if(unit instanceof LightInfantry) {
				return LIA;
			}
		} else // this unit is defending
			if(unit instanceof Archers) {
				return AD;
			}
			else if(unit instanceof Cavalry) {
				return CD;
			}
			else if(unit instanceof HeavyInfantry) {
				return HD;
			}
			else if(unit instanceof Pikemen) {
				return PD;
			}
			else if(unit instanceof LightInfantry) {
				return LID;
			}
		System.out.println("NO UNIT DAMAGE VALUE FOUND!!!");
		return 0;
	}
	
	public double getMorale() {
		double currentMorale = 0;
		
		for(int type = 0; type < units.length; type++ ) {
			for(int regiments = 0; regiments < units[type].length; regiments++ ) {
				if(units[type][regiments].getStatus().equals(UnitsStatusEnum.READY)) {
					currentMorale +=  units[type][regiments].getMorale();
				}
				
			} 
		}
		try {
			return currentMorale/maxArmyMorale;
		} catch (Exception e) {
			return 0;
		} 
	}
}
