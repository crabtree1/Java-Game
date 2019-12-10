/**
 * RickTower is a subclass of Tower. Rick towers are very reliable. When in 
 * danger, Rick Tower will always dole out 10 points of damage to any enemies
 * in its range.
 * @author David Gonzales, Mario Verdugo, Luke Cernetic, Chris Crabtree
 *
 */
public class RickTower extends Tower {
	
	public static final int type = 4;
	
	/**
	 * Constructor for the Rick tower
	 */
	public RickTower() {
		super();
		this.attackPower = 10;
		this.towerPic = "pictures/rick.png";
		this.towerPortrait = "pictures/rickportrait.png";
		this.towerName = "pictures/rickText.png";
	}
}
