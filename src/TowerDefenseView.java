import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.GroupLayout.Alignment;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class TowerDefenseView extends Application implements Observer {

	private TowerDefenseModel model;
	private TowerDefenseController controller;
	private Tower currTowerClicked = null;
	private GridPane grid;
	private VBox sideBar;
	private HBox towerBox;
	private Rectangle towerText;
	private Text money;
	private Text lives;
	private boolean isSelling = false;

	
	
	@Override
	public void start(Stage stage) throws Exception {
		model = new TowerDefenseModel();
		controller = new TowerDefenseController(model);
		model.addObserver(this);
		
		stage.setTitle("Rick and Morty Tower Defense");
		
		BorderPane border = new BorderPane();
		
		//Creates side bar
		sideBar = new VBox();
		sideBar.setMinHeight(600);
		sideBar.setMinWidth(295);
		sideBar.setStyle("-fx-border-color: black;\n"
                + "-fx-border-width: 6;\n");
		border.setRight(sideBar);
		
		towerText = new Rectangle();
		towerText.setWidth(295);
		towerText.setHeight(100);
		towerText.setFill(Color.BLACK);
		
		
		//Creates tower panels
		towerBox = new HBox();
		towerBox.setMaxWidth(705);
		
		VBox moneyLivesBox = new VBox();
		moneyLivesBox.setStyle("-fx-background-image: url(\"pictures/moneyLivesBackground.png\")");
		moneyLivesBox.setPadding(new Insets(5, 0, 0, 25));
		moneyLivesBox.setMinHeight(100);
		moneyLivesBox.setMinWidth(105);
		moneyLivesBox.setAlignment(Pos.TOP_CENTER);
		money = new Text(Integer.toString(this.controller.getMoney()));
		money.setFont(Font.font ("Verdana", 23));
		money.setFill(Color.YELLOW);
		lives = new Text(Integer.toString(this.controller.getHealth()));
		lives.setFont(Font.font ("Verdana", 23));
		lives.setFill(Color.YELLOW);
		moneyLivesBox.getChildren().addAll(lives, money);
		
		//sell button
		Rectangle sellButton = new Rectangle();
		sellButton.setWidth(60);
		sellButton.setHeight(35);
		Image sellButtonImg = new Image("pictures/sellButton.png");
		sellButton.setFill(new ImagePattern(sellButtonImg));
		sellButton.setOnMouseClicked((event) -> {
			if(this.isSelling) {
				this.isSelling = false;
			} else {
				this.isSelling = true;
			}
		});
		moneyLivesBox.getChildren().add(sellButton);
		
		VBox rickTowerBox = new VBox();
		rickTowerBox.setMinWidth(100);
		rickTowerBox.setMinHeight(100);
		Rectangle rickPanel = new Rectangle();
		rickPanel.setWidth(100);
		rickPanel.setHeight(100);
		Image rickPanelImg = new Image("/pictures/rickPanel.png");
		rickPanel.setFill(new ImagePattern(rickPanelImg));
		rickTowerBox.getChildren().add(rickPanel);
		rickTowerBox.setOnMouseClicked((event) -> {
			currTowerClicked = new RickTower();
			setPortrait();
		});
		
		VBox mortyTowerBox = new VBox();
		mortyTowerBox.setMinWidth(100);
		mortyTowerBox.setMinHeight(100);
		Rectangle mortyPanel = new Rectangle();
		mortyPanel.setWidth(100);
		mortyPanel.setHeight(100);
		Image mortyPanelImg = new Image("/pictures/mortyPanel.png");
		mortyPanel.setFill(new ImagePattern(mortyPanelImg));
		mortyTowerBox.getChildren().add(mortyPanel);
		mortyTowerBox.setOnMouseClicked((event) -> {
			currTowerClicked = new MortyTower();
			setPortrait();
		});
		
		VBox meeseeksTowerBox = new VBox();
		meeseeksTowerBox.setMinWidth(100);
		meeseeksTowerBox.setMinHeight(100);
		Rectangle meeseeksPanel = new Rectangle();
		meeseeksPanel.setWidth(100);
		meeseeksPanel.setHeight(100);
		Image meeseeksPanelImg = new Image("/pictures/meeseeksPanel.png");
		meeseeksPanel.setFill(new ImagePattern(meeseeksPanelImg));
		meeseeksTowerBox.getChildren().add(meeseeksPanel);
		meeseeksTowerBox.setOnMouseClicked((event) -> {
			currTowerClicked = new MeeseeksTower();
			setPortrait();
		});
		
		VBox jerryTowerBox = new VBox();
		jerryTowerBox.setMinWidth(100);
		jerryTowerBox.setMinHeight(100);
		Rectangle jerryPanel = new Rectangle();
		jerryPanel.setWidth(100);
		jerryPanel.setHeight(100);
		Image jerryPanelImg = new Image("/pictures/jerryPanel.png");
		jerryPanel.setFill(new ImagePattern(jerryPanelImg));
		jerryTowerBox.getChildren().add(jerryPanel);
		jerryTowerBox.setOnMouseClicked((event) -> {
			currTowerClicked = new JerryTower();
			setPortrait();
		});
		
		VBox birdpersonTowerBox = new VBox();
		birdpersonTowerBox.setMinWidth(100);
		birdpersonTowerBox.setMinHeight(100);
		Rectangle birdpersonPanel = new Rectangle();
		birdpersonPanel.setWidth(100);
		birdpersonPanel.setHeight(100);
		Image birdpersonPanelImg = new Image("/pictures/birdpersonPanel.png");
		birdpersonPanel.setFill(new ImagePattern(birdpersonPanelImg));
		birdpersonTowerBox.getChildren().add(birdpersonPanel);
		birdpersonTowerBox.setOnMouseClicked((event) -> {
			currTowerClicked = new BirdPersonTower();
			setPortrait();
		});
		
		VBox squanchyTowerBox = new VBox();
		squanchyTowerBox.setMinWidth(100);
		squanchyTowerBox.setMinHeight(100);
		Rectangle squanchyPanel = new Rectangle();
		squanchyPanel.setWidth(100);
		squanchyPanel.setHeight(100);
		Image squanchyPanelImg = new Image("/pictures/squanchyPanel.png");
		squanchyPanel.setFill(new ImagePattern(squanchyPanelImg));
		squanchyTowerBox.getChildren().add(squanchyPanel);
		squanchyTowerBox.setOnMouseClicked((event) -> {
			currTowerClicked = new SquanchyTower();
			setPortrait();
		});
		
		towerBox.getChildren().addAll(moneyLivesBox, rickTowerBox, mortyTowerBox,
									  meeseeksTowerBox, jerryTowerBox, 
									  birdpersonTowerBox, squanchyTowerBox, towerText);
		
		border.setBottom(towerBox);
		
		
		//Creates map
		grid = new GridPane();
		int[][] currMap = controller.getRoad().getMap();
		for (int i = 0; i < currMap.length; i++) {
			for (int j = 0; j < currMap[i].length; j++) {
				Rectangle temp = new Rectangle();
				temp.setOnMouseClicked((event) -> {
					if(this.isSelling) {
						this.controller.sellTower(event.getSceneX(), event.getSceneY());
						this.isSelling = false;
						Image pic = new Image("/pictures/space.png");
						temp.setFill(new ImagePattern(pic));
					} else {
						controller.addTower(currTowerClicked, event.getSceneX(), event.getSceneY());
					}
				});
				temp.setWidth(47);
				temp.setHeight(47);
				if (currMap[i][j] == 0) {
					Image pic = new Image("/pictures/space.png");
					temp.setFill(new ImagePattern(pic));
				}
				else if (currMap[i][j] == 1){
					Image pic = new Image("/pictures/road.png");
					temp.setFill(new ImagePattern(pic));
				}
				else if (currMap[i][j] == 2){
					Image pic = new Image("/pictures/portal.png");
					temp.setFill(new ImagePattern(pic));
				} else {
					temp.setFill(Paint.valueOf("red"));
					temp.setOnMouseClicked(e -> {
						if (controller.getGamePhase().equals("place")) {
							controller.startRound();
						}

					});
				}
				grid.add(temp, j, i);
			}
		}
		border.setCenter(grid);

		// right bar (width): 295px
		// map (width): 705px
		Scene scene = new Scene(border, 1000, 711);
		stage.setScene(scene);
		stage.show();
		//playGame();
	}
	

	
	
	
	
	public void setPortrait() {
		Rectangle portrait = new Rectangle();
		portrait.setWidth(295);
		portrait.setHeight(600);
		Image currPortrait = new Image(currTowerClicked.getTowerPortrait());
		portrait.setFill(new ImagePattern(currPortrait));
		sideBar.getChildren().clear();
		sideBar.getChildren().add(portrait);
		
		towerBox.getChildren().remove(towerText);
		Image currName = new Image(currTowerClicked.getTowerName());
		towerText.setFill(new ImagePattern(currName));
		towerBox.getChildren().add(towerText);
	}

	
	public Rectangle findNode(int x, int y) {
		for (Node node: grid.getChildren()) {
			if(grid.getRowIndex(node) == x && grid.getColumnIndex(node) == y) {
				Rectangle curr = (Rectangle) node;
				return curr;
			}
		}
		return null;
	}
	
	private void showLost() {
		Alert winAlert = new Alert(AlertType.INFORMATION);
		winAlert.setHeaderText("Message");
		winAlert.setContentText("You lost!");
		winAlert.showAndWait();
	}

	@Override
	public void update(Observable o, Object arg) {
		if (controller.getGamePhase().equals("place")) {
			money.setText(Integer.toString(this.controller.getMoney()));
			int[] coords = (int[]) arg;
			Rectangle curr = findNode(coords[0], coords[1]);
			Image pic = new Image(currTowerClicked.getTowerPic());
	        curr.setFill(new ImagePattern(pic));

		} else {
			if(this.controller.getHealth() <= 0) {
				lives.setText("0");
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						showLost();
					}
				});
			} else {
				lives.setText(Integer.toString(this.controller.getHealth()));
			}
			ArrayList<Enemy> enemies = (ArrayList<Enemy>) arg;
			int[][] currMap = controller.getRoad().getMap();
			for (int i = 0; i < currMap.length; i++) {
				for (int j = 0; j < currMap[i].length; j++) {
					boolean found  = false;
					if (currMap[i][j] == 1) {
						Rectangle curr = findNode(i, j);
						for (int k = 0; k < enemies.size(); k++) {
							Enemy currEnemy = enemies.get(k);
							if (i == currEnemy.getX() && j == currEnemy.getY()) {
								curr.setFill(Paint.valueOf(currEnemy.getTowerPic()));
								found = true;
								break;
							}
						}
						if (!found) {
							Image pic = new Image("/pictures/road.png");
							curr.setFill(new ImagePattern(pic));
						}
					}

				}
			}
			Tower[][] towers = controller.getTowerMap();
			for (int i = 0; i < towers.length; i ++) {
				for (int j = 0; j < towers[i].length; j ++) {
					for (int k = 0; k < enemies.size(); k ++) {
						Enemy currEnemy = enemies.get(k);
						if (towers[i][j] != null) {
							if (towers[i][j] instanceof BirdPersonTower) {
								currEnemy.takeDamage(1);
								System.out.println(currEnemy.getHealth());
							} else if (currEnemy.getX() == i + 1 && currEnemy.getY() == j || //below
								currEnemy.getX() == i - 1 && currEnemy.getY() == j || // above
								currEnemy.getX() == i + 1 && currEnemy.getY() == j + 1 || // lower right diagonal
								currEnemy.getX() == i - 1 && currEnemy.getY() == j - 1 || // upper left diagonal
								currEnemy.getX() == i - 1 && currEnemy.getY() == j + 1 || // upper right diagonal
								currEnemy.getX() == i + 1 && currEnemy.getY() == j - 1 || // lower left diagonal
								currEnemy.getX() == i && currEnemy.getY() == j - 1 || // left
								currEnemy.getX() == i && currEnemy.getY() == j + 1) { // right
								int temp = currEnemy.getHealth();
								//System.out.println(towers[i][j].attackPower);
								currEnemy.takeDamage(towers[i][j].attackPower);
								//System.out.println(currEnemy.getHealth());
								if((currEnemy.getHealth() < temp) && (currEnemy.getHealth() != 0)) {
									this.controller.addAttackMoney();
									money.setText(Integer.toString(this.controller.getMoney()));
								}
							}
						}
					}
				}
			}
		}
	}
}
