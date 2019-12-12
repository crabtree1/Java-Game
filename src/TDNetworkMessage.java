
import java.io.Serializable;

/**
 * This class is a message class used to send data during a networked game
 * @author Chris Crabtree
 *
 */
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

	       /**
	        * Constructor for a message showing a tower placed
	        * @param row An int of the row
	        * @param col An int of the col
	        * @param towerType An int of the tower type
	   	 	*/
	       public TDNetworkMessage(int row, int col, int towerType) {
	    	   this.row = row;
	    	   this.col = col;
	    	   this.towerType = towerType;
	       }
	       
	       /**
	        * Constructor for a message indicating a map selection
	        * @param mapSelection An int of the map selected
	        */
	       public TDNetworkMessage(int mapSelection) {
	    	   this.mapSelection = mapSelection;
	       }
	       
	       /**
	        * Constructor for a message showing if the game is in progress
	        * @param playing A boolean indicating if the game is playing
	   	 	*/
	       public TDNetworkMessage(boolean playing) {
	    	   this.isPlaying = playing;
	       }
	       
	       /**
	        * Constructor for a message holding seeds for enemy generation
	        * @param seed1 A long of the seed 1
	        * @param seed2 A long of the seed 2
	   	 	*/
	       public TDNetworkMessage(Long seed1, Long seed2) {
	    	   this.seed1 = seed1;
	    	   this.seed2 = seed2;
	       }
	       
	       /**
	        * Defualt constructor
	   	 	*/
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
	       
	       /**
	        * This method returns seed1
	        * @return A long seed value
	        */
	       public Long getSeed1() {
	    	   return this.seed1;
	       }
	       
	       /**
	        * This method returns seed2
	        * @return A long seed value
	        */
	       public Long getSeed2() {
	    	   return this.seed2;
	       }
	       
	       /**
	        * This method returns a boolean indicating if the game is paused
	        * @return A boolean indicating if the game is paused
	        */
	       public boolean isPuased() {
	    	   return this.isPaused;
	       }
	       
	       /**
	        * This method indicates if a tower is being removed
	        * @return A boolean indicating if a tower is being removed
	        */
	       public boolean isRemoving() {
	    	   return this.removeTower;
	       }

	       /**
	        * This method returns an int value of the tower selected
	        * @return An int of the selected tower type
	        */
	       public int getTower() {
	    	   return this.towerType;
	       }
	       
	       /**
	        * This method returns and int of the map selection
	        * @return An int of the map selected
	        */
	       public int getMap() {
	    	   return this.mapSelection;
	       }
	        /**
	         * This method returns a boolean indicating if the game is playing
	         * @return A boolean indicating if the game is playing
	         */
	       public boolean isPlaying() {
	    	   return this.isPlaying;
	       }
	       
	       /**
	        * This method sets the boolean indicating if the game is paused
	        * @param isPaused A boolean indicating if the game is paused
	        */
	       public void setPaused(boolean isPaused) {
	    	   this.isPaused = isPaused;
	       }
	       
	       /**
	        * This method sets a boolean indicating if the tower selected should be removed
	        * @param remove A boolean indicating if the tower should be removed
	        */
	       public void setRemove(boolean remove) {
	    	   this.removeTower = remove;
	       }
}
