/**
 * Tower Defense View class that represents the gui representation
 * for the game. This class contains a private inner class that
 * holds a dialog box that allows the player to play on a server
 * with someone else. The rest of the class establishes the game
 * board's visuals, properly laying out the starting menu, allowing
 * the user to pick their stage, click on towers, click where to
 * place their towers, and starting, pausing, and fast forwarding
 * the game when they click the respective buttons.
 * @author David Gonzales, Mario Verdugo, Luke Cernetic, Chris Crabtree
 */

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.Optional;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import javafx.animation.PathTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
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
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

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
	private TwoPlayerDialogBox dialogBox;
	private Clip introClip;
	
	/**
	 * Method to launch the gui and set all proper menus and pictures
	 */
	@Override
	public void start(Stage stage) throws Exception {
		this.stage = stage;
		model = new TowerDefenseModel();
		controller = new TowerDefenseController(model);
		model.addObserver(this);
		
		stage.setTitle("Rick and Morty Tower Defense");
		
		introClip = this.createAudioClip("src/sounds/intro.wav");
		
		border = new BorderPane();
		Rectangle mainMenu = new Rectangle();
		mainMenu.setWidth(1000);
		mainMenu.setHeight(727);
		mainMenu.setOnMouseClicked((event) -> {
			if (event.getX() > 45 && event.getX() < 380 && event.getY() > 350 && event.getY() < 411) {
				isMultiplayer = false;
				Image mapSelection = new Image("pictures/mapSelection.png");
				mainMenu.setFill(new ImagePattern(mapSelection));
				mainMenu.setOnMouseClicked((event2) -> {
					Clip backgroundMusic = this.createAudioClip("src/sounds/background.wav");
					if (event2.getX() > 75 && event2.getX() < 453 && event2.getY() > 100 && event2.getY() < 611) {
						introClip.stop();
						controller.setRoad(new Road1());
						startGame();
						Clip map1Clip = this.createAudioClip("src/sounds/Show me what you got.wav");
						map1Clip.start();
						backgroundMusic.start();
					}
					else if (event2.getX() > 546 && event2.getX() < 924 && event2.getY() > 100 && event2.getY() < 611) {
						introClip.stop();
						controller.setRoad(new Road2());
						startGame();
						Clip map2Clip = this.createAudioClip("src/sounds/get Schwifty.wav");
						map2Clip.start();
						backgroundMusic.start();
					}
				});
			}
		
			else if (event.getX() > 40 && event.getX() < 336 && event.getY() > 455 && event.getY() < 520 ) {
				dialogBox = new TwoPlayerDialogBox();
				if(!dialogBox.createType()) {
					Image mapSelection = new Image("pictures/mapSelection.png");
					mainMenu.setFill(new ImagePattern(mapSelection));
					mainMenu.setOnMouseClicked((event2) -> {
						Clip backgroundMusic = this.createAudioClip("src/sounds/background.wav");
						if (event2.getX() > 75 && event2.getX() < 453 && event2.getY() > 100 && event2.getY() < 611) {
							introClip.stop();
							controller.setRoad(new Road1());
							controller.sendMap(new TDNetworkMessage(1));
							startGame();
							Clip map1Clip = this.createAudioClip("src/sounds/Show me what you got.wav");
							map1Clip.start();
							backgroundMusic.start();
							model.setNetworked(true);
						}
						else if (event2.getX() > 546 && event2.getX() < 924 && event2.getY() > 100 && event2.getY() < 611) {
							introClip.stop();
							controller.setRoad(new Road2());
							controller.sendMap(new TDNetworkMessage(2));
							startGame();
							Clip map2Clip = this.createAudioClip("src/sounds/get Schwifty.wav");
							map2Clip.start();
							backgroundMusic.start();
							model.setNetworked(true);
						}
					});
				}
				setupNetwork();
				isMultiplayer = true;
				controller.setMulitplayer(isMultiplayer);
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
		Scene scene = new Scene(border, 1000, 727);
		stage.setScene(scene);
		stage.show();
		
		introClip.start();
		introClip.loop(30);
		
	}
	
	private Clip createAudioClip(String fileName) {
		File audioFile = new File(fileName);
		AudioInputStream aio;
		Clip audioClip = null;
		try {
			aio = AudioSystem.getAudioInputStream(audioFile);
			AudioFormat format = aio.getFormat();
			DataLine.Info info = new DataLine.Info(Clip.class, format);
			audioClip = (Clip) AudioSystem.getLine(info);
			audioClip.open(aio);
		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
			e.printStackTrace();
		}
		return audioClip;
	}
	
	/**
	 * Method that launches the playable game stage. It loads all proper
	 * graphics for the road and towers, as well as all buttons necessary
	 * for gameplay
	 */
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
							if(currTowerClicked instanceof MeeseeksTower) {
								Clip meeseeks = this.createAudioClip("src/sounds/Meeseeks.wav");
								meeseeks.start();
							} else if (currTowerClicked instanceof SquanchyTower) {
								Clip squanchy = this.createAudioClip("src/sounds/Squanchy.wav");
								squanchy.start();
							} else if (currTowerClicked instanceof RickTower) {
								Clip rick = this.createAudioClip("src/sounds/Rick.wav");
								rick.start();
							} else if (currTowerClicked instanceof MortyTower) {
								Clip morty = this.createAudioClip("src/sounds/Morty.wav");
								morty.start();
							} else if (currTowerClicked instanceof JerryTower) {
								Clip jerry = this.createAudioClip("src/sounds/Jerry.wav");
								jerry.start();
							} else if (currTowerClicked instanceof BirdPersonTower) {
								Clip birdperson = this.createAudioClip("src/sounds/Birdperson.wav");
								birdperson.start();
							}
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
									controller.startRound();
									//controller.sendEnimies();
									controller.sendPlay();
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
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			try {
				socket = new Socket(this.dialogBox.getAddress(), this.dialogBox.getPort());
				controller.setTurn(false);
				controller.setIsClient(true);
				introClip.stop();
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
	
	/**
	 * Method to set the portrait of the gui depending on what
	 * tower the user has clicked on most recently
	 */
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

	/**
	 * Private method that creates the dialog alert when the user has lost the
	 * gamee, meaning the user's health has been depleted to 0. This alert gives
	 * them an option to start a new game as well.
	 */
	public Rectangle findNode(int x, int y) {
		for (Node node: grid.getChildren()) {
			if(grid.getRowIndex(node) == x && grid.getColumnIndex(node) == y) {
				Rectangle curr = (Rectangle) node;
				return curr;
			}
		}
		return null;
	}
	
	/**
	 * Private method that creates the dialog alert when the user has lost the
	 * gamee, meaning the user's health has been depleted to 0. This alert gives
	 * them an option to start a new game as well.
	 */
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
	
	/**
	 * Private method that creates the dialog alert when the user has won the
	 * game. This alert gives them the option to start a new game.
	 */
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

	/**
	 * Method that helps the gui run the actual game logic from the controller.
	 * It has varying behaviors depending on the phase of the game (attack or place)
	 * and provides the most up to date graphic representation of the game being
	 * played in the model.
	 */
	@Override
	public void update(Observable o, Object arg) {
		// in the place phase, determine if the user has won the game, and if not
		// allow them to place towers and update the gui accordingly
		if (controller.getGamePhase().equals("place")) {
			if (arg == null) {
				if (controller.getRound() == 10) {
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
			}
		} else {
			// in the attack phase, run the game as normal until the round
			// is over or the user has died.
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
			// have towers attack enemies, continue moving enemies along the path
			// as long as they are alive
			ArrayList<Enemy> enemies = (ArrayList<Enemy>) arg;
			int[][] currMap = controller.getRoad().getMap();
			this.controller.towerAttack();
						
			//FOR EACH TOWER
			Tower[][] towerMap = controller.getTowerMap();
			for (int i = 0; i < towerMap.length; i++) {
				for (int j = 0; j < towerMap[i].length; j ++) {
					// IF TOWER IS NOT EQUAL TO NULL
					if (towerMap[i][j] != null) {
						ArrayList<Enemy> enemiesList = towerMap[i][j].getEnemiesToAttack();
						//SEARCH THROUGH ENEMIES NEXT TO CURRENT TOWER
						for (int k = 0; k < enemiesList.size(); k++) {
							Rectangle test = new Rectangle();
							test.setWidth(10);
							test.setHeight(10);
							if (towerMap[i][j] instanceof RickTower) {
								test.setFill(Color.GREENYELLOW);
							}
							else if (towerMap[i][j] instanceof JerryTower) {
								test.setFill(Color.LIGHTSKYBLUE);
							}
							else if (towerMap[i][j] instanceof MortyTower) {
								test.setFill(Color.RED);
							}
							else if (towerMap[i][j] instanceof MeeseeksTower) {
								test.setFill(Color.SLATEBLUE);
							}
							else if (towerMap[i][j] instanceof SquanchyTower) {
								test.setFill(Color.ORANGE);
							}	
							else if (towerMap[i][j] instanceof BirdPersonTower) {
								test.setFill(Color.WHITE);
							}	
							Path path = new Path();
							
							//starting point
							path.getElements().add(new MoveTo((towerMap[i][j].getX() * 47) + 23, (((towerMap[i][j].getY()) * 47) + 40)));
							
							//ending point
							path.getElements().add(new LineTo((enemiesList.get(k).getY() * 47) + 23, ((enemiesList.get(k).getX() * 47)) + 40));
							
							PathTransition pathTransition = new PathTransition();
							pathTransition.setDuration(Duration.millis(controller.getGameSpeed()));
							pathTransition.setNode(test);
							pathTransition.setPath(path);
							pathTransition.setOnFinished((eventTest) -> {
								border.getChildren().remove(test);
							});
							pathTransition.play();

							Platform.runLater(new Runnable() {
				                 @Override public void run() {
				                     border.getChildren().add(test);
				                 }
				             });
						}
						towerMap[i][j].clearEnemies();
					}
				}
			}
					
			for (int i = 0; i < currMap.length; i++) {
				for (int j = 0; j < currMap[i].length; j++) {
					boolean found  = false;
					if (currMap[i][j] == 1) {
						Rectangle curr = findNode(i, j);
						for (int k = 0; k < enemies.size(); k++) {
							Enemy currEnemy = enemies.get(k);
							if (i == currEnemy.getX() && j == currEnemy.getY()) {
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
		}
	}
	
	/**
	 * Private inner class that creates the two player dialog
	 * boxes necessary to properly set the settings for networked
	 * game play
	 * @author David Gonzales, Mario Verdugo, Luke Cernetic, Chris Crabtree
	 *
	 */
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
