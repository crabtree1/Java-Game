import java.util.ArrayList;
import java.util.Observable;

public class TowerDefenseModel extends Observable{

	private Tower[][] towerMap;
	private ArrayList<Enemy> enemyMap;
	private Road road;
	private int health = 100;
	private int money = 100;
	private int gameSpeed = 120;
	private boolean paused = false;
	private String gamePhase;
	private int roundEnemies;
	private int[][] pathToFollow;
	private Integer round = 1;
	private int probability = 1;
	
	public TowerDefenseModel() {
		roundEnemies = 40;
		gamePhase = "place";
		
		towerMap = new Tower[13][15];
		enemyMap = new ArrayList<Enemy>();
	}
	
	public Integer getRound(){
		return this.round;
	}
	
	public void setRoad(Road currRoad) {
		road = currRoad;
		pathToFollow = road.getPath();
	}
	
	public String getGamePhase() {
		return this.gamePhase;
	}
	
	public void increaseGameSpeed() {
		if(this.gameSpeed == 40) {
			this.gameSpeed = 120;
		} else {
			this.gameSpeed -= 40;
		}
	}
	
	public void changePaused() {
		this.paused = !this.paused;
	}
	
	public void startRound() {
		gamePhase = "attack";
		Thread thread = new Thread(){
			public void run() {
				int i = 0;
				while(i < roundEnemies || enemyMap.size() != 0) {
					if(getHealth() < 0) {
						enemyMap = new ArrayList<Enemy>();
						break;
					}
					int speedToSleep = 40;
					if (!paused) {
						moveEnemies();
						int createProb = (int) (Math.random() * 4);
						if (createProb < probability && i < roundEnemies) {
							int randomEnemy = ((int) (Math.random() * 5)) + 1;
							createEnemy(randomEnemy);
							i += 1;
						}
						speedToSleep = gameSpeed;
					}
					
					try {
						Thread.sleep(speedToSleep);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				gamePhase = "place";
				round += 1;
				roundEnemies += 10;
				probability += 1;
				setChanged();
				notifyObservers(null);
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
		} else if  (randomEnemy == 2){
			enemy = new ToxicRickEnemy();
		} else if (randomEnemy == 3) {
			enemy  = new DoofusRickEnemy();
		} else if (randomEnemy == 4) {
			enemy = new EvilRickEnemy();
		} else {
			enemy = new GuardRickEnemy();
		}
		
		
		enemy.setCoords(road.getStartingPos()[0], road.getStartingPos()[1]);
		enemyMap.add(enemy);
	}
	
	public void moveEnemies() {
		if (health > 0) {
			for (int i = 0; i < enemyMap.size(); i++) {
				Enemy e = enemyMap.get(i);
				if (e.getAlive()) {
					findNext(e);
				}
			}
			
			setChanged();
			notifyObservers(enemyMap);
		}
	}
	
	public void findNext(Enemy e) {
		int[] enemyCoords = {e.getX(), e.getY()};
		for (int i = 0; i < pathToFollow.length - 1; i++) {
			if (pathToFollow[i][0] == enemyCoords[0] && pathToFollow[i][1] == enemyCoords[1]) {
				e.setCoords(pathToFollow[i+1][0], pathToFollow[i+1][1]);
				return;
			}
		}
		if(e.getHealth() > 0) {
			this.health -= e.getHealth();
		}
		enemyMap.remove(e);
	}
	
	/*
	public void printPath() {
		for (int i = 0; i < pathToFollow.length; i++) {
			for (int j = 0; j < pathToFollow[i].length; j++) {
				System.out.print(pathToFollow[i][j] + ",");
			}
			System.out.println();
		}
	}*/
	
	public Road getRoad() {
		return road;
	}
	
	public Tower[][] getTowerMap() {
		return towerMap;
	}
	
	public void addTower(Tower currTowerClicked, int x, int y) {
		if (currTowerClicked != null && towerMap[x][y] == null && road.getMap()[x][y] == 0 && gamePhase.equals("place")) {
			towerMap[x][y] = currTowerClicked;
			currTowerClicked.setCords(y, x);
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
	
	public void takeDamage() {
		this.health -= 1;
	}
	
	public void addAttackMoney() {
		this.money += 1;
	}
	
	public void spendMoney(int amount) {
		this.money -= amount;
	}
	
	public int getHealth() {
		return this.health;
	}
	
	public void towerAttack() {
		boolean hasEnemy;
		//Tower[][] towers = controller.getTowerMap();
		for (int i = 0; i < towerMap.length; i ++) {
			for (int j = 0; j < towerMap[i].length; j ++) {
				for (int k = 0; k < enemyMap.size(); k ++) {
					Enemy currEnemy = enemyMap.get(k);
					if (towerMap[i][j] != null) {
						int temp = currEnemy.getHealth();
						hasEnemy = false;
						if (towerMap[i][j] instanceof BirdPersonTower) {
							currEnemy.takeDamage(1);
							//System.out.println(currEnemy.getHealth());
							
						} 
						
						//below
						else if (currEnemy.getX() == i + 1 && currEnemy.getY() == j) {
							towerMap[i][j].addEnemy(i + 1, j);
							hasEnemy = true;
						}
						//above
						else if (currEnemy.getX() == i - 1 && currEnemy.getY() == j) {
							towerMap[i][j].addEnemy(i - 1, j);
							hasEnemy = true;
						}
						// lower right diagonal
						else if(currEnemy.getX() == i + 1 && currEnemy.getY() == j + 1) {
							towerMap[i][j].addEnemy(i + 1, j + 1);
							hasEnemy = true;
						}
						// upper left diagonal
						else if(currEnemy.getX() == i - 1 && currEnemy.getY() == j - 1) {
							towerMap[i][j].addEnemy(i - 1, j - 1);
							hasEnemy = true;
						}
						// upper right diagonal
						else if(currEnemy.getX() == i - 1 && currEnemy.getY() == j + 1) {
							towerMap[i][j].addEnemy(i - 1, j + 1);
							hasEnemy = true;
						}
						// lower left diagonal
						else if(currEnemy.getX() == i + 1 && currEnemy.getY() == j - 1) {
							towerMap[i][j].addEnemy(i + 1, j - 1);
							hasEnemy = true;
						}
						// left
						else if(currEnemy.getX() == i && currEnemy.getY() == j - 1) {
							towerMap[i][j].addEnemy(i, j - 1);
							hasEnemy = true;
						}
						// right
						else if(currEnemy.getX() == i && currEnemy.getY() == j + 1) {
							towerMap[i][j].addEnemy(i, j + 1);
							hasEnemy = true;
						}
							
						if (hasEnemy) {
							//System.out.println(towers[i][j].attackPower);
								
							if (towerMap[i][j] instanceof MeeseeksTower) {
								int damageMultiplier = (int) (Math.random() * 100);
								if (damageMultiplier <= 50) {
									currEnemy.takeDamage(towerMap[i][j].attackPower * 2);
								} else {
									currEnemy.takeDamage(towerMap[i][j].attackPower);
								}
							} else if (towerMap[i][j] instanceof SquanchyTower) {
								int damageMultiplier = (int) (Math.random() * 100);
								if (damageMultiplier <= 25) {
									currEnemy.takeDamage(towerMap[i][j].attackPower * 4);
								} else {
									currEnemy.takeDamage(towerMap[i][j].attackPower);
								}
							} else if (towerMap[i][j] instanceof JerryTower && currEnemy
									instanceof DoofusRickEnemy) {
								currEnemy.takeDamage(towerMap[i][j].attackPower * 2);
							} else {
								currEnemy.takeDamage(towerMap[i][j].attackPower);
								//System.out.println(currEnemy.getHealth());
							}
						}
						if((currEnemy.getHealth() < temp) && (currEnemy.getHealth() != 0)) {
							addAttackMoney();
						}
						if (currEnemy.getHealth() <= 0) {
							currEnemy.setAlive(false);
							enemyMap.remove(currEnemy);
						}
						System.out.println(i + ", " + j + ": " + towerMap[i][j].getEnemiesToAttack());
					}
				}
			}
		}
	}
	
	// getters/setters to help with testing
	public void setMoney(int amount) {
		this.money = amount;
	}
	
	public int getGameSpeed() {
		return this.gameSpeed;
	}
	
	public boolean getPaused() {
		return this.paused;
	}
	
	public ArrayList<Enemy> getEnemies() {
		return this.enemyMap;
	}
	
	public void setHealth(int amount) {
		this.health = amount;
	}
	
}
