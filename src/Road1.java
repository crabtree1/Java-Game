/**
 * Road1 is a subclass of the Road class. This will specify a particular layout
 * for one of the game stages. 2s represent enemy portals, 1s are the path for
 * the enemies to follow, 0s are placeable squares for towers, and 3, 4, and
 * 5 represent squares for the start, pause and fast forward buttons
 * @author David Gonzales, Mario Verdugo, Luke Cernetic, Chris Crabtree
 *
 */
public class Road1 extends Road {

	/**
	 * Constructor for the Road1 class
	 */
	public Road1() {
		map = new int[13][15];
		
		int[] row0 = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0};
		int[] row1 = {0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 0};
		int[] row2 = {0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0};
		int[] row3 = {0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0};
		int[] row4 = {0, 1, 0, 1, 1, 1, 0, 1, 1, 1, 0, 0, 0, 0, 0};
		int[] row5 = {0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 0, 0, 0, 0};
		int[] row6 = {0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 1, 1, 0};
		int[] row7 = {0, 1, 1, 1, 0, 1, 1, 1, 0, 1, 0, 1, 0, 1, 0};
		int[] row8 = {0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 1, 0};
		int[] row9 = {2, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 0, 1, 0};
		int[] row10 = {0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0};
		int[] row11 = {0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 0};
		int[] row12 = {4, 3, 5, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};

		map[0] = row0;
		map[1] = row1;
		map[2] = row2;
		map[3] = row3;
		map[4] = row4;
		map[5] = row5;
		map[6] = row6;
		map[7] = row7;
		map[8] = row8;
		map[9] = row9;
		map[10] = row10;
		map[11] = row11;
		map[12] = row12;

		int[][] temp = { {0,13}, {1,13}, {2,13}, {3,13}, {3,12}, {3,11}, {2,11}, {1,11}, {1,10},
				{1,9},{1,8}, {1,7}, {1,6}, {1,5}, {1,4}, {1,3}, {1,2}, {1,1}, {2,1}, {3,1},{4,1},{5,1},
				{6,1}, {7,1},{7,2},{7,3},{6,3},{5,3},{4,3},{4,4},{4,5},{5,5},{6,5},{7,5},{7,6},{7,7},
				{6,7},{5,7},{4,7},{4,8},{4,9},{5,9},{6,9},{7,9},{8,9},{9,9},{9,10},{9,11},{8,11},{7,11},
				{6,11},{6,12},{6,13},{7,13},{8,13},{9,13},{10,13},{11,13},{11,12},{11,11},{11,10},{11,9},
				{11,8},{11,7},{10,7},{9,7},{9,6},{9,5},{9,4},{9,3},{9,2},{9,1},{9,0}};
		
		path = temp;
		
		int[] temp2 = {0,13};
		super.startingPos = temp2;
	}
	
	/**
	 * Getter to return the int value for a given point in the
	 * road
	 * @param row - row in the map to get the value from
	 * @param col - col in the map to get the value from
	 * @return the int value at row, col
	 */
	public int getValAtPos(int row, int col) {
		return map[row][col];
	}
}
