package factors;

import java.util.Scanner;
import java.util.Random;
import java.io.*;

import armies.*;
import soldier_types.*;

/**
 * Class Composition creates composition for both armies, 
 * either randomly or with user's input
 * 
 * @author Viktor Szitkey
 * @version 2.0
 * */
public class Composition {
	
	private Armies army;
	
	private static Random randomGenerator = new Random();
	
	private final double MIN = 0;
	private final double MAX = 3000;
	private final int RAN = 10;
	private double summary = 0;
	private int archers = 0, cavalry = 0, heavy = 0, pike = 0, light = 0;
	
	public Composition() {}
	
	/**
	 * chooseRandomComposition method sets composition to random values,
	 * not entirely random though, there is guarantee that faction bonus 
	 * and leaders specialization(if it's higher than average) put some base regiments into composition
	 * 
	 * @param RAN constant integer that sets maximal value for random generator
	 * @param MIN constant integer that sets minimum for value of army in random composition 
	 * */
	public void chooseRandomComposition(FactionEnum faction, Commander leader, boolean scenario, ArmiesEnum allegienceToArmy) {
		
		int[] factionBonus = Faction.getRandomFactionBonus(faction); 
		
		do {
			summary = 0;
			
			this.archers = randomGenerator.nextInt(RAN) + leader.getRandomSpecialization("Archers") + factionBonus[0];
			summary += (UnitsEnum.ARCHERS.getUnitTypeCost() * archers);
			
			this.cavalry = randomGenerator.nextInt(RAN) + leader.getRandomSpecialization("Cavalry") + factionBonus[1];
			summary += (UnitsEnum.CAVALRY.getUnitTypeCost() * cavalry);
				
			this.heavy = randomGenerator.nextInt(RAN) + leader.getRandomSpecialization("Heavy Infantry") + factionBonus[2];
			summary += (UnitsEnum.HEAVY.getUnitTypeCost() * heavy);
			
			this.pike = randomGenerator.nextInt(RAN) + leader.getRandomSpecialization("Pikemen") + factionBonus[3];
			summary += (UnitsEnum.PIKE.getUnitTypeCost() * pike);
			
			this.light = randomGenerator.nextInt(RAN) + leader.getRandomSpecialization("Light Infantry") + factionBonus[4];
			summary += (UnitsEnum.LIGHT.getUnitTypeCost() * light);
		
		} while(!(this.archers >= 0 && this.cavalry >= 0 && this.heavy >= 0 && this.pike >= 0 && this.light >= 0 && summary <= MAX && summary >= MIN));
		
		this.createArmy(faction, leader, scenario, allegienceToArmy);	
		
	}
	
	public void createArmy(FactionEnum faction, Commander leader, boolean scenario, ArmiesEnum allegienceToArmy) {
		
		switch(faction) {
		  case ROMAN: 
			  army =  new Roman(archers, cavalry, heavy, pike, light, scenario, allegienceToArmy);
		  	  break;
		  case CARTHAGINIAN: 
			  army =  new Carthaginian(archers, cavalry, heavy, pike, light, scenario,  allegienceToArmy);
		  	  break;
		  case GREEK: 
			  army =  new Greek(archers, cavalry, heavy, pike, light, scenario, allegienceToArmy);
		  	  break;
		  case CELTIC: 
			  army =  new Celtic(archers, cavalry, heavy, pike, light, scenario, allegienceToArmy);
		  	  break;
		}
	}
	
	public double summary() {
		
		summary = 0;
		
		summary += (UnitsEnum.ARCHERS.getUnitTypeCost() * archers);	
		summary += (UnitsEnum.CAVALRY.getUnitTypeCost() * cavalry);
		summary += (UnitsEnum.HEAVY.getUnitTypeCost() * heavy);
		summary += (UnitsEnum.PIKE.getUnitTypeCost() * pike);
		summary += (UnitsEnum.LIGHT.getUnitTypeCost() * light);
		
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
	
	public int getFactionCount() { return army.getArmyCount(); }
	public double getFactionSummary() { return summary; }
	public int getUnitNumber(int i) { return army.getUnitNumber(i); } //TODO this might be nonsense
	public Integer[] getComposition() { 
		Integer[] types = new Integer[5];
		types[0] = archers; types[1] = cavalry;
		types[2] = heavy; types[3] = pike;
		types[4] = light;
		return types; 
	}
	public String[] getUnitsName() { return army.getUnitsName(); }

	public Armies getArmy() { return army; }
}
