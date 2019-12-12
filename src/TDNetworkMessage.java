import java.io.Serializable;

public class TDNetworkMessage implements Serializable {
	       private static final long serialVersionUID = 1L;

	       private int row;
	       private int col;
	       private int towerType;
	       private int mapSelection;
	       private boolean isPlaying = false;
	       private boolean isPaused = false;
	       private boolean removeTower = false;
	       private Long seed1;
	       private Long seed2;

	       public TDNetworkMessage(int row, int col, int towerType) {
	    	   this.row = row;
	    	   this.col = col;
	    	   this.towerType = towerType;
	       }
	       
	       public TDNetworkMessage(int mapSelection) {
	    	   this.mapSelection = mapSelection;
	       }
	       
	       public TDNetworkMessage(boolean playing) {
	    	   this.isPlaying = playing;
	       }
	       
	       public TDNetworkMessage(Long seed1, Long seed2) {
	    	   this.seed1 = seed1;
	    	   this.seed2 = seed2;
	       }
	       
	       public TDNetworkMessage() {
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
	       
	       public Long getSeed1() {
	    	   return this.seed1;
	       }
	       
	       public Long getSeed2() {
	    	   return this.seed2;
	       }
	       
	       public boolean isPuased() {
	    	   return this.isPaused;
	       }
	       
	       public boolean isRemoving() {
	    	   return this.removeTower;
	       }

	       public int getTower() {
	    	   return this.towerType;
	       }
	       
	       public int getMap() {
	    	   return this.mapSelection;
	       }
	       
	       public boolean isPlaying() {
	    	   return this.isPlaying;
	       }
	       
	       public void setPaused(boolean isPaused) {
	    	   this.isPaused = isPaused;
	       }
	       
	       public void setRemove(boolean remove) {
	    	   this.removeTower = remove;
	       }
}
