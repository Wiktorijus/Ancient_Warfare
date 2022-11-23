package factors;

import java.util.Scanner;

import battle_phases.ArmyBuild;

import java.util.Random;

/**
 * Class Faction allows user or program choose faction for armies
 * 
 * @author Viktor Szitkey
 * @version 1.3
 * */
public class Faction {
	
	private Random randomGenerator = new Random();
	private Scanner sc = new Scanner(System.in);
	private String[] factions = { "Roman", "Carthaginian", "Greek", "Celtic" };
	private String faction;
	
	/**
	 * chooseRandomFaction method sets faction randomly
	 * 
	 * @see {@link #chooseFaction()}
	 * */
	public void randomFaction() {
		faction = factions[randomGenerator.nextInt(factions.length)];
	}
	
	public void setFactionName(String newFactionName) { faction = newFactionName; }
	
	/**
	 * getFactionName method returns faction name(full form), 
	 * this method is overloaded with {@link #getFaction(ArmyBuild winnersArmy) }
	 * 
	 * @return a String containing name of the faction
	 * */
	public String getFactionName() { return faction;	}
	
	/**
	 * getFactionName method returns value based on name of the faction, 
	 * this method is overloaded with {@link #getFactionName() }
	 * 
	 * @return an integer 
	 * */
	public int getFactionName(ArmyBuild winnersArmy) {
		
		String faction = winnersArmy.getFactionName();
		switch(faction) {
			case("Roman"):
				return 0;
			case("Carthaginian"):
				return 1;
			case("Greek"):
				return 2;
			case("Celtic"):
				return 3;
		}
		return 0;
	}
	
	/**
	 * getFaction method returns name of the faction to 
	 * method in FileControl class
	 * 
	 * @return a String containing name of the faction
	 * 
	 * @see  battle_phases.FileControl#saveNewResults()
	 * */
	public static String getFaction(int army) {
		
		switch(army) {
			case(0):
				return "Roman faction:";
			case(1):
				return "Carthaginian faction:";
			case(2):
				return "Greek faction:";
			case(3):
				return "Celtic faction:";
		}
		return "ArmyWithoutProperName";
	}
	
	/**
	 * getRandomFactionBonus method returns array containing information 
	 * important for random composition method
	 * 
	 * @return integer array 
	 * 
	 * @see Composition#chooseRandomComposition(String, Commander, boolean)
	 * */
	public static int[] getRandomFactionBonus(String factionName) {
		
		//TODO faction names should be maybe enum  otherwise is chatic
		int[] factionBonus = { 5, 5, 5, 5, 5 };
		
		switch(factionName) {
			case "Roman":
				factionBonus[2] = 10;
				factionBonus[3] =  0;
				factionBonus[4] = 10;
				break;
			case "Carthaginian":
				factionBonus[1] = 10;
				factionBonus[2] =  0;
				factionBonus[3] = 10;
				break;
			case "Celtic":
				factionBonus[0] = 10;
				factionBonus[2] =  0;
				factionBonus[4] = 10;
				break;
			case "Greek":
				factionBonus[0] =  0;
				factionBonus[2] = 10;
				factionBonus[3] = 10;
				break;	
		}
		
		return factionBonus;
	}
}