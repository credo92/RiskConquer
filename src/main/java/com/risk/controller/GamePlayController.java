package com.risk.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Stack;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.risk.entity.Card;
import com.risk.entity.Continent;
import com.risk.entity.Map;
import com.risk.entity.Player;
import com.risk.entity.Territory;
import com.risk.map.util.MapUtil;
import com.risk.model.DiceModel;
import com.risk.model.GameModel;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Game play controller to control all the
 * 
 * @author rahul
 * @version 1.0.0
 *
 */
public class GamePlayController implements Initializable {

	/**
	 * The @map reference.
	 */
	private Map map;

	/**
	 * The @gameModel reference.
	 */
	private GameModel gameModel;

	/**
	 * The @numberOfPlayers count of players.
	 */
	@FXML
	private ChoiceBox<Integer> numberOfPlayers;

	/**
	 * The @attack button.
	 */
	@FXML
	private Button attack;

	/**
	 * The @fortify button.
	 */
	@FXML
	private Button fortify;

	/**
	 * The @endTurn button.
	 */
	@FXML
	private Button endTurn;

	/**
	 * The @reinforcement button.
	 */
	@FXML
	private Button reinforcement;

	/**
	 * The @dataDisplay display container.
	 */
	@FXML
	private VBox dataDisplay;

	/**
	 * The @selectedTerritoryList list of territories drop down.
	 */
	@FXML
	private ListView<Territory> selectedTerritoryList;

	/**
	 * The @adjTerritoryList adjacent territory list.
	 */
	@FXML
	private ListView<Territory> adjTerritoryList;

	/**
	 * The @playerTime time remaining for the player.
	 */
	@FXML
	private Label playerTime;

	/**
	 * The @executor scheduler.
	 */
	private ScheduledExecutorService executor;

	/**
	 * The @playerChosen current player playing.
	 */
	@FXML
	private Label playerChosen;

	/**
	 * The @gameConsole output console.
	 */
	@FXML
	private TextArea gameConsole;

	/**
	 * The @placeArmy button.
	 */
	@FXML
	private Button placeArmy;

	/**
	 * The @numberOfPlayersSelected .
	 */
	private int numberOfPlayersSelected;

	/**
	 * The @gamePlayerList list of players in the game.
	 */
	private List<Player> gamePlayerList;

	/**
	 * The @playerPlaying current player playing.
	 */
	private Player playerPlaying;

	/**
	 * The @playerIterator.
	 */
	private Iterator<Player> playerIterator;
	
	/**
	 * The @stackOfCards.
	 */
	private Stack<Card> stackOfCards;
	
	private DiceModel diceModel;

	/**
	 * Constructor for GamePlayController
	 * 
	 * @param map
	 *            reference to the loaded map
	 */
	
	public GamePlayController(Map map) {
		this.map = map;
		this.gameModel = new GameModel();	
		this.diceModel = new DiceModel();
		
	}
	
	
	public String nameOfPlayer; //adding for test
	
	
	public String getNameOfPlayer() {
		return nameOfPlayer;
	} //adding for test

	public void setNameOfPlayer(String nameOfPlayer) {
		this.nameOfPlayer = nameOfPlayer;
	} //adding for test

	/**
	 * Initialize the number of players.
	 */
	public void initializeTotalPlayers() {
		numberOfPlayers.getItems().removeAll(numberOfPlayers.getItems());
		numberOfPlayers.getItems().addAll(3, 4, 5, 6);
	}

	/**
	 * Create player object on selection of number of players to be played.
	 */
	public void selectionOfPlayersListener() {
		numberOfPlayers.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Integer>() {
			@Override
			public void changed(ObservableValue<? extends Integer> observable, Integer oldValue, Integer newValue) {
				setNumberOfPlayersSelected(numberOfPlayers.getSelectionModel().getSelectedItem());

				gamePlayerList.clear();
				gamePlayerList = gameModel.createPlayer(getNumberOfPlayersSelected(), gamePlayerList, gameConsole);
				MapUtil.appendTextToGameConsole("===Players creation complete===\n", gameConsole);

				numberOfPlayers.setDisable(true);
				playerIterator = gamePlayerList.iterator();

				gameModel.assignArmiesToPlayers(gamePlayerList, gameConsole);
				assignTerritoryToPlayer();
				assignCardToTerritory();
			}
		});
	}

	/*
	 * (non-Javadoc) Game controller initializer, loading map data.
	 * 
	 * @see javafx.fxml.Initializable#initialize(java.net.URL,
	 * java.util.ResourceBundle)
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		gamePlayerList = new ArrayList<>();
		initializeTotalPlayers();
		selectionOfPlayersListener();
		loadMapData();
		MapUtil.disableControl(reinforcement, fortify, attack);

		selectedTerritoryList.setCellFactory(param -> new ListCell<Territory>() {
			@Override
			protected void updateItem(Territory item, boolean empty) {
				super.updateItem(item, empty);

				if (empty || item == null || item.getName() == null) {
					setText(null);
				} else {
					setText(item.getName() + ":-" + item.getArmies() + "-" + item.getPlayer().getName());
				}
			}
		});
		selectedTerritoryList.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				Territory territory = selectedTerritoryList.getSelectionModel().getSelectedItem();
				populateAdjTerritory(territory);
			}
		});

		adjTerritoryList.setCellFactory(param -> new ListCell<Territory>() {
			@Override
			protected void updateItem(Territory item, boolean empty) {
				super.updateItem(item, empty);

				if (empty || item == null || item.getName() == null) {
					setText(null);
				} else {
					setText(item.getName() + "-" + item.getArmies() + "-" + item.getPlayer().getName());
				}
			}
		});
		adjTerritoryList.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {

			}
		});
	}

	/**
	 * Populate adjacent territory of a given territory.
	 * 
	 * @param territory
	 *            territory to be checked
	 */
	private void populateAdjTerritory(Territory territory) {
		this.adjTerritoryList.getItems().clear();
		for (Territory adjTerr : territory.getAdjacentTerritories()) {
			this.adjTerritoryList.getItems().add(adjTerr);
		}
	}
	
	
	public void loadDiceView() {
		final Stage newMapStage = new Stage();
		newMapStage.setTitle("Attack Window");
		Territory attackingTerritory = selectedTerritoryList.getSelectionModel().getSelectedItem();
		Territory defendingTerritory = adjTerritoryList.getSelectionModel().getSelectedItem();
		DiceRollController diceController = new DiceRollController(attackingTerritory, defendingTerritory);

		FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("DiceLayout.fxml"));
		loader.setController(diceController);

		Parent root = null;
		try {
			root = (Parent) loader.load();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Scene scene = new Scene(root);
		newMapStage.setScene(scene);
		newMapStage.show();		
	}
	
	public boolean checkIfAttackIsValid() {
		Territory attackingTerritory = selectedTerritoryList.getSelectionModel().getSelectedItem();
		Territory defendingTerritory = adjTerritoryList.getSelectionModel().getSelectedItem();
		if(attackingTerritory.getPlayer().getName() != defendingTerritory.getPlayer().getName()) {
			return true;
		}
		return false;
		
	}

	/**
	 * Attack Phase of the game play.
	 * @param event
	 *            event.
	 */
	@FXML
	private void attack(ActionEvent event) {
		if(!checkIfAttackIsValid()) {
			MapUtil.infoBox("You cannot select territory belongs to you",  "Message", "");
		}
		loadDiceView();
		
	}

	/**
	 * Fortify phase of the game play.
	 * 
	 * @param event
	 *            event
	 */
	@FXML
	private void fortify(ActionEvent event) {
		Territory selectedTerritory = this.selectedTerritoryList.getSelectionModel().getSelectedItem();
		Territory adjTerritory = this.adjTerritoryList.getSelectionModel().getSelectedItem();

		if (selectedTerritory == null) {
			MapUtil.infoBox("Please choose Selected Territory as source.", "Message", "");
			return;
		} else if (adjTerritory == null) {
			MapUtil.infoBox("Please choose Adjacent Territory as destination.", "Message", "");
			return;
		} else if (!(adjTerritory.getPlayer().equals(playerPlaying))) {
			MapUtil.infoBox("Adjacent Territory does not belong to you.", "Message", "");
			return;
		}

		Integer armies = Integer.valueOf(MapUtil.inputDialogueBoxForArmiesFortification());
		if (armies > 0) {
			if (selectedTerritory.getArmies() == armies) {
				MapUtil.infoBox("You cannot move all the armies.", "Message", "");
				return;
			} else if (selectedTerritory.getArmies() < armies) {
				MapUtil.infoBox("You don't have " + armies + " armies.", "Message", "");
				return;
			} else {
				selectedTerritory.setArmies(selectedTerritory.getArmies() - armies);
				adjTerritory.setArmies(adjTerritory.getArmies() + armies);
				selectedTerritoryList.refresh();
				adjTerritoryList.refresh();
				MapUtil.appendTextToGameConsole("=======Fortification ended=======\n", gameConsole);
			}
		}
		else {
			MapUtil.infoBox("Invalid entry", "Message", "");
			return;
		}

		// initialize re-inforcement for the next player
		loadPlayingPlayer();

		initializeReinforcement();

	}

	/**
	 * Action on endTurn event, load another player.
	 * 
	 * @param event
	 *            event
	 */
	@FXML
	private void endTurn(ActionEvent event) {
		if (!executor.isShutdown()) {
			executor.shutdown();
		}
		start();	}

	/**
	 * Place armies on territory in round robin manner.
	 * 
	 * @param event
	 *            event
	 */
	@FXML
	private void placeArmy(ActionEvent event) {
		int playerArmies = playerPlaying.getArmies();
		if (playerArmies > 0) {
			Territory territory = selectedTerritoryList.getSelectionModel().getSelectedItem();
			if (territory == null) {
				territory = selectedTerritoryList.getItems().get(0);
			}
			territory.setArmies(territory.getArmies() + 1);
			playerPlaying.setArmies(playerArmies - 1);
		}
		loadMapData();
		selectedTerritoryList.refresh();

		boolean armiesExhausted = gameModel.checkIfPlayersArmiesExhausted(gamePlayerList);
		if (armiesExhausted) {
			executor.shutdownNow();
			loadPlayingPlayer();
			initializeAttack();
		} else {
			executor.shutdownNow();
			if (executor.isShutdown()) {
				start();
			}
		}
	}

	/**
	 * Game reinforcement phase. Assinging armies to the player and player assinging
	 * armies to their territory.
	 * 
	 * @param event
	 *            event
	 */
	@FXML
	private void reinforcement(ActionEvent event) {
		if (playerPlaying.getArmies() > 0) {
			Territory territory = selectedTerritoryList.getSelectionModel().getSelectedItem();
			if (territory == null) {
				MapUtil.infoBox("Select a territory to place army on.", "Message", "");
				return;
			}

			Integer armies = Integer.valueOf(MapUtil.inputDailougeBox());
			if (playerPlaying.getArmies() < armies) {
				MapUtil.infoBox("You do not have sufficent armies.", "Message", "");
				return;
			}
			territory.setArmies(territory.getArmies() + armies);
			playerPlaying.setArmies(playerPlaying.getArmies() - armies);
			MapUtil.appendTextToGameConsole(armies + ": assigned to territory " + territory.getName() + "\n",
					gameConsole);
			selectedTerritoryList.refresh();
			playerChosen.setText(playerPlaying.getName() + ":- " + playerPlaying.getArmies() + " armies left.");
		}
		// start attack phase
		if (playerPlaying.getArmies() <= 0) {
			initializeAttack();
		}
	}

	/**
	 * Load map data on the game screen.
	 */
	private void loadMapData() {
		dataDisplay.getChildren().clear();
		for (Continent continent : map.getContinents()) {
			dataDisplay.autosize();
			dataDisplay.getChildren().add(MapUtil.createNewTitledPane(continent));
		}
	}
		
	/**
	 * Distribute all territory among the player.
	 */
	private void assignTerritoryToPlayer() {
		MapUtil.appendTextToGameConsole("===Assigning territories===\n", gameConsole);
		gameModel.assignTerritoryToPlayer(map, gamePlayerList, gameConsole);
		MapUtil.appendTextToGameConsole("===Territories assignation complete===\n", gameConsole);
		loadMapData();
		start();
	}
	
	/**
	 * Assign card to each territories.
	 */
	private void assignCardToTerritory() {
		MapUtil.appendTextToGameConsole("===Assigning Card to territories===\n", gameConsole);
		stackOfCards = gameModel.assignCardToTerritory(map, gameConsole);
		MapUtil.appendTextToGameConsole("===Card assignation complete===\n", gameConsole);
	}

	/**
	 * Load the information of the current player playing.
	 */
	private void loadPlayingPlayer() {
		if (!playerIterator.hasNext()) {
			playerIterator = gamePlayerList.iterator();
		}
		playerPlaying = playerIterator.next();
		MapUtil.appendTextToGameConsole("============================ \n", gameConsole);
		MapUtil.appendTextToGameConsole(playerPlaying.getName() + "!....started playing.\n", gameConsole);
		selectedTerritoryList.getItems().clear();
		adjTerritoryList.getItems().clear();
		for (Territory territory : playerPlaying.getAssignedTerritory()) {
			selectedTerritoryList.getItems().add(territory);
		}
		playerChosen.setText(playerPlaying.getName() + ":- " + playerPlaying.getArmies() + " armies left.\n");
	}

	/**
	 * Calculate reinforcement armies based on the game rules.
	 */
	private void calculateReinforcementArmies() {
		if (this.playerPlaying != null) {
			playerPlaying = gameModel.calculateReinforcementArmies(map, playerPlaying);
			playerChosen.setText(playerPlaying.getName() + ":- " + playerPlaying.getArmies() + " armies left.");
		} else {
			MapUtil.appendTextToGameConsole("Error!. No player playing.", gameConsole);
		}
	}

	private void start() {
		executor = Executors.newSingleThreadScheduledExecutor();
		executor.scheduleWithFixedDelay(new Runnable() {
			@Override
			public void run() {
				Platform.runLater(() -> loadPlayingPlayer());
			}

		}, 0, 300000, TimeUnit.MILLISECONDS);
	}

	/**
	 * Initialize reinforcement phase of the game.
	 */
	private void initializeReinforcement() {
		MapUtil.disableControl(placeArmy, fortify, attack);
		MapUtil.enableControl(reinforcement);
		reinforcement.requestFocus();
		MapUtil.appendTextToGameConsole("============================ \n", gameConsole);
		MapUtil.appendTextToGameConsole("======Start Reinforcement! =========== \n", gameConsole);
		MapUtil.appendTextToGameConsole(playerPlaying.getName() + "\n", gameConsole);
		calculateReinforcementArmies();
	}

	/**
	 * Initialize attack phase of the game.
	 */
	
	private void initializeAttack() {
		MapUtil.disableControl(reinforcement, placeArmy);
		MapUtil.enableControl(attack);
		attack.requestFocus();
		MapUtil.appendTextToGameConsole("============================ \n", gameConsole);
		MapUtil.appendTextToGameConsole("===Attack phase under developement! === \n", gameConsole);
		
		//initializeFortification();
	}

	/**
	 * Initialize fortification phase of the game.
	 */
	private void initializeFortification() {
		if (gameModel.isFortificationPhaseValid(map, playerPlaying)) {
			MapUtil.disableControl(reinforcement, attack, placeArmy);
			MapUtil.enableControl(fortify);
			fortify.requestFocus();
			MapUtil.appendTextToGameConsole("============================ \n", gameConsole);
			MapUtil.appendTextToGameConsole("====Fortification phase started! ====== \n", gameConsole);
		} else {
			MapUtil.appendTextToGameConsole("====Fortification phase started! ====== \n", gameConsole);
			MapUtil.appendTextToGameConsole(playerPlaying.getName() + " has no armies to be fortified.", gameConsole);
			loadPlayingPlayer();
			initializeReinforcement();
		}
	}

	/**
	 * @return int number of layer selected
	 */
	public int getNumberOfPlayersSelected() {
		return numberOfPlayersSelected;
	}

	/**
	 * @param numberOfPlayersSelected number of player selected
	 */
	public void setNumberOfPlayersSelected(int numberOfPlayersSelected) {
		this.numberOfPlayersSelected = numberOfPlayersSelected;
	}
}