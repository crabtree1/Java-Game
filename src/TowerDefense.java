/**
 * This class contains the main for our tower defense game. The game is created in
 * the MVC design style. The game also depends on many subclasses such as types of enemies,
 * types of towers, and types of roads to use. The player will have set money to begin the game
 * and can place towers to defend their portal. Once the user is happy with how their towers
 * are placed, they will start the game. Each enemy they hit will earn them money, and they
 * will continue playing until their health is depleted. Additionally, players will be able to play
 * with another player with each one controlling one half of the board.
 */

import javafx.application.Application;

public class TowerDefense {

	public static void main(String[] args) {
		Application.launch(TowerDefenseView.class, args);
	}
}
