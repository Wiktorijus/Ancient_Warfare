package battle_phases;

import java.util.Scanner;

import factors.*;
import gui.UnitsEnum;
import javafx.scene.control.Button;

/**
 * Class ArmyBuild and it's instances calls various methods, that essentially create every part of an "army".
 * 
 * @author Viktor Szitkey
 * @version 1.3
 * */
public class ArmyBuild {
	
	private static boolean situation = true;
	private boolean scenario = true; // this shouldn't be constant true, change based on what army attacks, that is determined in GUI
	private Faction faction;
	private Commander leader;
	private Composition comp;
	
	
	private Scanner sc = new Scanner(System.in);
	
	/**
	 * ArmyBuild constructor, explicit parameterless
	 * */
	public ArmyBuild() {
		faction = new Faction();
		leader = new Commander();
		comp = new Composition();
		
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
		
		comp.chooseFaction(faction.getFactionName(), leader, scenario);
	}
	
	/**
	 * chooseSituation method sets simulation property, 
	 * which determines which "army" is attacking and which is defending
	 * 
	 * @param situation a boolean which tells if this property was already set for this simulation
	 * @param scenario a boolean is later for determining what is this armie's task (attack defend)
	 * */
	public void chooseSituation(String random) {
		if(situation){	
			scenario = Scenario.setSituation(random);
			situation = false;
		} else {
			scenario = Scenario.getSituation();
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
	public static void chooseWeather(String choosedWeather) { Weather.setWeather(choosedWeather); }
	
	/**
	 * chooseLocation method calls static method in class Location, which sets location for the battle
	 * */
	public static void chooseLocation(String choosedLocation) { Location.setLocation(choosedLocation); }
	
	public String getNickname() { return leader.getNickname(); }
	public String getName() { return leader.getName(); }
	public String getSpecialization() { return leader.getSpecialization(); }
	
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
	
	
	public void setFactionName(String newFactionName) { 
		
		this.faction.setFactionName(newFactionName);
	}
	/**
	 * getFactionName method calls through faction reference method of class Faction
	 * 
	 * @return a String that holds name of the faction 
	 * */
	public String getFactionName() { return faction.getFactionName(); }
	
	/**
	 * setFactionVictory method is called with instance of army that has bigger strength
	 * 
	 * @return value that is index in a field in Result class,
	 * 				 that tracks wins of every faction
	 * */
	public int getFactionIndex(ArmyBuild winnersArmy) { return faction.getFactionName(winnersArmy); }
	
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
		if(scenario) return "Attacking ";
		else return "Defending ";
	}
	
	/**
	 * reset method sets situation and scenario to default value for purposes next simulation
	 * */
	public void reset() { 
		situation = true;
		scenario = true;
	}
	
	public void randomArmy() {
		
		this.faction.randomFaction();
		this.leader.randomCommander();
		this.comp.chooseRandomComposition(this.faction.getFactionName(), leader, true); //TODO argument scenario = true shouldn't be const.
	}
	
	public Composition getComp() {
		return comp;
	}

	public Commander getLeader() {
		return leader;
	}
}
