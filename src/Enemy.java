import java.io.Serializable;

/**
 * Class that defines the base behaviors for all enemies in the game. Enemies
 * have health, x and y coordinates, a boolean to determine if they are alive
 * or not, and strings to keep track of the graphics needed.
 * @author David Gonzales, Mario Verdugo, Luke Cernetic, Chris Crabtree
 *
 */
public class Enemy implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int health;
	private int x;
	private int y;
	private boolean alive;
	
	protected String towerPic;
	protected String towerPortrait;
	protected String towerName;
	
	/**
	 * Default constructor for any enemy class
	 */
	public Enemy() {
		this.health = 100;
		this.x = 0;
		this.y = 0;
		this.alive = true;
	}
	
	/**
	 * Getter method to return the health of a given enemy
	 * @return health of the enemy
	 */
	public int getHealth() {
		return this.health;
	}
	
	/**
	 * Getter method to return the picture for the given enemy
	 * @return picture for the enemy
	 */
	public String getTowerPic() {
		return towerPic;
	}
	
	/**
	 * Getter method to return the portrait used for the given enemy
	 * @return the portrait
	 */
	public String getTowerPortrait() {
		return towerPortrait;
	}
	
	/**
	 * Getter method to return the name of the current enemy
	 * @return the name
	 */
	public String getTowerName() {
		return towerName;
	}
	
	/**
	 * Setter method to set the x coordinate for the current enemy
	 * @param x - int value that the x for the enemy should be changed to
	 */
	public void setX(int x) {
		this.x = x;
	}
	
	/**
	 * Setter method to set the y coordinate for the current enemy
	 * @param y - int value that the y for the enemy should be changed to
	 */
	public void setY(int y) {
		this.y = y;
	}
	
	/**
	 * Setter method to set both the x and y coordinates for the given enemy
	 * @param x - int value that the x will be changed to
	 * @param y - int value that the y will be changed to
	 */
	public void setCords(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Getter to return an enemy's x value
	 * @return the enemy's x
	 */
	public int getX() {
		return this.x;
	}
	
	/**
	 * Getter to return an enemy's y value
	 * @return the enemy's y
	 */
	public int getY() {
		return this.y;
	}
	
	/**
	 * Method to represent an enemy taking damage from a tower. The amount
	 * of health it will lose depends on the attack power of the given tower
	 * @param damageAmount - attack power from the attacking tower
	 */
	public void takeDamage(int damageAmount) {
		this.health -= damageAmount;
	}
	
	/**
	 * Setter method to change the alive boolean
	 * @param life - boolean value to determine if the enemy is alive or dead
	 */
	public void setAlive(boolean life) {
		this.alive = life;
	}
	
	/**
	 * Getter method to return the enemy's alive value
	 * @return the enemy's alive value
	 */
	public boolean getAlive() {
		return this.alive;
	}
}
