package armies;

/**
 * Class Roman is a reference to Roman faction
 * 
 *  @author Viktor Szitkey
 *  @version 2.0
 * */
public class Roman extends Armies {

	public Roman(int archers, int cavalry, int heavy, int hoplites, int light, boolean situation, ArmiesEnum allegienceToArmy){
		super(15, 25, 25, 10, 15, 25, 5, 20, 15, 10, archers, cavalry, heavy, hoplites, light, situation, allegienceToArmy);
	}
}
