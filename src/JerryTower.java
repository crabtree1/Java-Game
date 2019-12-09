
public class JerryTower extends Tower {
	
	public static final int type = 1;
	
	//jerry is weak so he only does the default 1 damage
	public JerryTower() {
		super();
		this.cost = 50;
		this.towerPic = "pictures/jerry.png";
		this.towerPortrait = "pictures/jerryportrait.png";
		this.towerName = "pictures/jerryText.png";
	}
}
