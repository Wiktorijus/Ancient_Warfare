package factors;

import java.util.Scanner;

import armies.FactionEnum;
import battle_phases.ArmyBuild;

import java.util.Random;

/**
 * Class Faction allows user or program choose faction for armies
 * 
 * @author Viktor Szitkey
 * @version 1.3
 * */
public class Faction {

	private FactionEnum factionName;
	
	/**
	 * chooseRandomFaction method sets faction randomly
	 * 
	 * @see {@link #chooseFaction()}
	 * */
	public void randomFaction() {
		//TODO must work with enum
		//faction = factions[randomGenerator.nextInt(factions.length)];
	}
	
	public void setFactionName(FactionEnum newFactionName) { factionName = newFactionName; }
	
	/**
	 * getFactionName method returns faction name(full form), 
	 * this method is overloaded with {@link #getFaction(ArmyBuild winnersArmy) }
	 * 
	 * @return a String containing name of the faction
	 * */
	public FactionEnum getFactionName() { return factionName;	}
	
	/**
	 * getFactionIndex method returns value based on name of the faction, 
	 * this method is overloaded with {@link #getFactionName() }
	 * 
	 * @return an integer 
	 * */
	public int getFactionIndex(FactionEnum factionName) {
		
		switch(factionName) {
			case ROMAN:	return 0;
			case CARTHAGINIAN:
				return 1;
			case GREEK:
				return 2;
			case CELTIC:
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
	public static int[] getRandomFactionBonus(FactionEnum factionName) {
		int[] factionBonus = { 0,0,0,0,0 };
		
		//TODO this makes armies huge for now reutning no bonus based on faction, if we deside to have bigger battles just uncomment and change faction bonus to you liking
		/**
		switch(factionName) {
			case ROMAN:
				factionBonus[2] = 5;
				factionBonus[3] =  0;
				factionBonus[4] = 5;
				break;
			case CARTHAGINIAN:
				factionBonus[1] = 5;
				factionBonus[2] =  0;
				factionBonus[3] = 5;
				break;
			case CELTIC:
				factionBonus[0] = 5;
				factionBonus[2] =  0;
				factionBonus[4] = 5;
				break;
			case GREEK:
				factionBonus[0] =  0;
				factionBonus[2] = 5;
				factionBonus[3] = 5;
				break;	
		}*/
		
		return factionBonus;
	}
}