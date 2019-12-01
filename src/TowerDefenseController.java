

public class TowerDefenseController {

	TowerDefenseModel model;
	
	public TowerDefenseController(TowerDefenseModel model) {
		this.model = model;
	}
	
	public Road getRoad() {
		return model.getRoad();
	}
	
	public Tower[][] getTowerMap(){
		return model.getTowerMap();
	}
	
	public void addTower(Tower currTowerClicked, double mouseX, double mouseY) {
		int row = 0;
		int col = 0;
		
		int lb;
		int ub;
		
		for (int i = 0; i < 15; i++) {
			lb = 47 * i;
			ub = 47 * (i + 1);
			if (mouseX >= lb && mouseX < ub) {
				col = i;
			}
			
			if (mouseY >= lb && mouseY < ub && i < 13) {
				row = i;
			}
		}
		model.addTower(currTowerClicked, row, col);
	}
	
	public int getMoney() {
		return this.model.getMoney();
	}
	
	public int getHealth() {
		return this.model.getHealth();
	}
}
