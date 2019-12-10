import java.io.Serializable;

/**
 * Tower class that defines the base behavior for all of the tower classes. It contains
 * information for all of their graphic representations, x and y coordinates, cost, and
 * attack powers.
 * @author David Gonzales, Mario Verdugo, Luke Cernetic, Chris Crabtree
 *
 */

public class Tower {
	
	protected String towerPic;
	protected String towerPortrait;
	protected String towerName;
	private int x;
	private int y;
	protected int cost = 1;
	protected int attackPower;
	
	/**
	 * Base constructor for the tower class
	 */
	public Tower() {
		this.x = 0;
		this.y = 0;
		this.attackPower = 1;
	}
	
	/**
	 * Setter method to change the x coordinate for the tower
	 * @param x - value to change the tower's x to
	 */
	public void setX(int x) {
		this.x = x;
	}
	
	/**
	 * Setter method to change the y coordinate for the tower
	 * @param y - value to change the tower's y to
	 */
	public void setY(int y) {
		this.y = y;
	}
	
	/**
	 * Setter method to set both the x and y coordinates for a tower
	 * @param x - value to change x to
	 * @param y - value to change y to
	 */
	public void setCords(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Getter method for the x coordinate for the tower
	 * @return x value for the tower
	 */
	public int getX() {
		return this.x;
	}
	
	/**
	 * Getter method to get the y coordinate for the tower
	 * @return y value for the tower
	 */
	public int getY() {
		return this.y;
	}
	
	/**
	 * Getter method to return the picture for the tower
	 * @return picture for the tower
	 */
	public String getTowerPic() {
		return towerPic;
	}
	
	/**
	 * Getter method to return the portrait for the tower
	 * @return portrait for the tower
	 */
	public String getTowerPortrait() {
		return towerPortrait;
	}
	
	/**
	 * Getter method to return the name for the tower
	 * @return name for the tower
	 */
	public String getTowerName() {
		return towerName;
	}
	
	/**
	 * Getter method to return the cost for the tower
	 * @return cost for the tower
	 */
	public int getCost() {
		return this.cost;
	}
	
	/**
	 * Getter method to return the attack power for the tower
	 * @return attack power for the tower
	 */
	public int getAttackPower() {
		return this.attackPower;
	}
}
