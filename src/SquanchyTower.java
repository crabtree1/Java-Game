/**
 * Class SquanchyTower is a subclass of Tower. Squanchy is a pretty powerful tower,
 * and it has a 25% chance of doing quadruple damage to an enemy in its vicinity.
 * @author David Gonzales, Mario Verdugo, Luke Cernetic, Chris Crabtree
 *
 */

public class SquanchyTower extends Tower {
	
	/**
	 * Constructor for the SquanchyTower class
	 */
	public SquanchyTower() {
		super();
		this.cost = 15;
		this.attackPower = 15;
		this.towerPic = "pictures/squanchy.png";
		this.towerPortrait = "pictures/squanchyportrait.png";
		this.towerName = "pictures/squanchyText.png";
	}
}