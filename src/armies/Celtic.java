package armies;

/**
 * Class Celtic is a reference to celtic faction
 * 
 *  @author Viktor Szitkey
 *  @version 1.3
 * */
public class Celtic extends Armies {
	
	public Celtic(int archers, int cavalry, int heavy, int hoplites, int light, boolean situation, ArmiesEnum allegienceToArmy){
		super(20, 30, 25, 10, 5, 15, 10, 25, 15, 10, archers, cavalry, heavy, hoplites, light, situation, allegienceToArmy);
	}
}
