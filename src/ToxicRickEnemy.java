import javafx.scene.paint.Paint;

public class ToxicRickEnemy extends Enemy {
	private int timesHit;
	
	public ToxicRickEnemy() {
		super();
		this.towerName = "ToxicRick";
		this.timesHit = 0;
		this.towerPic = "blue";
		//this.towerPortrait = "PathToPortrait";
	}
	
	public void takeDamage() {
		if (timesHit % 2 == 0) {
			super.takeDamage();
		}
		
		timesHit += 1;
	}
}
