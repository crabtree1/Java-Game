
public class EvilRickEnemy extends Enemy {
	private int numHits;

	public EvilRickEnemy() {
		super();
		this.towerName = "EvilRick";
		this.towerPic = "white";
		//this.towerPortrait = "PathToPortrait";
	}
	
	public void takeDamage(int damageAmount) {
		numHits ++;
		if (numHits % 2 == 0) {
			super.takeDamage(damageAmount);
		}
	}
}
