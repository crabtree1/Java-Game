/**
 * Controller class for our tower defense game. The controller has a model it
 * interacts with, and acts as the intermediary that allows the view to be able
 * to access the model.
 * @author David Gonzales, Mario Verdugo, Luke Cernetic, Chris Crabtree
 */

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;

import javafx.application.Platform;
import javafx.scene.image.Image;

public class TowerDefenseController {

	private TowerDefenseModel model;
	private ObjectOutputStream oos;
	private ObjectInputStream ois;
	private TDNetworkMessage otherMessage;
	private boolean isTurn = true;
	private boolean isMultiplayer = false;
	private int curTowerType = 0;
	private Image image = null;
	
	/**
	 * Constructor for the controller class
	 * @param model - model to use for the controller
	 */
	public TowerDefenseController(TowerDefenseModel model) {
		this.model = model;
	}
	
	/**
	 * This method initializes the data streams for network communications
	 * 
	 * This method initializes the ObjectOutputStream and ObjectInputSteam used
	 * to pass data through the network
	 * 
	 * @param socket: A socket from the current server connection
	 */
	public void initStreams(Socket socket) {
		 try {
			this.oos = new ObjectOutputStream(socket.getOutputStream());
			this.ois = new ObjectInputStream(socket.getInputStream());
			model.setOOS(oos);
		} catch (IOException e) {
		}
	}
	
	/**
	 * This method starts the turn of the current object
	 * 
	 * This method starts the current turn of an object by starting the thread that hold ois.readObject().
	 * We use a new thread because this is a blocking call, and it would cause the GUI to freeze. We also use
	 * Platfrom.runLater() to send GUI update commands to the GUI, which is the data from the opponents turn.
	 * 
	 * 
	 */
	public void startListening() {
		// creates a new thread to run ois.readObject()
		Thread inputThread = new Thread(new Runnable() {
			@Override
			public void run() {
				while(true) {
					otherMessage = null;
					try {
						Object other = ois.readObject();
						// after reading the object, it sends an update event to the main thread to update the gui
						Platform.runLater(new Runnable() {
							@Override
							public void run() {
								if(other instanceof TDNetworkMessage) {
									otherMessage = (TDNetworkMessage) other;
								}
								if(otherMessage.isPlaying()) {
									startRound();
								} else if(otherMessage.isPuased()) {
									changePaused();
								} else if(otherMessage.isRemoving()) {
									model.removeTowerNetwork(otherMessage.getRow(), otherMessage.getColumn());
								} else if (otherMessage.getSeed1() != null) {
									model.setSeed1(otherMessage.getSeed1());
									model.setSeed2(otherMessage.getSeed2());
								} else if(otherMessage.getTower() == 0) {
									image = new Image(new BirdPersonTower().getTowerPic());
									model.addTower(new BirdPersonTower(), otherMessage.getRow(), otherMessage.getColumn());
								} else if (otherMessage.getTower() == 1) {
									image = new Image(new JerryTower().getTowerPic());
									model.addTower(new JerryTower(), otherMessage.getRow(), otherMessage.getColumn());
								} else if (otherMessage.getTower() == 2) {
									image = new Image(new MeeseeksTower().getTowerPic());
									model.addTower(new MeeseeksTower(), otherMessage.getRow(), otherMessage.getColumn());
								} else if (otherMessage.getTower() == 3) {
									image = new Image(new MortyTower().getTowerPic());
									model.addTower(new MortyTower(), otherMessage.getRow(), otherMessage.getColumn());
								} else if (otherMessage.getTower() == 4) {
									image = new Image(new RickTower().getTowerPic());
									model.addTower(new RickTower(), otherMessage.getRow(), otherMessage.getColumn());
								} else {
									image = new Image(new SquanchyTower().getTowerPic());
									model.addTower(new SquanchyTower(), otherMessage.getRow(), otherMessage.getColumn());
								}
								// after finishing getting the opponets data, it is the current objects turn
								setTurn(true);
								//startListening();
							}
						});
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					} catch (SocketException e) {
						System.exit(1);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		});
		inputThread.start();
	}
	
	/**
	 * This method listens for a map from the server and sets the map accordingly 
	 */
	public void listenForMap() {
		try {
			otherMessage = (TDNetworkMessage) ois.readObject();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if(otherMessage.getMap() == 1) {
			setRoad(new Road1());
		} else {
			setRoad(new Road2());
		}
	}
	
	/**
	 * This method sends the map to the client
	 * @param message A TDNetworkMessage containing the map
	 */
	public void sendMap(TDNetworkMessage message) {
		try {
			oos.writeObject(message);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/** 
	 * This method tells the client to start the round
	 */
	public void sendPlay() {
		try {
			oos.writeObject(new TDNetworkMessage(true));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * This method sends the client the random seed needed to create random enemies
	 */
	public void sendSeeds() {
		try {
			oos.writeObject(new TDNetworkMessage(model.getSeed1(), model.getSeed2()));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * setter method to set the model for the controller to be
	 * whatever the passed parameter is
	 * @param model - tower model that will be the controller's model
	 */
	public void setModel(TowerDefenseModel model) {
		this.model = model;
	}
	
	/**
	 * Getter to return the controller's model
	 * @return the model
	 */
	public TowerDefenseModel getModel() {
		return model;
	}
	
	/**
	 * This method sets an int representation of the current tower
	 * @param curTower an int representing the current tower
	 */
	public void setTowerType(int curTower) {
		this.curTowerType = curTower;
	}
	
	/**
	 * Setter that sets the model's road
	 * @param currRoad - road object that will be the road's object
	 */
	public void setRoad(Road currRoad) {
		model.setRoad(currRoad);
	}
	
	/**
	 * This method returns a boolean indicating if it is its turn
	 * 
	 * @return A boolean indicating if it is its turn
	 */
	public boolean getTurn() {
		return this.isTurn ;
	}
	
	/**
	 * This method sets the turn of the current object
	 * 
	 * @param isTurn: A boolean represting the current turn of the object
	 */
	public void setTurn(boolean isTurn) {
		this.isTurn = isTurn;
	}
	
	/**
	 * This method sets a boolean indicating of the game is networked
	 * @param isMultiplayer A boolean indicating if the current game is networked
	 */
	public void setMulitplayer(boolean isMultiplayer) {
		this.isMultiplayer = isMultiplayer;
	}
	
	/**
	 * Getter to return the current road being used by the model
	 * @return the road in the model
	 */
	public Road getRoad() {
		return model.getRoad();
	}
	
	/**
	 * Getter to get the tower map at the current point in the model
	 * @return the 2D array tower map
	 */
	public Tower[][] getTowerMap(){
		return model.getTowerMap();
	}
	
	/**
	 * Method to begin the round in the model
	 */
	public void startRound() {
		model.startRound();
	}
	
	/** 
	 * This method returns an Image of the current tower
	 * @return An image of the current tower selected
	 */
	public Image getCurTower() {
		return this.image;
	}
	
	/**
	 * Method to add a tower to the game board. The location for the
	 * tower is dependent on where the user clicks in the gui, and the
	 * proper row and col are determined based on the click. The type of tower
	 * to be placed is also dependednt on what type of tower most recently clicked
	 * by the user
	 * @param currTowerClicked - the tower to be added to the tower map
	 * @param mouseX - x coordinate of where the user clicked in the gui
	 * @param mouseY - y coordinate of where the user clicked in the gui
	 */
	public void addTower(Tower currTowerClicked, double mouseX, double mouseY) {
		if(!isTurn) {
			return;
		}
		// only place a tower if the user can afford it
		if((getMoney() - currTowerClicked.getCost()) < 0) {
			return;
		}
		int row = 0;
		int col = 0;
		
		int lb;
		int ub;
		
		for (int i = 0; i < 15; i++) {
			lb = 47 * i;
			ub = 47 * (i + 1);
			if (mouseX >= lb && mouseX < ub) {
				col = i;
			}
			
			if (mouseY >= lb && mouseY < ub && i < 13) {
				row = i;
			}
		}
		
		if (currTowerClicked instanceof BirdPersonTower) {
			currTowerClicked = new BirdPersonTower();
		} else if (currTowerClicked instanceof JerryTower) {
			currTowerClicked = new JerryTower();
		}else if (currTowerClicked instanceof MeeseeksTower) {
			currTowerClicked = new MeeseeksTower();
		}else if (currTowerClicked instanceof MortyTower) {
			currTowerClicked = new MortyTower();
		} else if (currTowerClicked instanceof RickTower) {
			currTowerClicked = new RickTower();
		} else if (currTowerClicked instanceof SquanchyTower) {
			currTowerClicked = new SquanchyTower();
		} 
		if(!model.isClient() && isMultiplayer) {
			if(col < 8) {
				return;
			}
				
		} else if (model.isClient() && isMultiplayer) {
			if(col > 7) {
				return;
			}
		}
		this.model.spendMoney(currTowerClicked.getCost());
		model.addTower(currTowerClicked, row, col);
		if(isMultiplayer) {
			try {
				oos.writeObject(new TDNetworkMessage(row, col, curTowerType));
			} catch (IOException e) {
				e.printStackTrace();
			}
			this.isTurn = false;
		}
	}
	
	/**
	 * Method to sell the tower wherever the user clicks, 
	 * as long as there is a tower at that position.
	 * @param mouseX - x coordinate of the user click
	 * @param mouseY - y coordinate of the user click
	 */
	public void sellTower(double mouseX, double mouseY) {
		int row = 0;
		int col = 0;
		
		int lb;
		int ub;
		
		for (int i = 0; i < 15; i++) {
			lb = 47 * i;
			ub = 47 * (i + 1);
			if (mouseX >= lb && mouseX < ub) {
				col = i;
			}
			
			if (mouseY >= lb && mouseY < ub && i < 13) {
				row = i;
			}
		}
		if(!model.isClient() && isMultiplayer) {
			if(col < 8) {
				return;
			}
				
		} else if (model.isClient() && isMultiplayer) {
			if(col > 7) {
				return;
			}
		}
		if(this.model.towerAtPosition(row, col)) {
			this.model.removeTower(this.model.getTowerAtPost(row, col), row, col);
		}
		if(isMultiplayer) {
			try {
				TDNetworkMessage temp = new TDNetworkMessage(row, col, curTowerType);
				temp.setRemove(true);
				oos.writeObject(temp);
			} catch (IOException e) {
				e.printStackTrace();
			}
			//this.isTurn = false;
		}
	}
	
	/**
	 * Method to increase the speed of the game, therefore fastforwarding it
	 */
	public void increaseGameSpeed() {
		model.increaseGameSpeed();
	}
	
	/**
	 * Method to change the pause status of the game
	 */
	public void changePaused() {
		model.changePaused();
	}
	
	/**
	 * Method to see if a tower is at specific coordinates, which are
	 * determined by the paramters
	 * @param row - row value to check for a tower at
	 * @param col - col value to check for a tower at
	 */
	public void findTowerAtPosition(int row, int col) {
		this.model.towerAtPosition(row, col);
	}
	
	/**
	 * Getter to get the current game phase (either place or attack)
	 * @return the game phase
	 */
	public String getGamePhase() {
		return model.getGamePhase();
	}
	
	/**
	 * This method sets boolean if the game is a client
	 * @param isClient  A boolean indicating if the game is a client
	 */
	public void setIsClient(boolean isClient) {
		this.model.setClient(isClient);
	}
	
	/**
	 * Getter to get the user's money
	 * @return the user's money
	 */
	public int getMoney() {
		return this.model.getMoney();
	}
	
	/**
	 * Getter to get the user's health
	 * @return user's health
	 */
	public int getHealth() {
		return this.model.getHealth();
	}
	
	/**
	 * Method to give the user money when they have successfully attacked an
	 * enemy
	 */
	public void addAttackMoney() {
		this.model.addAttackMoney();
	}
	
	/**
	 * Method to remove health from the user if an enemy reaches their portal
	 */
	public void takeHealth() {
		this.model.takeDamage();
	}
	
	/**
	 * Method to have all towers attack the enemies
	 */
	public void towerAttack() {
		this.model.towerAttack();
	}
	
	/**
	 * Getter to get the current round of the game
	 * @return the game's round
	 */
	public int getRound() {
		return this.model.getRound();
	}

	/**
	 * This method tells the other game in a networked game to pause
	 */
	public void sendPause() {
		TDNetworkMessage temp = new TDNetworkMessage();
		temp.setPaused(true);
		try {
			oos.writeObject(temp);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Getter to return the current game speed of the model
	 * @return the game speed from the model
	 */
	public int getGameSpeed() {
		return this.model.getGameSpeed();
	}
}
