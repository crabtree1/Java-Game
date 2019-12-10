/**
 * Class that represents the model for our tower defense game. The model
 * contains a 2D array of the towers, an array of all enemies, the road
 * used for the current stage, keeps track of health, money, game speed, 
 * the current game phase, and the current round. This is where towers
 * attack enemies, enemeis are generated and moved, and towers can be placed.
 * @author David Gonzales, Mario Verdugo, Luke Cernetic, Chris Crabtree
 */

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
	private int round = 1;
	private int probability = 1;
	
	/**
	 * Constructor for the tower defense model
	 */
	public TowerDefenseModel() {
		roundEnemies = 40;
		gamePhase = "place";
		
		towerMap = new Tower[13][15];
		enemyMap = new ArrayList<Enemy>();
	}
	
	/**
	 * Getter to get the current round of the game
	 * @return current game round
	 */
	public int getRound(){
		return this.round;
	}
	
	/**
	 * Setter to set the model's road to currRoad
	 * @param currRoad - Road object that will be the model's road
	 */
	public void setRoad(Road currRoad) {
		road = currRoad;
		pathToFollow = road.getPath();
	}
	
	/**
	 * Getter to get the current game phase
	 * @return the game phase
	 */
	public String getGamePhase() {
		return this.gamePhase;
	}
	
	/**
	 * Method to increase the speed of the game and loop around
	 * back to the original speed
	 */
	public void increaseGameSpeed() {
		if(this.gameSpeed == 40) {
			this.gameSpeed = 120;
		} else {
			this.gameSpeed -= 40;
		}
	}
	
	/**
	 * Method to change the current status of the game being paused
	 */
	public void changePaused() {
		this.paused = !this.paused;
	}
	
	/**
	 * Method to begin the enemy attacks. Once the round starts, users
	 * cannot place new towers, enemies are created and moved. Once all
	 * enemies have been defeated, the game phase swaps back into placing
	 * towers, the round increases, and the number of enemies in the next round increases.
	 */
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
	
	/**
	 * Method to create an enemy. The type of enemy is determined
	 * by a random number generated when this function is called, and
	 * adds this new enemy to the enemy list.
	 * @param randomEnemy - specifies the type of enemy to create
	 */
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
		
		
		enemy.setCords(road.getStartingPos()[0], road.getStartingPos()[1]);
		enemyMap.add(enemy);
	}
	
	/**
	 * Method to move enemies along the road, as long as the user is
	 * alive and the given enemy is alive
	 */
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
	
	/**
	 * Method to find the next position to move an enemy to.
	 * The enemies will continue to find a new next until they
	 * are dead or have reached the end of the board
	 * @param e - current enemy to find the next for
	 */
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
	
	/**
	 * Getter to return the road in the model
	 * @return the road used in the model
	 */
	public Road getRoad() {
		return road;
	}
	
	/**
	 * Method to return the tower map in the model
	 * @return the 2D tower map array
	 */
	public Tower[][] getTowerMap() {
		return towerMap;
	}
	
	/**
	 * Method to add a tower at the given x and y values. Towers can only be placed
	 * if the tower is properly initialized and there are no towers at that point
	 * and it is not part of the road and the game is in the place phase.
	 * @param currTowerClicked - tower object to be placed
	 * @param x - x coordinate to place the tower
	 * @param y - y coordinate to place the tower
	 */
	public void addTower(Tower currTowerClicked, int x, int y) {
		if (currTowerClicked != null && towerMap[x][y] == null && road.getMap()[x][y] == 0 && gamePhase.equals("place")) {
			towerMap[x][y] = currTowerClicked;
			
			int[] returnArray = {x, y};
			setChanged();
			notifyObservers(returnArray);
		}
	}
	
	/**
	 * Method to check if there is a tower at a given point in the
	 * tower map
	 * @param x - x value to check for a tower
	 * @param y - y value to check for a tower
	 * @return true if there is a tower, false otherwise
	 */
	public boolean towerAtPosition(int x, int y) {
		if(towerMap[x][y] != null) {
			return true;
		}
		return false;
	}
	
	/**
	 * Method to return the tower object at a given point
	 * @param x - x value to get the tower from
	 * @param y - y value to get the tower from
	 * @return the tower at the given position
	 */
	public Tower getTowerAtPost(int x, int y) {
		return towerMap[x][y];
	}
	
	/**
	 * Method to remove the current tower object from the tower map,
	 * meaning it has been sold
	 * @param currTowerClicked - tower object to be removed
	 * @param x - x value to remove the tower from
	 * @param y - y value to remove the tower from
	 */
	public void removeTower(Tower currTowerClicked, int x, int y) {
		towerMap[x][y] = null;
		this.money += currTowerClicked.getCost()/2;
		
		int[] returnArray = {x, y};
		setChanged();
		notifyObservers(returnArray);
	}
	
	/**
	 * Getter to get the user's money
	 * @return the money
	 */
	public int getMoney() {
		return this.money;
	}
	
	/**
	 * Method for the user to lose health
	 */
	public void takeDamage() {
		this.health -= 1;
	}
	
	/**
	 * Method to give the user more money once they have succesfully attacked
	 * an enemy
	 */
	public void addAttackMoney() {
		this.money += 1;
	}
	
	/**
	 * Method to subtract spent moeny from the user's total
	 * @param amount - amount of money spent
	 */
	public void spendMoney(int amount) {
		this.money -= amount;
	}
	
	/**
	 * getter method to get the health of the user
	 * @return the user's health
	 */
	public int getHealth() {
		return this.health;
	}
	
	/**
	 * Method to process all of the possible tower attacks. The function loops through
	 * the tower map to find all of the towers, then loops through the enemies to see
	 * if any enemies are touching the towers, and if they are, they are attacked.
	 */
	public void towerAttack() {
		for (int i = 0; i < towerMap.length; i ++) {
			for (int j = 0; j < towerMap[i].length; j ++) {
				for (int k = 0; k < enemyMap.size(); k ++) {
					Enemy currEnemy = enemyMap.get(k);
					if (towerMap[i][j] != null) {
						int temp = currEnemy.getHealth();
						// bird person attacks all people on the board no matter their location
						if (towerMap[i][j] instanceof BirdPersonTower) {
							currEnemy.takeDamage(1);
						} else if (currEnemy.getX() == i + 1 && currEnemy.getY() == j || //below
							currEnemy.getX() == i - 1 && currEnemy.getY() == j || // above
							currEnemy.getX() == i + 1 && currEnemy.getY() == j + 1 || // lower right diagonal
							currEnemy.getX() == i - 1 && currEnemy.getY() == j - 1 || // upper left diagonal
							currEnemy.getX() == i - 1 && currEnemy.getY() == j + 1 || // upper right diagonal
							currEnemy.getX() == i + 1 && currEnemy.getY() == j - 1 || // lower left diagonal
							currEnemy.getX() == i && currEnemy.getY() == j - 1 || // left
							currEnemy.getX() == i && currEnemy.getY() == j + 1) { // right
								// meeseeks may do double damage
								if (towerMap[i][j] instanceof MeeseeksTower) {
									int damageMultiplier = (int) (Math.random() * 100);
									if (damageMultiplier <= 50) {
										currEnemy.takeDamage(towerMap[i][j].attackPower * 2);
									} else {
										currEnemy.takeDamage(towerMap[i][j].attackPower);
									}
								// squanchy may do quadruple damage
								} else if (towerMap[i][j] instanceof SquanchyTower) {
									int damageMultiplier = (int) (Math.random() * 100);
									if (damageMultiplier <= 25) {
										currEnemy.takeDamage(towerMap[i][j].attackPower * 4);
									} else {
										currEnemy.takeDamage(towerMap[i][j].attackPower);
									}
								// jerry does double damage to doofus rick
								} else if (towerMap[i][j] instanceof JerryTower && currEnemy
										instanceof DoofusRickEnemy) {
									currEnemy.takeDamage(towerMap[i][j].attackPower * 2);
								} else {
									currEnemy.takeDamage(towerMap[i][j].attackPower);
								}
						}
						if((currEnemy.getHealth() < temp) && (currEnemy.getHealth() != 0)) {
							addAttackMoney();
						}
						// remove dead enemies
						if (currEnemy.getHealth() <= 0) {
							currEnemy.setAlive(false);
							enemyMap.remove(currEnemy);
						}
					}
				}
			}
		}
	}
	
	/**
	 * Setter to set the user's money
	 * @param amount - amount to set the money to
	 */
	public void setMoney(int amount) {
		this.money = amount;
	}
	
	/**
	 * getter method to get the game's current speed
	 * @return the current game speed
	 */
	public int getGameSpeed() {
		return this.gameSpeed;
	}
	
	/**
	 * Getter method to return the current paused state of the game
	 * @return true if the game is paused, false otherwise
	 */
	public boolean getPaused() {
		return this.paused;
	}
	
	/**
	 * Getter method to return the current enemy array list
	 * @return the enemy array list
	 */
	public ArrayList<Enemy> getEnemies() {
		return this.enemyMap;
	}
	
	/**
	 * Setter to set the health of the user
	 * @param amount - amount to set the health to
	 */
	public void setHealth(int amount) {
		this.health = amount;
	}
	
}
