/**
 * Controller class for our tower defense game. The controller has a model it
 * interacts with, and acts as the intermediary that allows the view to be able
 * to access the model.
 * @author David Gonzales, Mario Verdugo, Luke Cernetic, Chris Crabtree
 */

public class TowerDefenseController {

	private TowerDefenseModel model;
	
	/**
	 * Constructor for the controller class
	 * @param model - model to use for the controller
	 */
	public TowerDefenseController(TowerDefenseModel model) {
		this.model = model;
	}
	
	/**
	 * setter method to set the model for the controller to be
	 * whatever the passed parameter is
	 * @param model - tower model that will be the controller's model
	 */
	public void setModel(TowerDefenseModel model) {
		this.model = model;
	}
	
	/**
	 * Getter to return the controller's model
	 * @return the model
	 */
	public TowerDefenseModel getModel() {
		return model;
	}
	
	/**
	 * Setter that sets the model's road
	 * @param currRoad - road object that will be the road's object
	 */
	public void setRoad(Road currRoad) {
		model.setRoad(currRoad);
	}
	
	/**
	 * Getter to return the current road being used by the model
	 * @return the road in the model
	 */
	public Road getRoad() {
		return model.getRoad();
	}
	
	/**
	 * Getter to get the tower map at the current point in the model
	 * @return the 2D array tower map
	 */
	public Tower[][] getTowerMap(){
		return model.getTowerMap();
	}
	
	/**
	 * Method to begin the round in the model
	 */
	public void startRound() {
		model.startRound();
	}
	
	/**
	 * Method to add a tower to the game board. The location for the
	 * tower is dependent on where the user clicks in the gui, and the
	 * proper row and col are determined based on the click. The type of tower
	 * to be placed is also dependednt on what type of tower most recently clicked
	 * by the user
	 * @param currTowerClicked - the tower to be added to the tower map
	 * @param mouseX - x coordinate of where the user clicked in the gui
	 * @param mouseY - y coordinate of where the user clicked in the gui
	 */
	public void addTower(Tower currTowerClicked, double mouseX, double mouseY) {
		// only place a tower if the user can afford it
		if((getMoney() - currTowerClicked.getCost()) < 0) {
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
		// subtract the tower cost from the user's money
		this.model.spendMoney(currTowerClicked.getCost());
		model.addTower(currTowerClicked, row, col);
	}
	
	/**
	 * Method to sell the tower wherever the user clicks, 
	 * as long as there is a tower at that position.
	 * @param mouseX - x coordinate of the user click
	 * @param mouseY - y coordinate of the user click
	 */
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
	
	/**
	 * Method to increase the speed of the game, therefore fastforwarding it
	 */
	public void increaseGameSpeed() {
		model.increaseGameSpeed();
	}
	
	/**
	 * Method to change the pause status of the game
	 */
	public void changePaused() {
		model.changePaused();
	}
	
	/**
	 * Method to see if a tower is at specific coordinates, which are
	 * determined by the paramters
	 * @param row - row value to check for a tower at
	 * @param col - col value to check for a tower at
	 */
	public void findTowerAtPosition(int row, int col) {
		this.model.towerAtPosition(row, col);
	}
	
	/**
	 * Getter to get the current game phase (either place or attack)
	 * @return the game phase
	 */
	public String getGamePhase() {
		return model.getGamePhase();
	}
	
	/**
	 * Getter to get the user's money
	 * @return the user's money
	 */
	public int getMoney() {
		return this.model.getMoney();
	}
	
	/**
	 * Getter to get the user's health
	 * @return user's health
	 */
	public int getHealth() {
		return this.model.getHealth();
	}
	
	/**
	 * Method to give the user money when they have successfully attacked an
	 * enemy
	 */
	public void addAttackMoney() {
		this.model.addAttackMoney();
	}
	
	/**
	 * Method to remove health from the user if an enemy reaches their portal
	 */
	public void takeHealth() {
		this.model.takeDamage();
	}
	
	/**
	 * Method to have all towers attack the enemies
	 */
	public void towerAttack() {
		this.model.towerAttack();
	}
	
	/**
	 * Getter to get the current round of the game
	 * @return the game's round
	 */
	public int getRound() {
		return this.model.getRound();
	}
}
