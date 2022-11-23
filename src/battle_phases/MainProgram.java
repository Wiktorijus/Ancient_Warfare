package battle_phases;

import java.io.*;
import java.util.Scanner;

/**
 * Class MainProgram contains main method and other methods used for acquisition of user's input,
 * upon which certain scenario of program will run.
 * 
 *  @author Viktor Szitkey
 *  @version 1.3
 * */
public class MainProgram {
	
	private static PrintWriter pw = new PrintWriter(System.out, true);
	private static Scanner sc = new Scanner(System.in);
	private static String random = "console"; 
	private static int n = 0;
	
	/**
	 * welcome method tells what to expect from program
	 * and gives some information
	 * 
	 * @param pw Used as replacement of "System.out".
	 * */
	public static void welcome() {
		
		for(int i = 0; i < 98; i++)pw.print("-");
		pw.println("\n- Welcome to Battle Simulator(v. 1.3), created by. Viktor Szitkey, for purposes of ZOOP class.   -");
		pw.println("- In a moment, you'll asked few questions, upon which program will create small simulation.           -\n" 
				+ "- Almost every question have random option, so you don't need put everything by yourself, enjoy! -");
		for(int i = 0; i < 98; i++)pw.print("-");
		pw.println();
	}

	/**
	 * choose method asks user what scenario will program run,
	 * either console input, user will customize input for program (one simulation),
	 * or random, which makes every needed input random and gives user
	 * options like running more simulations and saving results to file.
	 * 
	 * @param input the String used for saving user input
	 * @param c the character used for testing allowed input
	 * @param random a String which value helps determined scenarios    
	 * */
	public static void choose() {
		String input;
		char c;
		do {
			pw.println("Choose main Input(type console or random Input):");
			input = sc.next();
			c = input.toLowerCase().charAt(0);
		}	
		while(!(c == 'c' || c == 'r'));
		if(c == 'r') {
			
			random = "random";
			do {
				pw.println("Choose number of simulations(1-10000)?:");
				n = sc.nextInt();
			}while(!(n > 0 && n <= 10000));
			do {
				pw.println("Should program save the results(yes/no)?:");
				input = sc.next();
				c = input.toLowerCase().charAt(0);
			}	
			while(!(c == 'y' || c == 'n'));	
			runRandomProgram(c);
		}
		else {
			runConsoleProgram();
		}
	}	

	/**
	 * method runConsoleProgram runs one of the scenario.
	 * In this case program will run single simulation, in which 
	 * randomness of simulation is completely customized by user.
	 * 
	 * @param firstArmy the instance of ArmyBuild,
	 * 					used as representation of the first "army",
	 * 					participating in battle (simulation)
	 * @param secondArmy the instance of ArmyBuild,
	 * 					representing second "army",
	 * 					opponent to first "army"
	 * */
	private static void runConsoleProgram() {
		
		ArmyBuild firstArmy = new ArmyBuild();
		ArmyBuild secondArmy = new ArmyBuild();
		
		ArmyBuild.chooseLocation(random);
		ArmyBuild.chooseWeather(random);
		
		//firstArmy.createCommander(random);
		firstArmy.chooseSituation(random);
		//firstArmy.chooseArmy(random);
		
		//secondArmy.createCommander(random);
		secondArmy.chooseSituation(random);
		//secondArmy.chooseArmy(random);
	
		Result.finalResult(firstArmy, secondArmy);
	}

	/**
	 * method runRandomProgram is a counterPart to runConsoleProgram method.
	 * This method is used when user need more simulations with single run of program.
	 * Also, results of these simulations can be save in text file.  
	 * 
	 * @see #runConsoleProgram() 
	 * */
	private static void runRandomProgram(char c) {
		
		for(int i = 0; i < n; i++) {
			ArmyBuild firstArmy = new ArmyBuild();
			ArmyBuild secondArmy = new ArmyBuild();
			
			ArmyBuild.chooseLocation(random);
			ArmyBuild.chooseWeather(random);
			
			//firstArmy.createCommander(random);
			firstArmy.chooseSituation(random);
			//firstArmy.chooseArmy(random);
			
			//secondArmy.createCommander(random);
			secondArmy.chooseSituation(random);
			//secondArmy.chooseArmy(random);
	
			Result.finalResult(firstArmy, secondArmy);
			
			firstArmy.reset();
		}
		if(c == 'y') { FileControl.writeTheResult(); }		
	}
	
	/**
	 * main method calls welcome method and more importantly
	 * choose method, which determines scenario.
	 * */
	public static void main(String[] args) {
		
		MainProgram.welcome();
		MainProgram.choose();
	}
}
