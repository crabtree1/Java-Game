/**
 * GuardRickEnemy is a subclass of Enemy. Guard Ricks are a mostly basic
 * enemy, but they have a 25% chance to avoid any given attack from a
 * tower.
 * @author David Gonzales, Mario Verdugo, Luke Cernetic, Chris Crabtree
 *
 */
public class GuardRickEnemy extends Enemy {

	/**
	 * Constructor for guard rick
	 */
	public GuardRickEnemy() {
		super();
		this.towerName = "GuardRick";
		this.towerPic = "pictures/guardRick.png";		
	}
	
	/**
	 * Method to take damage, but only if guard rick does not successfully
	 * dodge the attack
	 * @param damageAmount - amount of damage guard rick may take
	 */
	public void takeDamage(int damageAmount) {
		// 25% chance that tiny rick avoids an attack
		int chanceHit = (int) (Math.random() * 100);
		if (chanceHit <= 75) {
			super.takeDamage(damageAmount);
		}
	}
}
