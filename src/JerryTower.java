/**
 * JerryTower is a subclass of Tower. Jerry is a hilariously inept defender,
 * like everything else he does. He is the only tower that can kill doofus rick
 * and can also affect toxic rick's grime shield.
 * @author David Gonzales, Mario Verdugo, Luke Cernetic, Chris Crabtree
 *
 */
public class JerryTower extends Tower {

	/**
	 * Constructor for the jerry tower
	 */
	public JerryTower() {
		super();
		this.cost = 1;
		this.attackPower = 0;
		this.towerPic = "pictures/jerry.png";
		this.towerPortrait = "pictures/jerryportrait.png";
		this.towerName = "pictures/jerryText.png";
	}
}
