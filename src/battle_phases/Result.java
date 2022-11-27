package battle_phases;

import java.io.*;

import factors.Location;
import factors.Weather;

/**
 * Class Result decide result of the simulation and print it in console
 * 
 * @author Viktor Szitkey
 * @version 1.3
 * */
public class Result {

	private static PrintWriter pw = new PrintWriter(System.out, true);
	protected static int[] victories = new int[4];
	protected static int cwNumber = 0;
	private static int[] firstComposition;
	private static int[] secondComposition;
	private static String[] types;
	private static boolean cw = false;
	
	
	/**
	 * finalResult method calls methods, which are provide part of the output,
	 * which finalResult method put together
	 * 
	 * @param firstArmy an ArmyBuild instance referencing to values needed in method
	 * @param secondArmy an ArmyBuild instance referencing to values needed in method
	 * @param cw a boolean decides if simulation was made with two exact factions (civil war)
	 * @param firstComposition an integer array holds count of every type of unit used in this.army
	 * @param secondComposition an integer array holds count of every type of unit used in this.army
	 * */
	public static String finalResult(ArmyBuild firstArmy, ArmyBuild secondArmy) {
		
		//cw = false;
		//firstComposition = firstArmy.getCompostion();
		//secondComposition = secondArmy.getCompostion();
		//types = firstArmy.getUnitsName();
		
		//weatherAndLocation();
		//armySummary(firstArmy, secondArmy);
		return result(firstArmy, secondArmy);
		
	}
	
	/**
	 * weatherAndLocation method is called from finalResult method
	 * and prints start point of the output and general information about simulation
	 * */
	private static void weatherAndLocation() {
		
		pw.println("\t\t\t##############   Analyze is done!  ##############");
		for(int i = 0; i < 125; i++)pw.print("#");
		pw.println("\n\tWeather during battle: " + Weather.getWeather().toUpperCase());
		pw.println("\tLocation of battle: " + Location.getLocation().toUpperCase());
		pw.println();
	}
	
	/**
	 * armySummary method prints detail information of both simulation participants
	 * */
	private static void armySummary(ArmyBuild firstArmy, ArmyBuild secondArmy) {
	
		if(firstArmy.getFactionName().equals(secondArmy.getFactionName())) { pw.println("\tCIVIL WAR"); cw = true; cwNumber++; }
		if(cw) pw.println("\t" + firstArmy.getFactionName() + " state army:");
		else pw.println("\t" + firstArmy.getFactionName() + " army:");
		pw.println("\t\tScenario:\t\t" + firstArmy.getScenario().toUpperCase());
		//pw.println("\t\tCommander of " + firstArmy.getFactionName().toLowerCase() + " army" + ":\t" + firstArmy.getName() + firstArmy.getNickname());
		//pw.println("\t\t\tSkill:\t\t" + firstArmy.getSkill().toUpperCase());
		pw.println("\t\t\tSpecialization:\t" + firstArmy.getLeader().getSpecialization().toUpperCase());
		pw.println("\t\tValue of army:\t\t" + firstArmy.getSummary());
		pw.println("\t\tNumber of soldiers:\t" + firstArmy.getCount());
		pw.println("\t\tStrength:\t\t" + firstArmy.getStrength());
		pw.print("\t\tComposition:\t");
		for(int i = 0; i < firstComposition.length; i++) { pw.format("|%s %02d => %d men|", types[i].charAt(0), firstComposition[i], firstComposition[i]*firstArmy.getUnitNumber(i)); }
		
		pw.println("\n");
		if(cw) pw.println("\t" + secondArmy.getFactionName() + " rebel army:");
		else pw.println("\t" + secondArmy.getFactionName() + " army:");
		pw.println("\t\tScenario:\t\t" + secondArmy.getScenario().toUpperCase());
		//pw.println("\t\tCommander of " + secondArmy.getFactionName().toLowerCase() + " army" + ":\t" + secondArmy.getName() + secondArmy.getNickname());
		//pw.println("\t\t\tSkill:\t\t" + secondArmy.getSkill().toUpperCase());
		//pw.println("\t\t\tSpecialization:\t" + secondArmy.getSpecialization().toUpperCase());
		pw.println("\t\tValue of army:\t\t" + secondArmy.getSummary());
		pw.println("\t\tNumber of soldiers:\t" + secondArmy.getCount());
		pw.println("\t\tStrength:\t\t" + secondArmy.getStrength());
		pw.print("\t\tComposition:\t");
		for(int i = 0; i < secondComposition.length; i++) { pw.format("|%s %02d => %d men|",types[i].charAt(0), secondComposition[i], secondComposition[i]*secondArmy.getUnitNumber(i)); }
		pw.println("\n");
	}
	
	/**
	 * result method summary the result of simulation and prints it
	 * */
	private static String result( ArmyBuild firstArmy, ArmyBuild secondArmy) {
		
		String result;
		
		firstArmy.chooseArmy();
		secondArmy.chooseArmy();
		//pw.println(firstArmy.getStrength());
		//pw.println(secondArmy.getStrength());
		
		
		
		
		if(firstArmy.getStrength() > secondArmy.getStrength()) {
			result = "!VICTOR! " + firstArmy.getScenario();
		}
		else if (firstArmy.getStrength() < secondArmy.getStrength()) {
			result = "Right wins";
		}
		else {
			result = "Draw";
		}
		
		return result; 
		/**if(firstArmy.getStrength() > secondArmy.getStrength()) {
			
			pw.println("\t!VICTOR! " + firstArmy.getScenario() + firstArmy.getFactionName().toLowerCase() + " army has prevailed" +
								" under commander " + firstArmy.getName() + firstArmy.getNickname() + "."); 
			pw.println("\t!LOSER! " + secondArmy.getScenario() + secondArmy.getFactionName().toLowerCase() +
								" army, commanded by " + secondArmy.getName() + secondArmy.getNickname() + " was defeated!");
			if(!cw) victories[firstArmy.getFactionIndex(firstArmy)]++;
		} else if (firstArmy.getStrength() < secondArmy.getStrength()){
			
			pw.println("\t!VICTOR! " + secondArmy.getScenario() + secondArmy.getFactionName().toLowerCase() + " army has prevailed" +
					   " under commander " + secondArmy.getName() + secondArmy.getNickname() + "."); 
			pw.println("\t!LOSER! " + firstArmy.getScenario() + firstArmy.getFactionName().toLowerCase()+ " army, commanded by " + firstArmy.getName() 
						+ firstArmy.getNickname() +" was defeated!");
			if(!cw) victories[secondArmy.getFactionIndex(secondArmy)]++;
		} else pw.println("\t\tBoth armies are equal. It would be massacre!");
		
		for(int i = 0; i < 125; i++)pw.format("#");
		pw.println();*/
	}
}
