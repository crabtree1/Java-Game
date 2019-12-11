/**
 * Class DoofusRickEnemy is a subclass of Enemy. It is not a very scary
 * enemy, and it is especially susceptible to attacks from JerryTowers.
 * @author David Gonzales, Mario Verdugo, Luke Cernetic, Chris Crabtree
 *
 */

public class DoofusRickEnemy extends Enemy {

	/**
	 * Constructor for the doofus Rick enemy
	 */
	public DoofusRickEnemy() {
		super();
		this.towerName = "DoofusRick";
		this.towerPic = "pictures/doofusRick.png";		
		this.health = 20;
	}
}
