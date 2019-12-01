import java.util.Observable;
import java.util.Observer;

import javax.swing.GroupLayout.Alignment;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
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
		Text money = new Text(Integer.toString(this.controller.getMoney()));
		money.setFont(Font.font ("Verdana", 23));
		money.setFill(Color.YELLOW);
		Text lives = new Text(Integer.toString(this.controller.getHealth()));
		lives.setFont(Font.font ("Verdana", 23));
		lives.setFill(Color.YELLOW);
		moneyLivesBox.getChildren().addAll(lives, money);
		
		//sell button
		Rectangle sellButton = new Rectangle();
		sellButton.setWidth(60);
		sellButton.setHeight(35);
		Image sellButtonImg = new Image("pictures/sellButton.png");
		sellButton.setFill(new ImagePattern(sellButtonImg));
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
					controller.addTower(currTowerClicked, event.getSceneX(), event.getSceneY());
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
				else {
					Image pic = new Image("/pictures/portal.png");
					temp.setFill(new ImagePattern(pic));
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
