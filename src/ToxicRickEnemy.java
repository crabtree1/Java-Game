import javafx.scene.paint.Paint;

public class ToxicRickEnemy extends Enemy {
	private int numHits;
	
	public ToxicRickEnemy() {
		super();
		this.towerName = "ToxicRick";
		this.towerPic = "pictures/toxicRick.png";
		this.numHits = 0;
		//this.towerPortrait = "PathToPortrait";
	}
	
	public void takeDamage(int damageAmount) {
		// takes 3 hits to break through the grime shield
		numHits ++;
		if (numHits % 4 == 0) {
			super.takeDamage(damageAmount);
		}
	}
}
