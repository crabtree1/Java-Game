/**
 * Class BirdPersonTower is a subclass of Tower. It has a cost of 5 and
 * attacks all enemies on the board, no matter the location.
 * @author David Gonzales, Mario Verdugo, Luke Cernetic, Chris Crabtree
 *
 */
public class BirdPersonTower extends Tower{
	
	/**
	 * Constructor for the bird person class
	 */
	public BirdPersonTower() {
		super();
		this.cost = 5;
		this.attackPower = 1;
		this.towerPic = "pictures/birdperson.png";
		this.towerPortrait = "pictures/birdpersonportrait.png";
		this.towerName = "pictures/birdpersonText.png";
	}
	
}
