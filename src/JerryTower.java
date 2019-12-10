/**
 * JerryTower is a subclass of Tower. Jerry is a hilariously inept defender,
 * like everything else he does. He is stronger attacking Doofus Rick's than all
 * other enemy types.
 * @author David Gonzales, Mario Verdugo, Luke Cernetic, Chris Crabtree
 *
 */
public class JerryTower extends Tower {
	public static final int type = 1;
	/**
	 * Constructor for the jerry tower
	 */
	public JerryTower() {
		super();
		this.cost = 50;
		this.towerPic = "pictures/jerry.png";
		this.towerPortrait = "pictures/jerryportrait.png";
		this.towerName = "pictures/jerryText.png";
	}
}
