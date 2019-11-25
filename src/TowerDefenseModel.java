import java.util.Observable;

public class TowerDefenseModel extends Observable{

	private Tower[][] towerMap;
	private Road road;
	
	public TowerDefenseModel() {
		towerMap = new Tower[13][15];
		road = new Road1();
	}
	
	public Road getRoad() {
		return road;
	}
	
	public Tower[][] getTowerMap() {
		return towerMap;
	}
	
	public void addTower(Tower currTowerClicked, int x, int y) {
		if (currTowerClicked != null && towerMap[x][y] == null && road.getMap()[x][y] == 0) {
			towerMap[x][y] = currTowerClicked;
			
			int[] returnArray = {x, y};
			setChanged();
			notifyObservers(returnArray);
		}
	}
}
