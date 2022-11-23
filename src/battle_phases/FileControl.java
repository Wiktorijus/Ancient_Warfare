package battle_phases;

import java.io.*;

import factors.Faction;

/**
 * Class FileControl handles saving the results of the simulation to a file
 * 
 *  @author Viktor Szitkey
 *  @version 1.3
 * */
public class FileControl {
	
	private static RandomAccessFile counterReader;
	private static RandomAccessFile resultOutput;
	private static int resultCounter;
	private static int[] victories = new int[4];
	private static double summary = 0;
	
	/**
	 * writeResult method opens files, handles exceptions and calls
	 * 
	 * @param counterReader reference for file, that contains control data
	 * @param resultOutput reference for file
	 * */
	public static void writeTheResult()  {
		
		try {
	 		counterReader = new RandomAccessFile("counter.txt", "rw");
			resultOutput = new RandomAccessFile("results.txt", "rw");
			
			getSavedData();
			saveNewResults();
			saveNewData();
			
			counterReader.close();
			resultOutput.close();
			
		}catch(IOException e) {
			System.out.println("IOException error");
		}
	}
	
	/**
	 * getSavedData method loads data from previous simulations and rewrite some of them
	 * 
	 * @param resultCounter an integer that holds count of simulations
	 * @param victories an integer field containing victories of previous simulations for each faction
	 * */
	private static void getSavedData() throws NumberFormatException, IOException {
		
		resultCounter =  Integer.parseInt(counterReader.readLine());
		if( resultCounter == -1) resultCounter = 1;
		
		if( resultCounter != 1) {
			for(int i = 0; i < victories.length; i++) {
				victories[i] = Integer.parseInt(counterReader.readLine()) + Result.victories[i];
				summary += victories[i];
			}
		} else {
			for(int i = 0; i < victories.length; i++) {
				victories[i] = Result.victories[i];
				summary += victories[i];
			}
		}
	}
	
	/**
	 * saveNewResults method saves new results to file
	 * 
	 * @param firstLineLength a integer that holds length of first line
	 * */
	private static void saveNewResults() throws IOException {
		
		int firstLineLength = resultOutput.readLine().length();
		
		resultOutput.seek(firstLineLength);
		
		for(int i = 0; i < victories.length; i++) {
			if(i == 0) resultOutput.writeBytes(String.format("\n%s %.2f\n",Faction.getFaction(i), (victories[i]*100)/summary));
			else {	
				resultOutput.writeBytes(String.format("%s %.2f\n",Faction.getFaction(i), (victories[i]*100)/summary));
			}
		}
		resultOutput.seek(resultOutput.length());
		
		resultOutput.writeBytes("\t\t\t\n" + resultCounter + ". simulation\n#");
		
		for(int i = 0; i < Result.victories.length; i++) {
			resultOutput.writeBytes(String.format("\t %s |%02d|",Faction.getFaction(i), Result.victories[i]));
		}
		resultOutput.writeBytes(String.format("\tCivil wars: |%02d| #\n", Result.cwNumber));
	
		for(int i = 0; i < 130; i++){ resultOutput.writeBytes("-");}
	}

	/**
	 * saveNewData saves data from current simulation and increment simulation counter
	 * */
	private static void saveNewData() throws IOException {
		counterReader.seek(0);
		counterReader.writeBytes(String.format("%s\n", ++resultCounter));
		
		for(int i = 0; i < victories.length; i++) {
			counterReader.writeBytes(String.format("%s\r\n", (victories[i] + Result.victories[i])));
		}
	}
}
