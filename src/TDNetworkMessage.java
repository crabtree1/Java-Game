import java.io.Serializable;

public class TDNetworkMessage implements Serializable {
	       private static final long serialVersionUID = 1L;

	       private int row;
	       private int col;
	       private Tower curTower;

	       public TDNetworkMessage(int row, int col, Tower tower) {
	    	   this.row = row;
	    	   this.col = col;
	    	   this.curTower = tower;
	       }

	       /**
	        * Returns the row field of the  instance.
	        * 
	        * @return an int describing the row to be changed.
	        */
	       public int getRow() {
	    	   return row;
	       }

	       /**
	        * Returns the column of the  instance.
	        * 
	        * @return an int describing the column to be changed.
	        */
	       public int getColumn() {
	    	   return col;
	       }

	       public Tower getTower() {
	    	   return this.curTower;
	       }
}
