
public class Road1 extends Road {

	public Road1() {
		map = new int[13][15];
		
		int[] row0 = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0};
		int[] row1 = {0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 0};
		int[] row2 = {0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0};
		int[] row3 = {0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0};
		int[] row4 = {0, 1, 0, 1, 1, 1, 0, 1, 1, 1, 0, 0, 0, 0, 0};
		int[] row5 = {0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 0, 0, 0, 0};
		int[] row6 = {0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 1, 1, 0};
		int[] row7 = {0, 1, 1, 1, 0, 1, 1, 1, 0, 1, 0, 1, 0, 1, 0};
		int[] row8 = {0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 1, 0};
		int[] row9 = {1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 0, 1, 0};
		int[] row10 = {0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0};
		int[] row11 = {0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 0};
		int[] row12 = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};

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
	}
}
