package battle_phases;

import java.util.ArrayList;
import armies.ArmiesEnum;
import armies.ArmiesStatusEnum;
import armies.FactionEnum;
import factors.*;
import gui.MyRectangleUnit;
import soldier_types.UnitsEnum;

/**
 * Class ArmyBuild and it's instances calls various methods, that essentially create every part of an "army".
 * 
 * @author Viktor Szitkey
 * @version 2.0
 * */
public class ArmyBuild {
	
	private static boolean situation = true;
	private boolean roleOfArmy;
	private Faction faction;
	private Commander leader;
	private Composition comp;
	private ArmiesEnum allegienceToArmy;
	private ArmiesStatusEnum status = ArmiesStatusEnum.PREPARING;
	private ArrayList<MyRectangleUnit> reserveLine = new ArrayList<>();
	private ArrayList<MyRectangleUnit> secondLine = new ArrayList<>();
	private ArrayList<MyRectangleUnit> firstLine = new ArrayList<>();
	
	/**
	 * ArmyBuild constructor, explicit parameterless
	 * */
	public ArmyBuild(ArmiesEnum allegienceToArmy, boolean roleOfArmy) {
		faction = new Faction();
		leader = new Commander();
		comp = new Composition();
		this.roleOfArmy = roleOfArmy;
		
		this.allegienceToArmy = allegienceToArmy;	
	}
	
	/**
	 * chooseArmy method calls methods based on scenario,
	 * that will determine faction and composition of "army"
	 * 
	 * @param random a String which tells which scenario is running
	 * @param faction a instance of class Faction to which faction properties are saved
	 * @param comp a instance of class Composition to which composition of an army is saved 
	 * */
	public void chooseArmy() {
		
		comp.createArmy(faction.getFactionName(), leader, roleOfArmy, allegienceToArmy);
	}
	
	/**
	 * chooseSituation method sets simulation property, 
	 * which determines which "army" is attacking and which is defending
	 * 
	 * @param situation a boolean which tells if this property was already set for this simulation
	 * @param roleOfArmy a boolean is later for determining what is this armie's task (attack defend)
	 * */
	public void chooseSituation(String random) {
		if(situation){	
			roleOfArmy = Scenario.setSituation(random);
			situation = false;
		} else {
			roleOfArmy = Scenario.getSituation();
		}
	}
	
	/**
	 * createCommander method calls a method that creates instance of class commander
	 * 
	 * @param leader instance of the Commander class 
	 * */
	public void setCommanderSkill(Integer valueOfSkill) { leader.setSkill(valueOfSkill); }
	
	/**
	 * chooseWeather method calls static method in class Weather, which sets weather for the battle
	 * */
	//public static void chooseWeather(String choosedWeather) { Weather.setWeather(choosedWeather); }
	/**
	 * getCount method providing count of soldiers in this.army
	 * 
	 * @return number of soldiers in this.army
	 * */
	public int getCount() { return (int) comp.getFactionCount(); }
	
	public void changeComposition(String id, int changedValue) {
		
			
		//TODO add more unit types
		switch(id.substring(0, id.length() - 2)) {
		case("archer"):
			comp.changeUnit(UnitsEnum.ARCHERS, changedValue);
			break;
		case("cavalry"):
			comp.changeUnit(UnitsEnum.CAVALRY, changedValue);
			break;
		case("heavy"):
			comp.changeUnit(UnitsEnum.HEAVY, changedValue);
			break;
		case("pike"):
			comp.changeUnit(UnitsEnum.PIKE, changedValue);
			break;	
		case("light"):
			comp.changeUnit(UnitsEnum.LIGHT, changedValue);
			break;	
		default:
			System.out.println("Unknow type of unit add it in Army Build nad in Controller");
		}
	}
	
	/**
	 * getUnitNumber method returns number of soldiers in a one regiment
	 * 
	 * @param i integer determines type of the regiment
	 * 
	 * @return count of soldiers in regiment
	 * */
	public int getUnitNumber(int i) { return comp.getUnitNumber(i); }
	
	/**
	 * getSummary method returns a value of an army
	 * 
	 * @return a value that indicates worth of the entire army,
	 * 			not to be confuse with a strength of an army 
	 * */
	public double getSummary() { return comp.getFactionSummary(); }
	
	/**
	 * getStrenght method returns strength on an this.army
	 * 
	 * @return this.army strength
	 * */
	public int getStrength() { return (int) comp.getFactionStrength(); }
	
	
	public void setFactionName(FactionEnum newFactionName) { 
		
		this.faction.setFactionName(newFactionName);
	}
	/**
	 * getFactionName method calls through faction reference method of class Faction
	 * 
	 * @return a String that holds name of the faction 
	 * */
	public FactionEnum getFactionName() { return faction.getFactionName(); }
	
	/**
	 * getUnitTypeName method returns array containing list of unit types names
	 * 
	 * @return a String array containing list of unit types names
	 * */
	public String[] getUnitsName() { return comp.getUnitsName(); }
	
	/**
	 * getScenario method return scenario in a String
	 * 
	 * @return a String that is equivalent of scenario
	 * */
	public String getScenario() {
		if(roleOfArmy) return "Attacking ";
		else return "Defending ";
	}
	
	/**
	 * reset method sets situation and scenario to default value for purposes next simulation
	 * */
	public void reset() { 
		situation = true;
		roleOfArmy = true;
	}
	
	public void randomArmy() {
		
		this.faction.randomFaction(); // TODO make this visible on GUI
		this.leader.randomCommander(); // TODO make this visible on GUI and add commander effect in general
		this.comp.chooseRandomComposition(this.faction.getFactionName(), leader, true, allegienceToArmy); //TODO argument scenario = true shouldn't be const.
	}
	
	public void setArmyStatus (ArmiesStatusEnum newStatus) {
		status = newStatus;
	}
	public ArmiesStatusEnum getArmyStatus () {
		return status;
	}
	
	public Faction getFaction() {
		return faction;
	}
	public Composition getComp() {
		return comp;
	}

	public Commander getLeader() {
		return leader;
	}
	
	public ArrayList<MyRectangleUnit> getReserveLine() {
		return reserveLine;
	}

	public ArrayList<MyRectangleUnit> getSecondLine() {
		return secondLine;
	}

	public ArrayList<MyRectangleUnit> getFirstLine() {
		return firstLine;
	}

	public void resetLines() {
		firstLine.clear();
		secondLine.clear();
		reserveLine.clear();
	}
	
}
