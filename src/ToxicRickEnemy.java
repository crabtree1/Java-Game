/**
 * ToxicRickEnemy is a subclass of Enemy. Toxic Rick is pretty tough to beat
 * since it has a 3 hit slime shield, meaning it must be hit 4 times for any
 * damage to be done.
 * @author David Gonzales, Mario Verdugo, Luke Cernetic, Chris Crabtree
 *
 */
public class ToxicRickEnemy extends Enemy {
	private int numHits;
	
	/**
	 * Toxic Rick constructor
	 */
	public ToxicRickEnemy() {
		super();
		this.towerName = "ToxicRick";
		this.towerPic = "pictures/toxicRick.png";
		this.numHits = 0;
	}
	
	/**
	 * Method to do damage to toxic rick, but damage is only done once every
	 * 4 hits.
	 */
	public void takeDamage(int damageAmount) {
		// takes 3 hits to break through the grime shield
		numHits ++;
		if (numHits % 4 == 0) {
			super.takeDamage(damageAmount);
		}
	}
}
