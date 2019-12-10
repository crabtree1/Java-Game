import java.io.Serializable;

public class TDNetworkMessage implements Serializable {
	       private static final long serialVersionUID = 1L;

	       private int row;
	       private int col;
	       private int towerType;

	       public TDNetworkMessage(int row, int col, int towerType) {
	    	   this.row = row;
	    	   this.col = col;
	    	   this.towerType = towerType;
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

	       public int getTower() {
	    	   return this.towerType;
	       }
}
