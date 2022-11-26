package factors;

import java.util.Random;
import java.util.Scanner;
import java.io.*;

/**
 * Class Commander creates instances of commanders of armies, 
 * which greatly influence outcome of simulation
 * 
 * @author Viktor Szitkey
 * @version 1.3
 * */
public class Commander {
	
	private String name;
	private Integer skill;
	private String nickname;
	private String specialization;
	
	private Integer MAXSKILLEVEL = 5; 
	
	private static String[] nicknames = { "Ironborne", "Skullcrusher", "Young Wolf", "White Wolf", "Java Guru" };
	private static String[] names = { "Julius Ceasar", "Augustus", "Haniball Barca", "Alexander the Great", "Boudicca" };
	private static String[] competence = { "bad", "average", "superb", "genius" };
	private static String[] types = { "archers", "cavalry", "heavy infantry", "hoplites", "light infantry" };
	
	private static Random randomGenerator = new Random();
	private static Scanner sc = new Scanner(System.in);
	private static PrintWriter pw = new PrintWriter(System.out, true);
	
	public Commander() {
		this.skill = 1; // TODO shouldn't be constant 
		this.specialization = "archers"; // TODO shouldn't be constant
	}

	/**
	 * Commander constructor creates instance of Commander class
	 * 
	 * @param name a String containing name of the commander
	 * @param skill a String containing of the commander
	 * @param specialization a String containing specialization of the commander
	 * @param nickname a String containing nickname of the commander
	 * */
	public Commander(String name, String skill, String specialization, String nickname) {
		this.name = name;
		//this.skill = skill;
		this.specialization = specialization;
		this.nickname = nickname;
	}
	
	public Commander(Integer skill) {
		this.skill = skill;
	}

	
	public String getName() { return name; }
	
	public void setSkill(Integer valueOfSkill) { skill = valueOfSkill;  }
	public double getSkill() { return skill; };
	
	public String getSpecialization(){ return specialization; }
	public String getNickname() {
		if(nickname.equals("")) return "";	
		return (" \"" + nickname +"\"");
	}
	
	/**
	 * getValueOfSkill method return number equivalent of commander's skill
	 * 
	 * @return double number equivalent of commander's skill
	 * */
	public double getValueOfSkill(){ return skill; }
	
	/**
	 * getModifier method returns different double value
	 * 
	 * @param type a String containing type of unit
	 * 
	 * @return double value
	 * */
	public double getModifier(String type){
		if(type.substring(0, 2).toLowerCase().equals(specialization.substring(0, 2))) return 1.5;				
		return 1;
	}
	
	/**
	 * getRandomSpecialization method may returns value, that increasing chance of choosing 
	 * this type of unit in random generated army composition
	 * 
	 * @return integer value that either increase or not changes chance of choosing this type of unit in composition 
	 * */
	public int getRandomSpecialization(String type){
		//TODO set this function is messy in name and fucntion it makes random composition to get at least 5 units of leader specialization
		//if(type.substring(0, 2).equals(specialization.substring(0, 2))) return 5;				
		return 0;
	}
	
	/**
	 * customization method gives user option to select way how will be commander create
	 * 
	 * @return a String that holds answer for further processing
	 * */
	private static String customization() {
		String random;
		do {
		pw.println("##  Customize commander (type set), or we let us do it for you (type random)):");
		random = sc.next().substring(0, 1).toLowerCase();
		} while (!random.equals("s") && !random.equals("r"));
		if(random.equals("r")) random = "random";
		return random;
	}
	
	/**
	 * chooseCommanderName method gives user option to select commander's name
	 * 
	 * @return a String containing name of commander 
	 * */
	private static String chooseCommanderName(String random) {
		String name;
		
		if(random.equals("random")) { name = names[randomGenerator.nextInt(names.length)]; return name; }
		
		pw.println("## What is his name?(your string or type random):");
		name = sc.next();
		if(name.equals("random")) {
			name = names[randomGenerator.nextInt(names.length)]; 
		}
		return name;
	}
	
	/**
	 * chooseCommanderSkill method gives user option to select commander's skill
	 * 
	 * @return a String containing skill of commander 
	 * */
	private static String chooseCommanderSkill(String random) {
		String skill;
		
		if(random.equals("random")) { skill = competence[randomGenerator.nextInt(competence.length)].toLowerCase(); return skill; }
		do {
			pw.println("## How good is this commander(bad, average, superb = specialization bonus, genius = SP + nickname, or type random)?:");
			skill = sc.next().toLowerCase();
		} while(!(skill.equals("bad") || skill.equals("average") || skill.equals("superb") || skill.equals("genius") || skill.equals("random")));
		if(skill.equals("random")) {
			
			skill = competence[randomGenerator.nextInt(competence.length)]; 
		}
		return skill;
	}
	
	/**
	 * specifySpecialization method gives user option to select commander's specialization
	 * 
	 * @return a String containing specialization of commander
	 * */
	private static String specifySpecialization(String skill, String random) { 
		String specialization = "None";
		if(skill.equals("bad") ||  skill.equals("average")) return specialization;
		if(random.equals("random")) { specialization = types[randomGenerator.nextInt(types.length)]; return specialization; }
		do{
			if(skill.equals("superb")){
				pw.println("##  Your superb general has specialization, choose(Archers, Cavalry, Heavy(infantry), Hoplites, Light(infantry), or type random?:");
				specialization = sc.next().toLowerCase();
				if(specialization.toLowerCase().equals("random")) { specialization = types[randomGenerator.nextInt(types.length)]; break; } 			
			}
			else if(skill.equals("genius")) {
				pw.println("## Your genial general has specialization, choose(Archers, Cavalry, Heavy(infantry), Hoplites, Light(infantry), type random?:");
				specialization = sc.next().toLowerCase();
				if(specialization.toLowerCase().equals("random")) { specialization = types[randomGenerator.nextInt(types.length)]; break; } 			
			}
		} while(!(specialization.equals("archers") || specialization.equals("cavalry") || specialization.equals("heavy") 
				|| specialization.equals("hoplites") || specialization.equals("light")));
		
		if(specialization.equals("heavy") || specialization.equals("light")) specialization += " infantry";
		return specialization; 
	}	
	
	/**
	 * setNickname method gives user option to select commander's nickname
	 * 
	 * @return a String containing nickname of commander
	 * */
	private static String setNickname(String skill,String random) {
		
		String nickname = "";
		if(!skill.equals("genius")) return nickname;
		if(random.equals("random")) { nickname = nicknames[randomGenerator.nextInt(nicknames.length)]; return nickname; } 
		
		pw.println("## Choose his nickname (type nickname or random): ");
		nickname = sc.next();
		if(nickname.equals("random")) nickname = nicknames[randomGenerator.nextInt(nicknames.length)];
		
		return nickname;
	}
	
	/**
	 * createCommander method creates instance of class commander
	 * 
	 * @param name a String that holds name of the commander
	 * @param skill a String that holds skill of the commander
	 * @param nickname a String that holds nickname of the commander
	 * @param specialization a String that holds specialization of the commander
	 * 
	 * @return a reference to Commander Class
	 * */
	public static Commander createCommander(Integer value) {
		Integer skill;
		String name, nickname, specialization;
		
		//name = chooseCommanderName(random);
		skill = value;
		//specialization = specifySpecialization(skill, random);
		//nickname = setNickname(skill,random);
		return new Commander(skill);
		//return new Commander(name, skill, specialization, nickname);
	}
	
	public void randomCommander() {
		
		this.specialization = "None";
		
		this.skill = randomGenerator.nextInt(MAXSKILLEVEL);
		
		if(skill > 2) { this.specialization = types[randomGenerator.nextInt(types.length)]; }
		
	
	}
}
