package factors;

import java.util.Scanner;
import java.util.InputMismatchException;
import java.util.Random;
import java.io.*;

import armies.*;
import gui.UnitsEnum;
import soldier_types.*;

/**
 * Class Composition creates composition for both armies, 
 * either randomly or with user's input
 * 
 * @author Viktor Szitkey
 * @version 1.3
 * */
public class Composition {
	
	private Armies army;
	
	private static Random randomGenerator = new Random();
	private static Scanner sc = new Scanner(System.in);
	private static PrintWriter pw = new PrintWriter(System.out, true);
	
	private final double MIN = 0;
	private final double MAX = 3000;
	private final int RAN = 5;
	private double summary = 0;
	private int archers = 0, cavalry = 0, heavy = 0, pike = 0, light = 0;
	private double strength = 0;
	
	public Composition() {}

	/**
	 * chooseComposition method let's user to choose his custom composition
	 *  
	 * @param summary integer to which variable archers, cavalry, heavy, hoplites, light add values,
	 * 			summary controls value of user's composition, so it can not be set over constant MAX
	 * @param MAX constant integer that sets limit to value of an army
	 * */
	public void chooseComposition(String choose, Commander leader, boolean scenario) {
		
		pw.println("## Choose composition of this faction, you can use up to " + MAX + " points, :");
		
		do {
			summary = 0;
			try{
				pw.println("* ARCHERS (200 men in unit) = 50 points per unit *");
				archers = sc.nextInt();
				summary += (getValue(new Archers()) * archers);
				if(summary > MAX) {pw.println("\t!Over limit! Cost of your army was " + summary + " , you can't go over " + MAX +"!"); continue;}
				pw.println("You have used " + summary + " of your points.");
				
				pw.println("* CAVALRY (100 men in unit) = 50 per unit *");
				cavalry = sc.nextInt();
				summary += (getValue(new Cavalry()) * cavalry);
				if(summary > MAX) {pw.println("\t!Over limit! Cost of your army was " + summary + " , you can't go over " + MAX +"!"); continue;}
				pw.println("You have used " + summary + " of your points.");
				
				pw.println("* HEAVY (200 men in unit) = 80 per unit *");
				heavy = sc.nextInt();
				summary += (getValue(new HeavyInfantry()) * heavy);
				if(summary > MAX) {pw.println("\t!Over limit! Cost of your army was " + summary + " , you can't go over " + MAX +"!"); continue;}
				pw.println("You have used " + summary + " of your points.");
				
				pw.println("* HOPLITES (200 men in unit) = 70 per unit *");
				pike = sc.nextInt();
				summary += (getValue(new Pikemen()) * pike);
				if(summary > MAX) {pw.println("\t!Over limit! Cost of your army was " + summary + " , you can't go over " + MAX +"!"); continue;}
				pw.println("You have used " + summary + " of your points.");
				
				pw.println("* LIGHT (400 men in unit) = 50 per unit *");
				light = sc.nextInt();
				summary += (getValue(new LightInfantry()) * light);
				if(summary > MAX) {pw.println("\t!Over limit! Cost of your army was " + summary + " , you can't go over " + MAX +"!"); continue;}
				pw.println("You have used " + summary + " of your points.");
				
				
				if(archers >= 0 && cavalry >= 0 && heavy >= 0 && pike >= 0 && light >= 0){
					String temp;
					do {
						pw.println("## You have used " + summary + " of your points. "
											+ "Do you wish to proceed(go with this composition yes, no = start all over)  ?");
						temp = sc.next().substring(0, 1).toLowerCase();
					} while(!(temp.equals("y") || temp.equals("n")));	
					if(temp.equals("n")) summary = MAX + 1;
						
					
				}
				else pw.println("## Choose only 0 or more than 0! ");
			}catch(InputMismatchException e) { 
				pw.println("TYPE ONLY NUMERALS!!! \nRANDOM COMPOSITION WILL BE SET FOR BOTH ARMIES!!!");
				this.chooseRandomComposition(choose, leader, scenario);
			} 
		} while(!(archers >= 0 && cavalry >= 0 && heavy >= 0 && pike >= 0 && light >= 0 && summary < MAX));
		
		this.chooseFaction(choose, archers, cavalry, heavy, pike, light, leader, scenario);
	}
	
	/**
	 * chooseRandomComposition method sets composition to random values,
	 * not entirely random though, there is guarantee that faction bonus 
	 * and leaders specialization(if it's higher than average) put some base regiments into composition
	 * 
	 * @param RAN constant integer that sets maximal value for random generator
	 * @param MIN constant integer that sets minimum for value of army in random composition 
	 * */
	public void chooseRandomComposition(String choose, Commander leader, boolean scenario) {
		
		int[] factionBonus = Faction.getRandomFactionBonus(choose); 
		
		
		
		do {
			summary = 0;
			
			archers = randomGenerator.nextInt(RAN) + leader.getRandomSpecialization("Archers") + factionBonus[0];
			summary += (getValue(new Archers()) * archers);	
			
			cavalry = randomGenerator.nextInt(RAN) + leader.getRandomSpecialization("Cavalry") + factionBonus[1];
			summary += (getValue(new Cavalry()) * cavalry);
				
			heavy = randomGenerator.nextInt(RAN) + leader.getRandomSpecialization("Heavy Infantry") + factionBonus[2];
			summary += (getValue(new HeavyInfantry()) * heavy);
			
			pike = randomGenerator.nextInt(RAN) + leader.getRandomSpecialization("Pikemen") + factionBonus[3];
			summary += (getValue(new Pikemen()) * pike);
			
			light = randomGenerator.nextInt(RAN) + leader.getRandomSpecialization("Light Infantry") + factionBonus[4];
			summary += (getValue(new LightInfantry()) * light);
			//pw.println(summary);
		
		} while(!(archers >= 0 && cavalry >= 0 && heavy >= 0 && pike >= 0 && light >= 0 && summary <= MAX && summary >= MIN));
		
		this.chooseFaction(choose, archers, cavalry, heavy, pike, light, leader, scenario);	
		
	}
	
	/**
	 * chooseFaction method creates instance of an army with composition leader and scenario.
	 * 
	 * @param choose a String that hold name of the faction, which army is about to be created
	 * */
	public void chooseFaction(String choose, int archers, int cavalry, int heavy, int pike, int light, Commander leader, boolean scenario) {
		
		switch(choose) {
		  case("Roman"): 
			  army =  new Roman(archers, cavalry, heavy, pike, light, scenario);
		      strength = army.getArmyStrength(leader);
		  	  break;
		  case("Carthagian"): 
			  army =  new Carthaginian(archers, cavalry, heavy, pike, light, scenario);
		      strength = army.getArmyStrength(leader);
		  	  break;
		  case("Greek"): 
			  army =  new Greek(archers, cavalry, heavy, pike, light, scenario);
		      strength = army.getArmyStrength(leader);
		  	  break;
		  case("Celtic"): 
			  army =  new Celtic(archers, cavalry, heavy, pike, light, scenario);
		      strength = army.getArmyStrength(leader);
		  	  break;
		}
	}
	
	public void chooseFaction(String choose, Commander leader, boolean scenario) {
		
		switch(choose) {
		  case("Roman"): 
			  army =  new Roman(archers, cavalry, heavy, pike, light, scenario);
		      strength = army.getArmyStrength(leader);
		  	  break;
		  case("Carthaginian"): 
			  army =  new Carthaginian(archers, cavalry, heavy, pike, light, scenario);
		      strength = army.getArmyStrength(leader);
		  	  break;
		  case("Greek"): 
			  army =  new Greek(archers, cavalry, heavy, pike, light, scenario);
		      strength = army.getArmyStrength(leader);
		  	  break;
		  case("Celtic"): 
			  army =  new Celtic(archers, cavalry, heavy, pike, light, scenario);
		      strength = army.getArmyStrength(leader);
		  	  break;
		}
	}
	
	public double summary() {
		
		summary = 0;
		
		summary += (getValue(new Archers()) * archers);	
		summary += (getValue(new Cavalry()) * cavalry);
		summary += (getValue(new HeavyInfantry()) * heavy);
		summary += (getValue(new Pikemen()) * pike);
		summary += (getValue(new LightInfantry()) * light);
		
		return summary/MAX;
	}
	
	public void setArchers(int value) { this.archers += value; }
	public void setCavalry(int value) { this.cavalry += value; }
	public void setHeavy(int value) { this.heavy += value; }
	public void setPikes(int value) { this.pike += value; }
	public void setLight(int value) { this.light += value; }
	
	public void changeUnit(UnitsEnum typeOfUnit, int value) {
		switch(typeOfUnit) {
			case ARCHERS:
				 this.archers = Math.max(0, this.archers + value);
				break;
			case CAVALRY:
				this.cavalry = Math.max(0, this.cavalry + value);
				break;
			case HEAVY:
				this.heavy = Math.max(0, this.heavy + value);
				break;
			case PIKE:
				this.pike = Math.max(0, this.pike + value);
				break;
			case LIGHT:
				this.light = Math.max(0, this.light + value);
				break;
			default:
				System.out.println("Unknown unit type!!!");
		}	
	}
	/**
	 * getValue method returns value of certain unit type for methods
	 * chooseComposition and chooseRandomComposition
	 * 
	 * @return integer value of certain unit type
	 * 
	 * @see {@link #chooseComposition(String choose, Commander leader, boolean scenario)}
	 * @see {@link #chooseRandomComposition(String choose, Commander leader, boolean scenario)}
	 * */
	public int getValue(Units unit) { return Armies.getValue(unit); }
	
	public double getFactionStrength() { return strength; }
	public double getFactionCount() { return army.getArmyCount(); }
	public double getFactionSummary() { return summary; }
	public int getUnitNumber(int i) { return army.getUnitNumber(i); } // this might be nonsense
	public Integer[] getComposition() { 
		Integer[] types = new Integer[5];
		types[0] = archers; types[1] = cavalry;
		types[2] = heavy; types[3] = pike;
		types[4] = light;
		return types; 
	}
	public String[] getUnitsName() { return army.getUnitsName(); }

	public int getArchers() {
		return archers;
	}

	public int getCavalry() {
		return cavalry;
	}

	public int getHeavy() {
		return heavy;
	}

	public int getPike() {
		return pike;
	}

	public int getLight() {
		return light;
	}
}
