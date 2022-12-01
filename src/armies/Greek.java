package armies;

/**
 * Class Greek is a reference to greek faction
 * 
 *  @author Viktor Szitkey
 *  @version 1.3
 * */
public class Greek extends Armies {
	
	public Greek(int archers, int cavalry, int heavy, int hoplites, int light, boolean situation, ArmiesEnum allegienceToArmy){
		super(10, 20, 25, 10, 15, 25, 15, 30, 15, 10, archers, cavalry, heavy, hoplites, light, situation,  allegienceToArmy);
	}
}
