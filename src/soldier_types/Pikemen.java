package soldier_types;

/**
 * Class Hoplites is an instance of hoplites regiment 
 * 
 * @author Viktor Szitkey
 * @version 1.3
 * */
public class Pikemen extends Units {
	
	private int value = 70;
	private String name = "Hoplites";
	
	public Pikemen() {
		super(5, 200);
	}
	
	public int getValue() { return value; }
	
	public String getName() { return name; }
}
