package soldier_types;

/**
 * Interface RandomFactors implements it's methods to classes Archers and Cavalry
 * */
public interface RandomFactors {
 
	public double weatherValue(String weather);	
	public double locationValue(String location, boolean attack);
}
