package armies;

/**
 * Class Carthaginian is a reference to carthaginian faction
 * 
 *  @author Viktor Szitkey
 *  @version 1.3
 * */
public class Carthaginian extends Armies {

	public Carthaginian(int archers, int cavalry, int heavy, int hoplites, int light, boolean situation){
		super(15, 25, 30, 15, 5, 15, 15, 30, 10 , 5, archers, cavalry, heavy, hoplites, light, situation);
	}
}
