import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.Optional;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
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
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class TowerDefenseView extends Application implements Observer {
	

	private TowerDefenseModel model;
	private TowerDefenseController controller;
	private BorderPane border;
	private Tower currTowerClicked = null;
	private GridPane grid;
	private VBox sideBar;
	private HBox towerBox;
	private Rectangle towerText;
	private Text money;
	private Text lives;
	private boolean isSelling = false;
	private Stage stage;
	private Text roundLabel;
	private boolean isMultiplayer = false;
	TwoPlayerDialogBox dialogBox;
	
	
	
	@Override
	public void start(Stage stage) throws Exception {
		this.stage = stage;
		model = new TowerDefenseModel();
		controller = new TowerDefenseController(model);
		model.addObserver(this);
		
		stage.setTitle("Rick and Morty Tower Defense");
		
		border = new BorderPane();
		Rectangle mainMenu = new Rectangle();
		mainMenu.setWidth(1000);
		mainMenu.setHeight(711);
		mainMenu.setOnMouseClicked((event) -> {
			if (event.getX() > 45 && event.getX() < 380 && event.getY() > 350 && event.getY() < 411) {
				isMultiplayer = false;
				Image mapSelection = new Image("pictures/mapSelection.png");
				mainMenu.setFill(new ImagePattern(mapSelection));
				mainMenu.setOnMouseClicked((event2) -> {
					if (event2.getX() > 75 && event2.getX() < 453 && event2.getY() > 100 && event2.getY() < 611) {
						controller.setRoad(new Road1());
					}
					else if (event2.getX() > 546 && event2.getX() < 924 && event2.getY() > 100 && event2.getY() < 611) {
						controller.setRoad(new Road2());
					}
					startGame();
				});
			}
		
			else if (event.getX() > 40 && event.getX() < 336 && event.getY() > 455 && event.getY() < 520 ) {
				dialogBox = new TwoPlayerDialogBox();
				if(!dialogBox.createType()) {
					Image mapSelection = new Image("pictures/mapSelection.png");
					mainMenu.setFill(new ImagePattern(mapSelection));
					mainMenu.setOnMouseClicked((event2) -> {
						if (event2.getX() > 75 && event2.getX() < 453 && event2.getY() > 100 && event2.getY() < 611) {
							controller.setRoad(new Road1());
							controller.sendMap(new TDNetworkMessage(1));
						}
						else if (event2.getX() > 546 && event2.getX() < 924 && event2.getY() > 100 && event2.getY() < 611) {
							controller.setRoad(new Road2());
							controller.sendMap(new TDNetworkMessage(2));
						}
						startGame();
					});
				}
				setupNetwork();
				isMultiplayer = true;
				controller.setMulitplayer(isMultiplayer);
				//controller.setRoad(new Road1());
				if(dialogBox.createType()) {
					startGame();
				}
			}
		});
		Image titleScreen = new Image("pictures/titleScreen.png");
		mainMenu.setFill(new ImagePattern(titleScreen));
		border.setCenter(mainMenu);
		
		

		// right bar (width): 295px
		// map (width): 705px
		Scene scene = new Scene(border, 1000, 711);
		stage.setScene(scene);
		stage.show();
		//playGame();
		
		//AudioInputStream aio = AudioSystem.getAudioInputStream(new File("intro.mp3"));
		//Clip clip = AudioSystem.getClip();
		//clip.start();
	}
	
	public void startGame() {
		//Creates side bar
		sideBar = new VBox();
		sideBar.setMinHeight(600);
		sideBar.setMinWidth(295);
		Rectangle defaultPanel = new Rectangle();
		defaultPanel.setWidth(295);
		defaultPanel.setHeight(600);
		Image defaultPanelPic = new Image("pictures/defaultPanel.png");
		defaultPanel.setFill(new ImagePattern(defaultPanelPic));
		sideBar.getChildren().add(defaultPanel);
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
				sellButton.setFill(new ImagePattern(new Image("pictures/sellButtonPressed.png")));
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
			controller.setTowerType(4);
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
			controller.setTowerType(3);
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
			controller.setTowerType(2);
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
			controller.setTowerType(1);
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
			controller.setTowerType(0);
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
			controller.setTowerType(5);
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
						if (currTowerClicked != null) {
							controller.addTower(currTowerClicked, event.getSceneX(), event.getSceneY());
						}
						
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
				} else if (currMap[i][j] == 3) {
					Image pause = new Image("pictures/pauseButton.png");
					temp.setFill(new ImagePattern(pause));
					temp.setOnMouseClicked(e -> {
						if (controller.getGamePhase().equals("attack")) {
							if(isMultiplayer) {
								controller.sendPause();
								controller.changePaused();
							} else {
								controller.changePaused();
							}
						}
					});
				} else if (currMap[i][j] == 4){
					Image play = new Image("pictures/playButton.png");
					temp.setFill(new ImagePattern(play));
					temp.setOnMouseClicked(e -> {
						if (controller.getGamePhase().equals("place")) {
							if(isMultiplayer) {
								if(!dialogBox.createType()) {
									controller.sendPlay();
									controller.startRound();
									//controller.sendEnimies();
								}
							} else {
								controller.startRound();
							}
						}
					});
				} else {
					Image fastForward = new Image("pictures/fastForwardButton.png");
					temp.setFill(new ImagePattern(fastForward));
					temp.setOnMouseClicked(e -> {
						if (controller.getGamePhase().equals("attack")) {
							controller.increaseGameSpeed();
						}
					});
				}
				grid.add(temp, j, i);
			}
		}
		border.setCenter(grid);

		roundLabel = new Text("Round 1");
		HBox hbox = new HBox();
		hbox.setAlignment(Pos.CENTER);
		hbox.getChildren().add(roundLabel);
		border.setTop(hbox);
	}
	
	public void setupNetwork() {
		Socket socket = null;
		if(!dialogBox.createType()) {
			try {
				ServerSocket server = new ServerSocket(this.dialogBox.getPort());
				socket = server.accept();
				server.close();
				System.out.println("Server");
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			try {
				socket = new Socket(this.dialogBox.getAddress(), this.dialogBox.getPort());
				controller.setTurn(false);
				controller.setIsClient(true);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		this.controller.initStreams(socket);
		if(dialogBox.createType()) {
			controller.listenForMap();
		}
		controller.startListening();
		if(dialogBox.createType()) {
			//controller.listenForPlay();
			//controller.startListening();
		}
		
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
		Alert lossAlert = new Alert(AlertType.INFORMATION);
		lossAlert.setHeaderText("Message");
		lossAlert.setContentText("You lost! New Game?");
		ButtonType newGame = new ButtonType("NewGame");
		lossAlert.getButtonTypes().add(newGame);
		Optional<ButtonType> option = lossAlert.showAndWait();
		
		if(option.get() == newGame) {
			try {
				start(stage);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}
	
	private void showWin() {
		Alert winAlert = new Alert(AlertType.INFORMATION);
		winAlert.setHeaderText("Winner!");
		winAlert.setContentText("You Win! New Game?");
		ButtonType newGame = new ButtonType("NewGame");
		winAlert.getButtonTypes().add(newGame);
		Optional<ButtonType> option = winAlert.showAndWait();
		
		if(option.get() == newGame) {
			try {
				start(stage);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}

	@Override
	public void update(Observable o, Object arg) {
		if (controller.getGamePhase().equals("place")) {
			if (arg == null) {
				if (controller.getRound() == 3) {
					Platform.runLater(new Runnable() {
						@Override
						public void run() {
							showWin();
						}
					});
					return;
				}
				String string = "Round " + Integer.toString(controller.getRound());
				roundLabel.setText(string);
			}else {
				money.setText(Integer.toString(this.controller.getMoney()));
				Image pic;
				int[] coords = (int[]) arg;
				Rectangle curr = findNode(coords[0], coords[1]);
				if(!controller.getTurn()) {
					pic = controller.getCurTower();
				} else {
					pic = new Image(currTowerClicked.getTowerPic());
				}
		        curr.setFill(new ImagePattern(pic));
				//pic = new Image(currTowerClicked.getTowerPic());
		       // curr.setFill(new ImagePattern(pic));
			}
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
			this.controller.towerAttack();
			for (int i = 0; i < currMap.length; i++) {
				for (int j = 0; j < currMap[i].length; j++) {
					boolean found  = false;
					if (currMap[i][j] == 1) {
						Rectangle curr = findNode(i, j);
						for (int k = 0; k < enemies.size(); k++) {
							Enemy currEnemy = enemies.get(k);
							if (i == currEnemy.getX() && j == currEnemy.getY() && currEnemy.getAlive()) {
								Image enemyPic = new Image(currEnemy.getTowerPic());
								curr.setFill(new ImagePattern(enemyPic));
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
			money.setText(Integer.toString(this.controller.getMoney()));

			/*
			Tower[][] towers = controller.getTowerMap();
			for (int i = 0; i < towers.length; i ++) {
				for (int j = 0; j < towers[i].length; j ++) {
					for (int k = 0; k < enemies.size(); k ++) {
						Enemy currEnemy = enemies.get(k);
						if (towers[i][j] != null) {
							int temp = currEnemy.getHealth();
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
									//System.out.println(towers[i][j].attackPower);
									currEnemy.takeDamage(towers[i][j].attackPower);
									System.out.println(currEnemy.getHealth());
							}
							if((currEnemy.getHealth() < temp) && (currEnemy.getHealth() != 0)) {
								this.controller.addAttackMoney();
								money.setText(Integer.toString(this.controller.getMoney()));
							}
						}
					}
				}
			}
			*/
		}
	}
	
	private class TwoPlayerDialogBox extends Stage {
		/**
		 * Holds the data to select if it is a server or client
		 */
		private ToggleGroup createGroup;
		
		/**
		 * Holds the port selected by the user; default 4000
		 */
		private TextField portField;
		
		/**
		 * Holds the data to select if the game is played by a human or computer
		 */
		private ToggleGroup playAsGroup;
		
		/**
		 * Holds the address of the server; default localhost
		 */
		private TextField serverField;
		
		/**
		 * This is the constructor of the dialog box used by the user to select options about the
		 * game will be run. It creates the box and the shows it to the user when the user clicks new game
		 */
		public TwoPlayerDialogBox() {
			DialogPane dPane = new DialogPane();
			
			dPane.setMinHeight(130);
			dPane.setMinWidth(260);
			
			Alert dialog = new Alert(AlertType.NONE);
			dialog.initModality(Modality.APPLICATION_MODAL);
			dialog.initStyle(StageStyle.UTILITY);
			dialog.setTitle("Network Setup");
			
			createGroup = new ToggleGroup();
			
			RadioButton serverRadio = new RadioButton("Server");
			serverRadio.setSelected(true);
			serverRadio.setMinWidth(70);
			serverRadio.setToggleGroup(createGroup);
			
			RadioButton clientRadio = new RadioButton("Client");
			clientRadio.setMinWidth(70);
			clientRadio.setToggleGroup(createGroup);
			
			playAsGroup = new ToggleGroup();
			
			Text createText = new Text("   Create: ");
			Text serverText = new Text("   Server");
			Text portText = new Text("Port");
			
			serverField = new TextField("localhost");
			// default address = localhost
			serverField.setText("localhost");
			serverField.setMinWidth(90);
			
			portField = new TextField("4000");
			// default port = 4000
			portField.setText("4000");
			portField.setMinWidth(90);
			
			HBox createBox = new HBox(10);
			
			createBox.getChildren().addAll(createText, serverRadio, clientRadio);
			
			HBox serverPortBox = new HBox(10);
			serverPortBox.getChildren().addAll(serverText, serverField, portText, portField);
			
			VBox holder = new VBox(10);
			holder.getChildren().addAll(createBox, serverPortBox);
			
			
			dPane.getChildren().addAll(holder);
			dialog.setDialogPane(dPane);
			
			ButtonType ok = new ButtonType("OK");
			ButtonType cancel = new ButtonType("Cancel");
			dialog.getButtonTypes().addAll(ok, cancel);
			
			dialog.showAndWait();
		}
		
		/**
		 * This method returns the port selected by the use. Default of 4000
		 * 
		 * @return An int representing the port to run the game on
		 */
		public int getPort() {
			return  Integer.parseInt(portField.getText());
		}
		
		/**
		 * This method returns the address of the server. default of localhost
		 * 
		 * @return A string of the address of the server
		 */
		public String getAddress() {
			return serverField.getText();
			
		}
		
		/**
		 * This method returns a boolean indicating if the game is run by human or computer.
		 * true = human, false = computer
		 * 
		 * @return A boolean indicating how the game will be run (computer or human)
		 */
		public boolean playAs() {
			if(this.playAsGroup.getSelectedToggle().toString().contains("Human")) {
				return true;
			}
			return false;
		}
		
		/**
		 * This method returns a boolean indicating if the current instance is a server
		 * or a client
		 * 
		 * @return A boolean indicating if the instance is a server or client
		 */
		public boolean createType() {
			if(this.createGroup.getSelectedToggle().toString().contains("Server")) {
				return false;
			}
			return true;
		}
	}
}
