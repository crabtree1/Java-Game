

public class TowerDefenseController {

	private TowerDefenseModel model;
	
	public TowerDefenseController(TowerDefenseModel model) {
		this.model = model;
	}
	
	public void setModel(TowerDefenseModel model) {
		this.model = model;
	}
	
	public Road getRoad() {
		return model.getRoad();
	}
	
	public Tower[][] getTowerMap(){
		return model.getTowerMap();
	}
	
	public void startRound() {
		model.startRound();
	}
	
	public void addTower(Tower currTowerClicked, double mouseX, double mouseY) {
		if((this.getMoney() - currTowerClicked.getCost()) < 0) {
			return;
		}
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
		this.model.spendMoney(currTowerClicked.getCost());
		model.addTower(currTowerClicked, row, col);
	}
	
	public void sellTower(double mouseX, double mouseY) {
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
		if(this.model.towerAtPosition(row, col)) {
			this.model.removeTower(this.model.getTowerAtPost(row, col), row, col);
		}
	}
	
	public void findTowerAtPosition(int row, int col) {
		this.model.towerAtPosition(row, col);
	}
	
	public String getGamePhase() {
		return model.getGamePhase();
	}
	
	public int getMoney() {
		return this.model.getMoney();
	}
	
	public int getHealth() {
		return this.model.getHealth();
	}
	
	public void addAttackMoney() {
		this.model.addAttackMoney();
	}
	
	public void takeHealth() {
		this.model.takeDamage();
	}
}
