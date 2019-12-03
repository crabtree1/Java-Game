import java.util.ArrayList;
import java.util.Observable;

public class TowerDefenseModel extends Observable{

	private Tower[][] towerMap;
	private ArrayList<Enemy> enemyMap;
	private Road road;
	private int health = 100;
	private int money = 100;
	private String gamePhase;
	private int roundEnemies;
	private int[][] pathToFollow;
	
	public TowerDefenseModel() {
		roundEnemies = 40;
		gamePhase = "place";
		int[][] temp = { {0,13}, {1,13}, {2,13}, {3,13}, {3,12}, {3,11}, {2,11}, {1,11}, {1,10},
		{1,9},{1,8}, {1,7}, {1,6}, {1,5}, {1,4}, {1,3}, {1,2}, {1,1}, {2,1}, {3,1},{4,1},{5,1},
		{6,1}, {7,1},{7,2},{7,3},{6,3},{5,3},{4,3},{4,4},{4,5},{5,5},{6,5},{7,5},{7,6},{7,7},
		{6,7},{5,7},{4,7},{4,8},{4,9},{5,9},{6,9},{7,9},{8,9},{9,9},{9,10},{9,11},{8,11},{7,11},
		{6,11},{6,12},{6,13},{7,13},{8,13},{9,13},{10,13},{11,13},{11,12},{11,11},{11,10},{11,9},
		{11,8},{11,7},{10,7},{9,7},{9,6},{9,5},{9,4},{9,3},{9,2},{9,1},{9,0}};
		
		pathToFollow = temp;
		
		towerMap = new Tower[13][15];
		enemyMap = new ArrayList<Enemy>();
		road = new Road1();
	}
	
	public String getGamePhase() {
		return this.gamePhase;
	}
	
	public void startRound() {
		gamePhase = "attack";
		Thread thread = new Thread(){
			public void run() {
				int i = 0;
				while(i < roundEnemies || enemyMap.size() != 0) {
					if(getHealth() < 0) {
						break;
					}
					int randomEnemy = (int) (Math.random() * 2);
					moveEnemies();
					int createProb = (int) (Math.random() * 4);
					if (createProb < 1 && i < roundEnemies) {
						createEnemy(randomEnemy);
						i += 1;
					}
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				gamePhase = "place";
			}
		};
		thread.start();
	}
	
	
	public void createEnemy(int randomEnemy) {
		if(health < 0) {
			return;
		}
		Enemy enemy = null;
		if (randomEnemy == 1) {
			enemy = new PickelRickEnemy();
		} else {
			enemy = new ToxicRickEnemy();
		}
		
		enemy.setCords(0, 13);
		enemyMap.add(enemy);
	}
	
	public void moveEnemies() {
		for (int i = 0; i < enemyMap.size(); i++) {
			Enemy e = enemyMap.get(i);
			findNext(e);
		}
		
		setChanged();
		notifyObservers(enemyMap);
	}
	
	public void findNext(Enemy e) {
		int[] enemyCoords = {e.getX(), e.getY()};
		for (int i = 0; i < pathToFollow.length - 1; i++) {
			if (pathToFollow[i][0] == enemyCoords[0] && pathToFollow[i][1] == enemyCoords[1]) {
				e.setCords(pathToFollow[i+1][0], pathToFollow[i+1][1]);
				return;
			}
		}
		if(e.getHealth() > 0) {
			this.health -= e.getHealth();
		}
		enemyMap.remove(e);
	}
	
	public Road getRoad() {
		return road;
	}
	
	public Tower[][] getTowerMap() {
		return towerMap;
	}
	
	public void addTower(Tower currTowerClicked, int x, int y) {
		if (currTowerClicked != null && towerMap[x][y] == null && road.getMap()[x][y] == 0 && gamePhase.equals("place")) {
			towerMap[x][y] = currTowerClicked;
			
			int[] returnArray = {x, y};
			setChanged();
			notifyObservers(returnArray);
		}
	}
	
	public boolean towerAtPosition(int x, int y) {
		if(towerMap[x][y] != null) {
			return true;
		}
		return false;
	}
	
	public Tower getTowerAtPost(int x, int y) {
		return towerMap[x][y];
	}
	
	public void removeTower(Tower currTowerClicked, int x, int y) {
		towerMap[x][y] = null;
		this.money += currTowerClicked.getCost()/2;
		
		int[] returnArray = {x, y};
		setChanged();
		notifyObservers(returnArray);
	}
	
	public int getMoney() {
		return this.money;
	}
	
	public void spendMoney(int amount) {
		this.money -= amount;
	}
	
	public int getHealth() {
		return this.health;
	}
	
	public void takeDamage() {
		this.health -= 1;
	}
	
	public void addAttackMoney() {
		this.money += 1;
	}
}
