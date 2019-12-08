
public class GuardRickEnemy extends Enemy {

	public GuardRickEnemy() {
		super();
		this.towerName = "GuardRick";
		this.towerPic = "pictures/guardRick.png";
		//this.towerPortrait = "PathToPortrait";
		
	}
	
	public void takeDamage(int damageAmount) {
		// 25% chance that tiny rick avoids an attack
		int chanceHit = (int) (Math.random() * 100);
		if (chanceHit <= 75) {
			super.takeDamage(damageAmount);
		}
	}
}
