/**
 * Class Road defines general clath to create a path for enemies to follow, and the general
 * game path. Specific road subclasses will define coordinates for what the board will have at
 * any given point, which will inform whether a given point is a portal, enemy road, or
 * a placeable square for a tower.
 * @author David Gonzales, Mario Verdugo, Luke Cernetic, Chris Crabtree
 *
 */
public class Road {
	
	protected int[][] map;
	protected int[][] path;
	protected int[] startingPos;
	
	/**
	 * Getter method to return the map of the road
	 * @return 2D array of the map
	 */
	public int[][] getMap(){
		return map;
	}
	
	/**
	 * Getter to get the path of the road
	 * @return 2D array of the path
	 */
	public int[][] getPath() {
		return path;
	}
	
	/**
	 * Getter to get the starting position array
	 * @return array of the starting positions
	 */
	public int[] getStartingPos(){
		return startingPos;
	}

}
