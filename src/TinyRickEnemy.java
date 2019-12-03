
public class TinyRickEnemy extends Enemy {

	public TinyRickEnemy() {
		super();
		this.towerName = "TinyRick";
		this.towerPic = "yellow";
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
