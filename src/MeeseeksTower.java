
/**
 * Meeseeks tower is a subclass of the tower class. It is one of the stronger
 * attack towers, as it has a 50% chance of doing double damage (implemented in
 * model).
 * @author David Gonzales, Mario Verdugo, Luke Cernetic, Chris Crabtree
 *
 */
public class MeeseeksTower extends Tower {

	/**
	 * Constructor for the meeseeks tower
	 */
	public MeeseeksTower() {
		super();
		this.cost = 10;
		this.attackPower = 5;
		this.towerPic = "pictures/meeseeks.png";
		this.towerPortrait = "pictures/meeseeksportrait.png";
		this.towerName = "pictures/meeseeksText.png";
	}
}
