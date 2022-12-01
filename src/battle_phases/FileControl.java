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
	
	private RandomAccessFile counterReader;
	private RandomAccessFile resultOutput;
	private int resultCounter;
	private int[] victoriesAll = new int[4];
	private double summary = 0;
	private int civilWarCounter = 0;
	private int[] victories;
	/**
	 * writeResult method opens files, handles exceptions and calls
	 * 
	 * @param counterReader reference for file, that contains control data
	 * @param resultOutput reference for file
	 * */
	public void writeTheResult(int civilWar, int[] victories)  {
		
		this.civilWarCounter = civilWar;
		this.victories = victories;
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
	 * @param victoriesAll an integer field containing victories of previous simulations for each faction
	 * */
	private void getSavedData() throws NumberFormatException, IOException {
		
		resultCounter =  Integer.parseInt(counterReader.readLine());
		if( resultCounter == -1) resultCounter = 1;
		
		if( resultCounter != 1) {
			for(int i = 0; i < victoriesAll.length; i++) {
				victoriesAll[i] = Integer.parseInt(counterReader.readLine()) + victories[i];
				summary += victoriesAll[i];
			}
		} else {
			for(int i = 0; i < victoriesAll.length; i++) {
				victoriesAll[i] = victories[i];
				summary += victoriesAll[i];
			}
		}
	}
	
	/**
	 * saveNewResults method saves new results to file
	 * 
	 * @param firstLineLength a integer that holds length of first line
	 * */
	private void saveNewResults() throws IOException {
		
		int firstLineLength = resultOutput.readLine().length();
		
		resultOutput.seek(firstLineLength);
		
		for(int i = 0; i < victoriesAll.length; i++) {
			if(i == 0) resultOutput.writeBytes(String.format("\n%s %.2f\n",Faction.getFaction(i), (victoriesAll[i]*100)/summary));
			else {	
				resultOutput.writeBytes(String.format("%s %.2f\n",Faction.getFaction(i), (victoriesAll[i]*100)/summary));
			}
		}
		resultOutput.seek(resultOutput.length());
		
		resultOutput.writeBytes("\t\t\t\n" + resultCounter + ". simulation\n#");
		
		for(int i = 0; i < Result.victories.length; i++) {
			resultOutput.writeBytes(String.format("\t %s |%02d|",Faction.getFaction(i), victories[i]));
		}
		resultOutput.writeBytes(String.format("\tCivil wars: |%02d| #\n", civilWarCounter));
	
		for(int i = 0; i < 130; i++){ resultOutput.writeBytes("-");}
	}

	/**
	 * saveNewData saves data from current simulation and increment simulation counter
	 * */
	private void saveNewData() throws IOException {
		counterReader.seek(0);
		counterReader.writeBytes(String.format("%s\n", ++resultCounter));
		
		for(int i = 0; i < victoriesAll.length; i++) {
			counterReader.writeBytes(String.format("%s\r\n", (victoriesAll[i] + victories[i])));
		}
	}
}
