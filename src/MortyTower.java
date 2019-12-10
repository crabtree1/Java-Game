
/**
 * MortyTower is a subclass of Tower. Morty is a reliable tower, consistently
 * doling out 5 points of damage to any enemy in its range.
 * @author David Gonzales, Mario Verdugo, Luke Cernetic, Chris Crabtree
 *
 */
public class MortyTower extends Tower {
	
	public static final int type = 3;
	
	/**
	 * Constructor for the morty towers
	 */
	public MortyTower() {
		super();
		this.attackPower = 5;
		this.towerPic = "pictures/morty.png";
		this.towerPortrait = "pictures/mortyportrait.png";
		this.towerName = "pictures/mortyText.png";
	}
}
