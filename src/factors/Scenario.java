package factors;

import	java.util.Random;
import java.util.Scanner;

/**
 * Class Scenario control and sets scenario property
 * 
 * @author Viktor Szitkey
 * @version 1.3
 * */
public class Scenario {
	
	private static Random  randomGenerator = new Random();
	private static Scanner sc = new Scanner(System.in);
	private static String[] scenario = { "a", "d" };
	private static boolean task;
	
	/**
	 * setSituation method returns boolean that sets scenario for army
	 * 
	 * @param task True stands for attack, false for defend.
	 * 
	 * @return a boolean which value sets scenario for army
	 * 
	 * @see battle_phases.ArmyBuild#chooseSituation(String)
	 * */
	public static boolean setSituation(String random) {
		
		String temp = "r";
		if(random.equals("console")) {
			do {
				System.out.println("## Choose scenario for the first faction, will be "
									+ "this faction attacking(type attack) or defending(defend), random(type random)?:");
				temp = sc.next().substring(0, 1).toLowerCase();
			} while(!temp.equals("a") && !temp.equals("d") && !temp.equals("r"));
		}	
		if(random.equals("random") || temp.equals("r")) temp = scenario[randomGenerator.nextInt(scenario.length)]; 
		
		if(temp.equals("a")) task = true;
		else task = false;
		return task;	
	}
	
	/**
	 * getSituation method return negated value, that sets 
	 * other scenario for second army. Only called with second army.
	 * 
	 * @return a boolean
	 * 
	 * @see battle_phases.ArmyBuild#chooseSituation(String)
	 * */
	public static boolean getSituation() {
		return !task;
	}
}
