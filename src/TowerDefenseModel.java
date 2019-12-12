/**
 * Class that represents the model for our tower defense game. The model
 * contains a 2D array of the towers, an array of all enemies, the road
 * used for the current stage, keeps track of health, money, game speed, 
 * the current game phase, and the current round. This is where towers
 * attack enemies, enemeis are generated and moved, and towers can be placed.
 * @author David Gonzales, Mario Verdugo, Luke Cernetic, Chris Crabtree
 */

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Random;

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
	private long seed1;
	private long seed2;
	private boolean isClient = false;
	private boolean isNetworked = false;
	
	private ObjectOutputStream oos;
	
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
				while(i < roundEnemies || enemyMap.size() != 0 || health <= 0) {
					if(!isClient) {
						Random randSeed = new Random();
						seed1 = randSeed.nextLong();
						seed2 = randSeed.nextLong();
						if(isNetworked) {
							try {
								oos.writeObject(new TDNetworkMessage(getSeed1(), getSeed2()));
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
					}
					Random rand = new Random(seed1);
					Random rand1 = new Random(seed2);
					if(getHealth() < 0) {
						enemyMap = new ArrayList<Enemy>();
						break;
					}
					int speedToSleep = 40;
					if (!paused) {
						moveEnemies();
						int createProb = (int) (rand.nextDouble() * 4);
						if (createProb < probability && i < roundEnemies) {
							int randomEnemy = ((int) (rand1.nextDouble() * 5)) + 1;
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
		//int randomEnemy = 1;
		if (randomEnemy == 1) {
			enemy = new PickelRickEnemy();
		} else if  (randomEnemy == 2){
			enemy = new ToxicRickEnemy();
		} else if (randomEnemy == 3) {
			enemy  = new DoofusRickEnemy();
		} else if (randomEnemy == 4) {
			enemy = new EvilRickEnemy();
		} else if(randomEnemy == 5){
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
			/*
			int i = 0;
			while (i < enemyMap.size()) {
				System.out.println("i am in while loop");
				Enemy e = enemyMap.get(i);
				System.out.println(e);
				if (e != null) {
					boolean didRemove = false;
					if (e.getAlive()) {
						didRemove = findNext(e);
					}
					
					if (!didRemove) {
						i+=1;
					}
				}
			}
			*/
			ArrayList<Enemy> toRemove = new ArrayList<Enemy>();
			for (int i = 0; i < enemyMap.size(); i++) {
				Enemy e = enemyMap.get(i);
				boolean remove = false;
				if (e.getAlive()) {
					remove = findNext(e);
					
				}
				
				if (remove) {
					toRemove.add(e);
				}
			}
			for (int i = 0; i < toRemove.size(); i++) {
				enemyMap.remove(toRemove.get(i));
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
	 * @return true if a value was removed false otherwise.
	 */
	public boolean findNext(Enemy e) {
		int[] enemyCoords = {e.getX(), e.getY()};
		for (int i = 0; i < pathToFollow.length - 1; i++) {
			if (pathToFollow[i][0] == enemyCoords[0] && pathToFollow[i][1] == enemyCoords[1]) {
				e.setCords(pathToFollow[i+1][0], pathToFollow[i+1][1]);
				return false;
			} 
		}
		if(e.getHealth() > 0) {
			this.health -= e.getHealth();
		}
		//enemyMap.remove(e);
		return true;
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
			currTowerClicked.setCords(y, x);
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
	 * This method removes towers from the other game in a networked game
	 * @param x An int of the x value of the tower
	 * @param y An int of the y value of the tower
	 */
	public void removeTowerNetwork(int x, int y) {
		towerMap[x][y] = null;
		//this.money += currTowerClicked.getCost()/2;
		
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
		boolean hasEnemy;
		ArrayList<Enemy> toRemove = new ArrayList<Enemy>();
		//Tower[][] towers = controller.getTowerMap();
		for (int i = 0; i < towerMap.length; i ++) {
			for (int j = 0; j < towerMap[i].length; j ++) {
				int enemiesHit = 0;
				for (int k = 0; k < enemyMap.size(); k ++) {
					Enemy currEnemy = enemyMap.get(k);
					if (towerMap[i][j] != null) {
						int temp = currEnemy.getHealth();
						hasEnemy = false;
						if (towerMap[i][j] instanceof BirdPersonTower) {
							currEnemy.takeDamage(1);
							towerMap[i][j].addEnemy(currEnemy);
						} 
						//below
						else if (currEnemy.getX() == i + 1 && currEnemy.getY() == j) {
							towerMap[i][j].addEnemy(currEnemy);
							hasEnemy = true;
						}
						//above
						else if (currEnemy.getX() == i - 1 && currEnemy.getY() == j) {
							towerMap[i][j].addEnemy(currEnemy);
							hasEnemy = true;
						}
						// lower right diagonal
						else if(currEnemy.getX() == i + 1 && currEnemy.getY() == j + 1) {
							towerMap[i][j].addEnemy(currEnemy);
							hasEnemy = true;
						}
						// upper left diagonal
						else if(currEnemy.getX() == i - 1 && currEnemy.getY() == j - 1) {
							towerMap[i][j].addEnemy(currEnemy);
							hasEnemy = true;
						}
						// upper right diagonal
						else if(currEnemy.getX() == i - 1 && currEnemy.getY() == j + 1) {
							towerMap[i][j].addEnemy(currEnemy);
							hasEnemy = true;
						}
						// lower left diagonal
						else if(currEnemy.getX() == i + 1 && currEnemy.getY() == j - 1) {
							towerMap[i][j].addEnemy(currEnemy);
							hasEnemy = true;
						}
						// left
						else if(currEnemy.getX() == i && currEnemy.getY() == j - 1) {
							towerMap[i][j].addEnemy(currEnemy);
							hasEnemy = true;
						}
						// right
						else if(currEnemy.getX() == i && currEnemy.getY() == j + 1) {
							towerMap[i][j].addEnemy(currEnemy);
							hasEnemy = true;
						}
							
						if (hasEnemy) {
							enemiesHit ++;
								
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
								currEnemy.takeDamage(100);
							} else {
								currEnemy.takeDamage(towerMap[i][j].attackPower);
							}
						}
						if (currEnemy.getHealth() <= 0) {
							addAttackMoney();
							currEnemy.setAlive(false);
							toRemove.add(currEnemy);
						}
					}
					// if it is not a rick tower and the tower has attacked the farthest
					// enemy ahead, break from the enemy loop
					if (!(towerMap[i][j] instanceof RickTower) && enemiesHit == 1) {
						break;
					}
				}
			}
		}
		for (int i = 0; i < toRemove.size(); i++) {
			enemyMap.remove(toRemove.get(i));
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
	
	/**
	 * This method sets a boolean indicating if the game is a client
	 * @param isClient A boolean indicating if the game is a client
	 */
	public void setClient(boolean isClient) {
		this.isClient = isClient;
	}
	
	/**
	 * This method returns a boolean indicating if the game is a client
	 * @return A boolean indicating if the game is a client
	 */
	public boolean isClient() {
		return isClient;
	}
	
	/**
	 * This method sets seed1 for the enemy generation
	 * @param seed1 A long used to set the seed of the generation
	 */
	public void setSeed1(Long seed1) {
		this.seed1 = seed1;
	}
	
	/**
	 * This method sets seed2 for the enemy generation
	 * @param seed2 A long used to set the seed of the generation
	 */
	public void setSeed2(Long seed2) {
		this.seed2 = seed2;
	}
	
	/**
	 * This method gets the seed1 used for seeding the enemy generator
	 * @return A long of the seed
	 */
	public Long getSeed1() {
		return this.seed1;
	}
	
	/**
	 * This method gets the seed2 used for seeding the enemy generator
	 * @return A long of the seed
	 */
	public Long getSeed2() {
		return this.seed2;
	}
	
	/**
	 * This method sets the ObjectOutputStream used to send the seeds to the client
	 * @param oos An ObjectOutputStream object
	 */
	public void setOOS(ObjectOutputStream oos) {
		this.oos = oos;
	}
	
	/**
	 * This method sets a boolean indicating if the game is networked
	 * @param isNetworked A boolean indicating if the game is networked
	 */
	public void setNetworked(boolean isNetworked) {
		this.isNetworked = isNetworked;
	}
}
