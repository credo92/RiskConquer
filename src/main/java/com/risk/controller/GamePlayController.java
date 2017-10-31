package com.risk.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;

import com.risk.entity.Continent;
import com.risk.entity.Map;
import com.risk.entity.Player;
import com.risk.entity.Territory;
import com.risk.map.util.GameUtil;
import com.risk.map.util.MapUtil;
import com.risk.model.GameModel;
import com.risk.model.PlayerModel;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

/**
 * Game play controller to control all the
 * 
 * @author rahul
 * @version 1.0.0
 *
 */
public class GamePlayController implements Initializable, Observer {

	private Map map;

	/**
	 * The @gameModel refrence.
	 */
	private GameModel gameModel;

	private PlayerModel playerModel;

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
	 * Constructor for GamePlayController
	 * 
	 * @param map
	 *            reference to the loaded map
	 */
	public GamePlayController(Map map) {
		this.map = map;
		this.gameModel = new GameModel();
		this.playerModel = new PlayerModel();
		playerModel.addObserver(this);
	}

	/**
	 * Create player object on selection of number of players to be played.
	 */
	public void playerSelectionListner() {
		numberOfPlayers.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Integer>() {
			@Override
			public void changed(ObservableValue<? extends Integer> observable, Integer oldValue, Integer newValue) {
				setNumberOfPlayersSelected(numberOfPlayers.getSelectionModel().getSelectedItem());

				gamePlayerList.clear();
				gamePlayerList = playerModel.createPlayer(getNumberOfPlayersSelected(), gamePlayerList, gameConsole);
				MapUtil.appendTextToGameConsole("===Players creation complete===\n", gameConsole);

				numberOfPlayers.setDisable(true);
				playerIterator = gamePlayerList.iterator();

				playerModel.assignArmiesToPlayers(gamePlayerList, gameConsole);
				assignTerritoryToPlayer();
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
		GameUtil.initializeTotalPlayers(numberOfPlayers);
		playerSelectionListner();
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

	/**
	 * Attack Phase of the game play.
	 * 
	 * @param event
	 *            event.
	 */
	@FXML
	private void attack(ActionEvent event) {
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

		playerModel.fortificationPhase(selectedTerritory, adjTerritory);
		selectedTerritoryList.refresh();
		adjTerritoryList.refresh();
		loadMapData();

		MapUtil.appendTextToGameConsole("=======Fortification ended=======\n", gameConsole);
	}

	/**
	 * Action on endTurn event, load another player.
	 * 
	 * @param event
	 *            event
	 */
	@FXML
	private void endTurn(ActionEvent event) {
		loadPlayingPlayer();
		initializeReinforcement();
	}

	/**
	 * Place armies on territory in round robin manner.
	 * 
	 * @param event
	 *            event
	 */
	@FXML
	private void placeArmy(ActionEvent event) {
		playerModel.placeArmy(playerPlaying, selectedTerritoryList, gamePlayerList);
	}

	/**
	 * Game reinforcement phase. Assinging armies to the player and player assinging
	 * armies to their territory.
	 * @param event event
	 */
	@FXML
	private void reinforcement(ActionEvent event) {
		Territory territory = selectedTerritoryList.getSelectionModel().getSelectedItem();
		playerModel.reinforcementPhase(territory, gameConsole);
		selectedTerritoryList.refresh();
		loadMapData();
		playerChosen.setText(playerPlaying.getName() + ":- " + playerPlaying.getArmies() + " armies left.");
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
		loadPlayingPlayer();
	}

	/**
	 * Load the information of the current player playing.
	 */
	private void loadPlayingPlayer() {
		if (!playerIterator.hasNext()) {
			playerIterator = gamePlayerList.iterator();
		}
		playerPlaying = playerIterator.next();
		playerModel.setPlayerPlaying(playerPlaying);
		MapUtil.appendTextToGameConsole("============================ \n", gameConsole);
		MapUtil.appendTextToGameConsole(playerPlaying.getName() + "!....started playing.\n", gameConsole);
		selectedTerritoryList.getItems().clear();
		adjTerritoryList.getItems().clear();
		loadMapData();
		for (Territory territory : playerPlaying.getAssignedTerritory()) {
			selectedTerritoryList.getItems().add(territory);
		}
		playerChosen.setText(playerPlaying.getName() + ":- " + playerPlaying.getArmies() + " armies left.\n");
	}

	/**
	 * Calculate reinforcement armies based ont the game rules.
	 */
	private void calculateReinforcementArmies() {
		if (this.playerPlaying != null) {
			playerPlaying = playerModel.calculateReinforcementArmies(map, playerPlaying);
			playerChosen.setText(playerPlaying.getName() + ":- " + playerPlaying.getArmies() + " armies left.");
		} else {
			MapUtil.appendTextToGameConsole("Error!. No player playing.", gameConsole);
		}
	}

	/**
	 * Initialize reinforcement phase of the game.
	 */
	private void initializeReinforcement() {
		loadPlayingPlayer();

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
		playerModel.attackPhase();
	}

	/**
	 * Initialize fortification phase of the game.
	 */
	private void initializeFortification() {
		MapUtil.disableControl(reinforcement, attack, placeArmy);
		MapUtil.enableControl(fortify);
		fortify.requestFocus();
		MapUtil.appendTextToGameConsole("============================ \n", gameConsole);
		MapUtil.appendTextToGameConsole("====Fortification phase started! ====== \n", gameConsole);
	}

	private void updateWorldDomination() {

	}

	/**
	 * Initialize place army view.
	 */
	private void initializePlaceArmy() {
		loadMapData();
		selectedTerritoryList.refresh();
		loadPlayingPlayer();
	}

	/**
	 * check if there is a valid fortification phase. 
	 */
	private void isValidFortificationPhase() {
		playerModel.isFortificationPhaseValid(map, playerPlaying);
	}

	/**
	 * Change view if there is no valid fortification.
	 */
	private void noFortificationPhase() {
		MapUtil.appendTextToGameConsole("====Fortification phase started! ====== \n", gameConsole);
		MapUtil.appendTextToGameConsole(playerPlaying.getName() + " has no armies to be fortified.", gameConsole);
		loadPlayingPlayer();
		initializeReinforcement();
	}

	/**
	 * @return int number of layer selected
	 */
	public int getNumberOfPlayersSelected() {
		return numberOfPlayersSelected;
	}

	/**
	 * @param numberOfPlayersSelected
	 *            number of player selected
	 */
	public void setNumberOfPlayersSelected(int numberOfPlayersSelected) {
		this.numberOfPlayersSelected = numberOfPlayersSelected;
	}

	/* (non-Javadoc)
	 * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
	 */
	public void update(Observable o, Object arg) {

		String view = (String) arg;

		if (view.equals("Attack")) {
			initializeAttack();
		}
		if (view.equals("FirstAttack")) {
			loadPlayingPlayer();
			initializeAttack();
		}
		if (view.equals("Reinforcement")) {
			initializeReinforcement();
		}
		if (view.equals("Fortification")) {
			initializeFortification();
		}
		if (view.equals("placeArmy")) {
			initializePlaceArmy();
		}
		if (view.equals("WorldDomination")) {
			updateWorldDomination();
		}
		if (view.equals("checkIfFortificationPhaseValid")) {
			isValidFortificationPhase();
		}
		if (view.equals("noFortificationMove")) {
			noFortificationPhase();
		}
	}
}