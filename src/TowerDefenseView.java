import java.util.Observable;
import java.util.Observer;

import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class TowerDefenseView extends Application implements Observer {

	private TowerDefenseModel model;
	private TowerDefenseController controller;
	private Tower currTowerClicked = null;
	private GridPane grid;
	
	
	@Override
	public void start(Stage stage) throws Exception {
		model = new TowerDefenseModel();
		controller = new TowerDefenseController(model);
		model.addObserver(this);
		
		stage.setTitle("Rick and Morty Tower Defense");
		
		BorderPane border = new BorderPane();
		
		
		//Creates tower panels
		HBox towerBox = new HBox();
		towerBox.setMaxWidth(705);
		
		VBox moneyLivesBox = new VBox();
		moneyLivesBox.setStyle("-fx-background-color:#1426E3");
		moneyLivesBox.setMinHeight(100);
		moneyLivesBox.setMinWidth(100);
		Text money = new Text("  $ Money: 0");
		money.setFill(Color.YELLOW);
		Text lives = new Text("  <3 Lives: 100");
		lives.setFill(Color.YELLOW);
		moneyLivesBox.getChildren().addAll(money, lives);
		
		VBox rickTowerBox = new VBox();
		rickTowerBox.setMinWidth(100);
		rickTowerBox.setMinHeight(100);
		rickTowerBox.setStyle("-fx-background-color:#E33914");
		Text rickTowerText = new Text("Rick");
		rickTowerBox.getChildren().add(rickTowerText);
		rickTowerBox.setOnMouseClicked((event) -> {
			currTowerClicked = new RickTower();
		});
		
		VBox mortyTowerBox = new VBox();
		mortyTowerBox.setMinWidth(100);
		mortyTowerBox.setMinHeight(100);
		mortyTowerBox.setStyle("-fx-background-color:#E3DC14");
		Text mortyTowerText = new Text("Morty");
		mortyTowerBox.getChildren().add(mortyTowerText);
		mortyTowerBox.setOnMouseClicked((event) -> {
			currTowerClicked = new MortyTower();
		});
		
		VBox meeseeksTowerBox = new VBox();
		meeseeksTowerBox.setMinWidth(100);
		meeseeksTowerBox.setMinHeight(100);
		meeseeksTowerBox.setStyle("-fx-background-color:#C7E314");
		Text meeseeksTowerText = new Text("Mr. Meeseeks");
		meeseeksTowerBox.getChildren().add(meeseeksTowerText);
		meeseeksTowerBox.setOnMouseClicked((event) -> {
			currTowerClicked = new MeeseeksTower();
		});
		
		VBox jerryTowerBox = new VBox();
		jerryTowerBox.setMinWidth(100);
		jerryTowerBox.setMinHeight(100);
		jerryTowerBox.setStyle("-fx-background-color:#14E33C");
		Text jerryTowerText = new Text("Jerry");
		jerryTowerBox.getChildren().add(jerryTowerText);
		jerryTowerBox.setOnMouseClicked((event) -> {
			currTowerClicked = new JerryTower();
		});
		
		VBox birdpersonTowerBox = new VBox();
		birdpersonTowerBox.setMinWidth(100);
		birdpersonTowerBox.setMinHeight(100);
		birdpersonTowerBox.setStyle("-fx-background-color:#14E3CB");
		Text birdpersonTowerText = new Text("Bird Person");
		birdpersonTowerBox.getChildren().add(birdpersonTowerText);
		birdpersonTowerBox.setOnMouseClicked((event) -> {
			currTowerClicked = new BirdPersonTower();
		});
		
		VBox squanchyTowerBox = new VBox();
		squanchyTowerBox.setMinWidth(105);
		squanchyTowerBox.setMinHeight(100);
		squanchyTowerBox.setStyle("-fx-background-color:#5D14E3");
		Text squanchyTowerText = new Text("Squanchy");
		squanchyTowerBox.getChildren().add(squanchyTowerText);
		squanchyTowerBox.setOnMouseClicked((event) -> {
			currTowerClicked = new SquanchyTower();
		});
		
		towerBox.getChildren().addAll(moneyLivesBox, rickTowerBox, mortyTowerBox,
									  meeseeksTowerBox, jerryTowerBox, 
									  birdpersonTowerBox, squanchyTowerBox);
		
		border.setBottom(towerBox);
		
		//Creates map
		grid = new GridPane();
		int[][] currMap = controller.getRoad().getMap();
		for (int i = 0; i < currMap.length; i++) {
			for (int j = 0; j < currMap[i].length; j++) {
				Rectangle temp = new Rectangle();
				temp.setOnMouseClicked((event) -> {
					controller.addTower(currTowerClicked, event.getSceneX(), event.getSceneY());
				});
				temp.setWidth(47);
				temp.setHeight(47);
				if (currMap[i][j] == 0) {
					temp.setFill(Color.YELLOWGREEN);
				}
				else {
					Image pic = new Image("/pictures/road.png");
					temp.setFill(new ImagePattern(pic));
				}
				grid.add(temp, j, i);
			}
		}
		border.setCenter(grid);


		Scene scene = new Scene(border, 705, 700);
		stage.setScene(scene);
		stage.show();
		
	}


	@Override
	public void update(Observable o, Object arg) {
		int[] coords = (int[]) arg;
		for (Node node: grid.getChildren()) {
			if(grid.getRowIndex(node) == coords[0] && grid.getColumnIndex(node) == coords[1]) {
				Image pic = new Image(currTowerClicked.getTowerPic());
	
	            Rectangle curr = (Rectangle) node;
				curr.setFill(new ImagePattern(pic));
	            break;
			}
		}
	}

}
