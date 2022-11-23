package soldier_types;

/**
 * Class LightInfatry is an instance of light infantry regiment 
 * 
 * @author Viktor Szitkey
 * @version 1.3
 * */
public class LightInfantry extends Units {
	
	private int value = 50;
	private String name = "Light Infantry";
	
	public LightInfantry() {
		super(2, 400);
	}
	
	public int getValue() {	return value; }
	
	public String getName() { return name; }
}
