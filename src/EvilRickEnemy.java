/**
 * EvilRickEnemy is a subclass of Enemy. It is one of the stronger enemies
 * since it has a shield that requires 2 hits to perform the damage that normally
 * only takes one on most other enemies
 * @author David Gonzales, Mario Verdugo, Luke Cernetic, Chris Crabtree
 *
 */
public class EvilRickEnemy extends Enemy {
	private int numHits;

	/**
	 * Constructor for the evil rick class
	 */
	public EvilRickEnemy() {
		super();
		this.towerName = "EvilRick";
		this.towerPic = "pictures/evilRick.png";
	}
	
	/**
	 * Method to represent evil rick being attacked by a tower. Since evil rick
	 * has a protective shield, only every other hit will do damage
	 * @param damageAmount - amount of damage the tower is doing
	 */
	public void takeDamage(int damageAmount) {
		numHits ++;
		if (numHits % 2 == 0) {
			super.takeDamage(damageAmount);
		}
	}
}
